package fr.eni.trocencheres.servlet;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bll.UtilisateurManager;
import fr.eni.trocencheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PageModifierProfil extends HttpServlet {

    private final UtilisateurManager um = new UtilisateurManager();
    private final BusinessException businessException = new BusinessException();
    Utilisateur util = null;

    /**
     * Affichage des données du profil de l'utilisateur connecté
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Récupère la session
        HttpSession session = req.getSession();
        util = (Utilisateur) session.getAttribute("utilisateur");
        //Si utilisateur est connecté
        if(session.getAttribute("utilisateur") != null ) {
            try {
                Utilisateur utilEnCours = um.choisirUtilisateur(util.getIdUtilisateur());
                //Affichage des informations présentes dans la base de données
                req.setAttribute("pseudo", utilEnCours.getPseudo());
                req.setAttribute("nom", utilEnCours.getNom());
                req.setAttribute("prenom", utilEnCours.getPrenom());
                req.setAttribute("email", utilEnCours.getEmail());
                req.setAttribute("telephone", utilEnCours.getTelephone());
                req.setAttribute("rue", utilEnCours.getRue());
                req.setAttribute("CP", utilEnCours.getCodePostal());
                req.setAttribute("ville", utilEnCours.getVille());
                req.setAttribute("mdp", utilEnCours.getMotDePasse()); // Ne pas afficher le message
                req.setAttribute("credit",utilEnCours.getCredit());
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("WEB-INF/html/PageModifierProfil.jsp").forward(req, resp);
    }

    /**
     * Récupération des données modifiées et envoi en BDD avec contrôles
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Récupère la session
        HttpSession session = req.getSession();
        util = (Utilisateur) session.getAttribute("utilisateur");
        //Si utilisateur est connecté
        if (session.getAttribute("utilisateur") != null) {
            //Récupération des différents champs afin de faire un update
            int idUtilisateur = util.getIdUtilisateur();
            String pseudo = req.getParameter("pseudo");
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String email = req.getParameter("email");
            String tel = req.getParameter("telephone");
            String rue = req.getParameter("rue");
            String cP = req.getParameter("CP");
            String ville = req.getParameter("ville");
            String motPasse = "";
            //si mot de passe actuel différent de nouveau Mot de passe et que confirmation Mot de passe est rempli
            if ((req.getParameter("mdpa") != req.getParameter("nmdp")) && req.getParameter("cmdp") != "") {
                if (req.getParameter("nmdp") == req.getParameter("cmdp")) {
                    motPasse = req.getParameter("nmdp");
                } else {
                    businessException.ajouterErreur(CodesResultatServlet.REGLE_UTILISATEUR_NOUVEAUMOTDEPASSE_ERREUR);
                }
            }
            if (req.getParameter("nmdp") == null) {
                motPasse = req.getParameter("mdpa");
            }
            Boolean admin = false;
            //Création de l'objet utilisateur
            Utilisateur utilUpdate = new Utilisateur(idUtilisateur, pseudo, nom, prenom, email, tel, rue, cP, ville, motPasse, admin);
            //Update
            try {
                um.miseAJourUtilisateur(utilUpdate);
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("WEB-INF/html/PageMonProfil.jsp").forward(req, resp);
    }
}
