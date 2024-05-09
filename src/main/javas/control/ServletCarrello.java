package main.javas.control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.javas.model.CartBean;
import main.javas.util.Carrello;
import main.javas.model.OrderModel;

import javax.servlet.annotation.WebServlet;

@WebServlet("/ServletCarrello")
public class ServletCarrello extends  HttpServlet {
    private static final long serialVersionUID = 1L;

    //il campo model viene utilizzato per interagire con il db
    static OrderModel model = new OrderModel();

    public ServletCarrello() {
        super();
    }
//Metodo che gestisce le richieste HTTP di tipo GET
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String action = req.getParameter("action");

    try {
        if (action != null) {

            if (action.equals("add")) {
                //va a prendere il codice del prodotto
                int code = 0;
                String codeParameter = req.getParameter("code");
                if (codeParameter != null && !codeParameter.isEmpty()) {
                    code = Integer.parseInt(req.getParameter("code"));
                }
                System.out.println("Code --> "+code);
                CartBean product = model.doRetrieveByKey(code);
                req.setAttribute("product", product);
                String dis = req.getContextPath() + "/ProductView.jsp";
                resp.sendRedirect(dis);

                Carrello cart = (Carrello) req.getSession().getAttribute("cart");
                if (cart == null) {
                    cart = new Carrello();
                }
                cart.aggiungi(product);
                req.getSession().setAttribute("cart", cart);

            } else if (action.equals("delete")) {
                int code = 0;
                String codeParameter = req.getParameter("code");
                if (codeParameter != null && !codeParameter.isEmpty()) {
                    code = Integer.parseInt(req.getParameter("code"));
                }
                Carrello cart = (Carrello) req.getSession().getAttribute("cart");
                if (cart != null) {
                    CartBean product = model.doRetrieveByKey(code);
                    cart.rimuovi(product);
                    req.getSession().setAttribute("cart", cart);
                }
                req.getRequestDispatcher("/carrello.jsp").forward(req, resp);

            }
        }
    } catch(SQLException e) {
        throw new ServletException(e);
    }
}

    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}