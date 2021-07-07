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

public class SupprimerCompte extends HttpServlet {
    private static final UtilisateurManager um = new UtilisateurManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Récupére la session
        HttpSession session = req.getSession();

        //Si l'utilisateur n'est pas connecté, on le renvoie vers la page
        if (session.getAttribute("utilisateur") != null) {
            Utilisateur util = (Utilisateur) session.getAttribute("utilisateur");
            try {
                //Suppression utilisateur
                um.supprimer(util.getIdUtilisateur());
                //Invalide la session
                session.invalidate();
                //Redirection vers la page d'accueil
                req.getRequestDispatcher("WEB-INF/html/PageAccueilEnchere.jsp").forward(req, resp);

            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }else{
            //Affichage page d'erreur
            this.getServletContext().getRequestDispatcher("/WEB-INF/erreur418.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
