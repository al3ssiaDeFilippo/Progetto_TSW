package main.javas.control.Order;

import main.javas.bean.CartBean;
import main.javas.bean.ProductBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.CartModel;
import main.javas.model.Product.ProductModelDS;
import main.javas.util.Carrello;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartModel cartModel = new CartModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello cart = getCart(session);
        UserBean user = (UserBean) session.getAttribute("user");

        String codeStr = request.getParameter("code");
        if (codeStr != null && !codeStr.trim().isEmpty()) {
            try {
                int code = Integer.parseInt(codeStr);
                ProductModelDS productModel = new ProductModelDS();
                ProductBean item = productModel.doRetrieveByKey(code);
                if (item != null) {
                    CartBean cartItem = new CartBean();
                    cartItem.setProductCode(item.getCode());
                    cart.rimuovi(cartItem);

                    if (user != null) {
                        cartModel.doDelete(item.getCode());
                    }
                    response.sendRedirect("carrello.jsp");
                } else {
                    response.sendRedirect("carrello.jsp");
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            response.sendRedirect("carrello.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private Carrello getCart(HttpSession session) {
        Carrello cart = (Carrello) session.getAttribute("cart");
        if (cart == null) {
            cart = new Carrello();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
