package main.javas.control.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/LogIn.jsp")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) { // se l'utente non è loggato, procedi normalmente
            chain.doFilter(req, res);
            System.out.println("User not logged in, proceeding normally");
        } else { // se l'utente è già loggato, reindirizza alla pagina home
            System.out.println("User already logged in, redirecting to ProductView.jsp");
            response.sendRedirect(request.getContextPath() + "/ProductView.jsp");
        }
    }
}