package main.javas.control.Order;

import main.javas.bean.UserBean;
import main.javas.model.Order.CartModel;
import main.javas.util.Carrello;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class ClearCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartModel cartModel = new CartModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello cart = getCart(session);
        UserBean user = (UserBean) session.getAttribute("user");

        try {
            cart.svuota();
            if (user != null) {
                cartModel.doDeleteAllByUser(user.getIdUser());
            }
            response.sendRedirect("carrello.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private Carrello getCart(HttpSession session) {
        Carrello cart = (Carrello) session.getAttribute("cart");
        UserBean user = (UserBean) session.getAttribute("user");
        if (user != null) {
            cart = (Carrello) session.getAttribute("loggedInCart");
            if (cart == null) {
                cart = new Carrello();
                session.setAttribute("loggedInCart", cart);
            }
        } else {
            if (cart == null) {
                cart = new Carrello();
                session.setAttribute("cart", cart);
            }
        }
        return cart;
    }
}
