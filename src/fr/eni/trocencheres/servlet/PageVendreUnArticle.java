package fr.eni.trocencheres.servlet;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bll.ArticleManager;
import fr.eni.trocencheres.bll.CategorieManager;
import fr.eni.trocencheres.bll.RetraitManager;
import fr.eni.trocencheres.bo.Article;
import fr.eni.trocencheres.bo.Categorie;
import fr.eni.trocencheres.bo.Retrait;
import fr.eni.trocencheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PageVendreUnArticle extends HttpServlet {
    List<Categorie> listeCategories = null;
    CategorieManager categorieManager = new CategorieManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Session
        HttpSession session = req.getSession();
        CategorieManager cm = new CategorieManager();
        LocalDate dateDuJour = java.time.LocalDate.now();
        req.setAttribute("dateDuJour", dateDuJour);
        listeCategories = cm.AfficherCategories();
        req.setAttribute("listeCategories",listeCategories);
        session.setAttribute("categorie",session.getAttribute("combo"));
        Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
        req.setAttribute("rue", util.getRue());
        req.setAttribute("CP", util.getCodePostal());
        req.setAttribute("ville", util.getVille());
        req.getRequestDispatcher("WEB-INF/html/PageVendreUnArticle.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
        String btn = req.getParameter("btn");
        if (btn.equals("annulerVente") ) {

        } else if (btn.equals("annuler")){
            req.getRequestDispatcher("WEB-INF/html/PageAccueilEnchere.jsp").forward(req, resp);
        } else if(btn.equals("enregistrer")) {
            Retrait retraitTest = new Retrait();
            Categorie categorie = new Categorie();
            Article newArticle = new Article();

            newArticle.setNomArticle(req.getParameter("nomArticle"));
            newArticle.setDescription(req.getParameter("description"));

            try {
                categorie = categorieManager.ChoisirCategorie(Integer.parseInt(req.getParameter("combo")));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
            newArticle.setPrixInitial(Integer.parseInt(req.getParameter("prix")));

            newArticle.setPhoto((req.getParameter("photo") != null)? req.getParameter("photo"): "");
            newArticle.setEtat_Article("nondisponible");

            //Formatage de Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateD = req.getParameter("dateDebut");
            String dateF = req.getParameter("dateFin");

            LocalDate dateDebut = LocalDate.parse(dateD, formatter);
            LocalDate dateFin = LocalDate.parse(dateF, formatter);

            newArticle.setDateDebutEncheres(dateDebut);
            newArticle.setDateFinEncheres(dateFin);

            newArticle.setUtilisateur(util);

            categorie.setLibelle(categorie.getLibelle());
            categorie.setNoCategorie(categorie.getNoCategorie());
            newArticle.setCategorie(categorie);

            retraitTest.setRue(req.getParameter("rue"));
            retraitTest.setCodePostal(req.getParameter("CP"));
            retraitTest.setVille(req.getParameter("ville"));

            ArticleManager articleManager = new ArticleManager();
            try {
                articleManager.ajouterArticle(newArticle);
            } catch (BusinessException e) {
                e.printStackTrace();
            }

            if (retraitTest.getRue() != util.getRue() || retraitTest.getCodePostal() != util.getCodePostal() || retraitTest.getVille() != util.getVille()){
                Retrait newRetrait = new Retrait();
                RetraitManager rm = new RetraitManager();
                newRetrait.setId(newArticle.getIdArticle());
                newRetrait.setRue(retraitTest.getRue());
                newRetrait.setCodePostal(retraitTest.getCodePostal());
                newRetrait.setVille(retraitTest.getVille());
                try {
                    rm.ajouterLieu(newRetrait);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }
            req.getRequestDispatcher("WEB-INF/html/PageAccueilEnchere.jsp").forward(req, resp);
        }
    }
}
