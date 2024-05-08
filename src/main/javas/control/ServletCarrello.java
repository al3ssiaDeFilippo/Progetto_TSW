package main.javas.control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.javas.model.ProductBean;
import main.javas.model.ProductModelDS;
import main.javas.util.Carrello;

import javax.servlet.annotation.WebServlet;

@WebServlet("/ServletCarrello")
public class ServletCarrello extends  HttpServlet {
    private static final long serialVersionUID = 1L;

    static ProductModelDS model = new ProductModelDS();

    public ServletCarrello() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dis;
        String action = req.getParameter("action");

        try {
            if (action != null) {
                if (action.equals("add")) {
                    System.out.println("Debug: action is add"); // Debug flag
                    int code = 0;
                    String codeParameter = req.getParameter("code");
                    if (codeParameter != null && !codeParameter.isEmpty()) {
                        code = Integer.parseInt(req.getParameter("code"));
                        req.removeAttribute("product");
                    }
                    ProductBean product = model.doRetrieveByKey(code);
                    System.out.println("Debug: retrieved product with code " + code); // Debug flag
                    req.setAttribute("product", product);
                    dis = req.getContextPath() + "/ProductView.jsp";
                    resp.sendRedirect(dis);

                    Carrello cart = (Carrello) req.getSession().getAttribute("cart");
                    if (cart == null) {
                        System.out.println("Debug: cart is null, creating a new one"); // Debug flag
                        cart = new Carrello();
                    }
                    cart.aggiungi(product);
                    System.out.println("Debug: added product to cart"); // Debug flag
                    req.getSession().setAttribute("cart", cart);

                } else if (action.equals("delete")) {
                    System.out.println("Debug: action is delete"); // Debug flag
                    int code = 0;
                    String codeParameter = req.getParameter("code");
                    if (codeParameter != null && !codeParameter.isEmpty()) {
                        code = Integer.parseInt(req.getParameter("code"));
                        //model.doDelete(code);
                        //System.out.println("Debug: deleted product with code " + code); // Debug flag
                    }
                    Carrello cart = (Carrello) req.getSession().getAttribute("cart");
                    if (cart != null) {
                        cart.rimuovi(code);
                        req.getSession().setAttribute("cart", cart);
                    }
                    resp.sendRedirect("carrello.jsp");
                }
            }
        }catch(SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}