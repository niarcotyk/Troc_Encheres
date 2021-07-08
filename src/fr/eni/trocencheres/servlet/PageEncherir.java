package fr.eni.trocencheres.servlet;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bll.ArticleManager;
import fr.eni.trocencheres.bll.EnchereManager;
import fr.eni.trocencheres.bo.Article;
import fr.eni.trocencheres.bo.Enchere;
import fr.eni.trocencheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PageEncherir extends HttpServlet {
    private ArticleManager am = new ArticleManager();
    private EnchereManager em = new EnchereManager();
    private BusinessException businessException = new BusinessException();
    Utilisateur util = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Récupère la session
        HttpSession session = req.getSession();
        session.setAttribute("idArticle", req.getParameter("nomArticle"));

        util = (Utilisateur) session.getAttribute("utilisateur");

        //Récupérer numéro article
        if (req.getParameter("nomArticle") != null) {
            int id = Integer.parseInt(req.getParameter("nomArticle"));
            //Si utilisateur est connecté
            if (session.getAttribute("utilisateur") != null) {
                //Récupération de l'article en fonction de l'id
                List<Article> listeArticles = am.ChoisirArticlesEncherir(id, util.getIdUtilisateur());
                //Affichage des données dans la jsp
                for (Article art : listeArticles) {
                    req.setAttribute("nom_art", art.getNomArticle());
                    req.setAttribute("credit", art.getUtilisateur().getCredit());
                    req.setAttribute("description", art.getDescription());
                    req.setAttribute("categorie", art.getCategorie().getLibelle());
                    int offre = art.getEnchere().getMontantEnchere();
                    req.setAttribute("offre", art.getEnchere().getMontantEnchere());
                    req.setAttribute("acquereur", art.getEnchere().getNomAcquereur());
                    req.setAttribute("prixInitial", art.getPrixInitial());
                    //Formatter la date
                    String dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.FRANCE).format(art.getDateFinEncheres());
                    req.setAttribute("dateFinEnchere", dateFormatter);
                    //Si une adresse est renseignée dans l'objet Retrait
                    if (art.getRetrait().getRue() != null) {
                        req.setAttribute("rue", art.getRetrait().getRue());
                        req.setAttribute("CP", art.getRetrait().getCodePostal());
                        req.setAttribute("ville", art.getRetrait().getVille());
                    } else {
                        req.setAttribute("rue", art.getUtilisateur().getRue());
                        req.setAttribute("CP", art.getUtilisateur().getCodePostal());
                        req.setAttribute("ville", art.getUtilisateur().getVille());
                    }
                    req.setAttribute("vendeur", art.getUtilisateur().getPseudo());
                    //Si offre en cours
                    if(offre != 0){
                        req.setAttribute("Proposition", offre + 1);
                    }else{
                        req.setAttribute("Proposition",art.getPrixInitial() + 1);
                    }
                }
                //Affichage de la page
                req.getRequestDispatcher("WEB-INF/html/PageEncherir.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("WEB-INF/html/PageAccueilEnchere.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Gestion du bouton Enchérir
        Enchere enchere = new Enchere();
        Timestamp ts = Timestamp.from(Instant.now());
        enchere.setDateEnchere(ts);
        enchere.setIdUtilisateur(util.getIdUtilisateur());
        enchere.setIdArticle(Integer.parseInt((String) session.getAttribute("idArticle")));
        enchere.setMontantEnchere(Integer.parseInt(req.getParameter("maProposition")));
        //Vérification du crédit utilisateur par rapport à la proposition
        if (Integer.parseInt(req.getParameter("maProposition")) < util.getCredit()) {
            try {
                em.miseAJourEnchere(enchere);
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        } else {
            businessException.ajouterErreur(CodesResultatServlet.ENCHERE_MONTANT_ECHEC);
        }
        req.getRequestDispatcher("WEB-INF/html/PageAccueilEnchere.jsp").forward(req, resp);
    }
}
