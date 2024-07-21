package main.javas.control.Order;

import main.javas.bean.CartBean;
import main.javas.bean.ProductBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.CartModel;
import main.javas.model.Product.ProductModelDS;
import main.javas.util.Carrello;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartModel cartModel = new CartModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Carrello cart = (Carrello) session.getAttribute("cart");

        if (cart == null) {
            cart = new Carrello();
            session.setAttribute("cart", cart);
        }

        String codeStr = request.getParameter("code");

        if (codeStr != null && !codeStr.trim().isEmpty()) {
            try {
                int code = Integer.parseInt(codeStr);
                ProductModelDS productModel = new ProductModelDS();
                ProductBean item = productModel.doRetrieveByKey(code);

                if (item != null) {
                    CartBean cartItem = new CartBean();
                    cartItem.setProductCode(item.getCode());
                    cartItem.setQuantity(1);
                    cartItem.setFrame(request.getParameter("frame"));
                    // Utilizza il valore di frameColor direttamente nell'istruzione set
                    String frameColor = request.getParameter("frameColor");
                    cartItem.setFrameColor(frameColor == null || frameColor.trim().isEmpty() ? "no color" : frameColor);
                    cartItem.setSize(request.getParameter("size"));
                    cartItem.setPrice(item.getPrice());
                    cart.aggiungi(cartItem);

                    if (session.getAttribute("user") != null) {
                        cartItem.setIdUser(((UserBean) session.getAttribute("user")).getIdUser());
                        cartModel.doSave(cartItem);
                    }

                    response.sendRedirect("carrello.jsp");
                }
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            response.sendRedirect("HomePage.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}