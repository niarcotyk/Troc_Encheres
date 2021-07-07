package fr.eni.trocencheres.ihm;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FiltreConnexion implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain isConnected) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpSession session = req.getSession();
        //Ce filtre redirige vers la page connexion s'il est déconnecté
        if(session.getAttribute("utilisateur")!=null){
        //tapper le code ici pour qu'il s'éxécute pendant le filtre
            isConnected.doFilter(req, servletResponse);
        }else{
            req.getRequestDispatcher("WEB-INF/html/PageConnexion.jsp").forward(req,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
