package fr.eni.trocencheres.servlet;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bll.UtilisateurManager;
import fr.eni.trocencheres.bo.Utilisateur;
import fr.eni.trocencheres.messages.LecteurMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PageCreerCompte extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //TODO voir à effacer
        String s = LecteurMessage.getMessageErreur(30000);
        System.out.println(s);
        req.setAttribute("error", s);
        req.getRequestDispatcher("WEB-INF/html/PageCreerCompte.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("boutonAction").equals("annuler")) {
            resp.sendRedirect(req.getContextPath() + "/PageAccueilEnchere");
        } else {
            BusinessException businessException = new BusinessException();
            boolean creationCompteValide = false;

            Utilisateur util = new Utilisateur();
            util.setNom(req.getParameter("nom"));
            util.setPrenom(req.getParameter("prenom"));
            util.setPseudo(req.getParameter("pseudo"));

            util.setEmail(req.getParameter("email"));
            util.setTelephone(req.getParameter("tel"));

            util.setRue(req.getParameter("rue"));
            util.setVille(req.getParameter("ville"));
            util.setCodePostal(req.getParameter("CP"));


            if (req.getParameter("mdp").equals(req.getParameter("mdpC")) && req.getParameter("mdp")!="" && req.getParameter("mdp")!=null) {
                UtilisateurManager uM = new UtilisateurManager();
                try {
                    uM.ajouterUtilisateur(util);

                    util.setMotDePasse(req.getParameter("mdp"));
                    HttpSession session = req.getSession();
                    session.setAttribute("utilisateur", util);
                    creationCompteValide = true;
                } catch (BusinessException e) {
                    e.printStackTrace();
                    businessException.ajouterErreur(CodesResultatServlet.RECORD_UTILISATEUR);
                }
                finally {
                    if(businessException.possedeErreurs()){
                        req.setAttribute("listeCodesErreur", businessException.getListeCodesErreur());
                    }
                }

            } else {
                businessException.ajouterErreur(CodesResultatServlet.MDP_DIFFERENT);
                req.setAttribute("listeCodesErreur", businessException.getListeCodesErreur());
            }
            //si la création a échoué, retour à la page de saisie
            if (!creationCompteValide) {
                req.setAttribute("pseudo", req.getParameter("pseudo"));
                req.setAttribute("nom", req.getParameter("nom"));
                req.setAttribute("prenom", req.getParameter("prenom"));
                req.setAttribute("email", req.getParameter("email"));
                req.setAttribute("tel", req.getParameter("tel"));
                req.setAttribute("rue", req.getParameter("rue"));
                req.setAttribute("CP", req.getParameter("CP"));
                req.setAttribute("ville", req.getParameter("ville"));
                this.doGet(req, resp);
            }
            req.setCharacterEncoding("UTF-8");
            req.getRequestDispatcher("WEB-INF/html/PageAccueilEnchere.jsp").forward(req, resp);
        }
    }
}
