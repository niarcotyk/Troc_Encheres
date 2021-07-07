package fr.eni.trocencheres.servlet;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bll.UtilisateurManager;
import fr.eni.trocencheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class PageConnexion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Vérifier si la coche Se souvenir de moi, à savoir si un Cookie existe déjà
        Cookie[] cookies = req.getCookies();
        String utilisateurCookie = null;
        if(cookies!= null){
            for(Cookie c: cookies){
                if(c.getName().equals("utilisateurCookie")){
                    //mettre en UTF-8
                    utilisateurCookie = java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                    req.setAttribute("utilisateurCookie", utilisateurCookie);
                    break;
                }
            }
        }
        req.getRequestDispatcher("WEB-INF/html/PageConnexion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UtilisateurManager um = new UtilisateurManager();
        Utilisateur util = null;
        String identifiant = req.getParameter("Identifiant").trim();
        String souvenir = req.getParameter("souvenir");

        //récupération de la checkBox souvenir
        if(souvenir != null){
            //création cookie avec encodage UTF-8
            Cookie c = new Cookie("utilisateurCookie", java.net.URLDecoder.decode(identifiant, "UTF-8"));
            c.setMaxAge(Integer.parseInt(getServletContext().getInitParameter("MAX_AGE_COOKIE"))); //valeur maxi en int
            c.setHttpOnly(true);
            resp.addCookie(c);
        }

        try {
            util = um.getUtilisateur(req.getParameter("Identifiant"), req.getParameter("MotDePasse"));
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        if (util != null) {
            HttpSession session = req.getSession();
            session.setAttribute("utilisateur", util);
            resp.sendRedirect(req.getContextPath() + "/PageAccueilEnchere");
        }else{
            //pas connecté
            doGet(req,resp);
        }

    }
}
