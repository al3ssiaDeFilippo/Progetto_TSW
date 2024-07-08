package main.javas.control.Order;

import main.javas.bean.CartBean;
import main.javas.model.Order.CartModel;
import main.javas.bean.ProductBean;
import main.javas.model.Product.ProductModelDS;
import main.javas.bean.UserBean;
import main.javas.util.Carrello;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ServletCarrello")
public class ServletCarrello extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartModel cartModel = new CartModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
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

        String action = request.getParameter("action");

        try {
            if ("clear".equals(action)) {
                cart.svuota();
                if (user != null) {
                    cartModel.doDeleteAllByUser(user.getIdUser());
                }
                response.sendRedirect("carrello.jsp");
            } else {
                String codeStr = request.getParameter("code");

                if (codeStr != null && !codeStr.trim().isEmpty()) {
                    int code = Integer.parseInt(codeStr);
                    ProductModelDS productModel = new ProductModelDS();
                    ProductBean item = productModel.doRetrieveByKey(code);

                    if (item != null) {
                        switch (action) {
                            case "add":
                                handleAddToCart(cart, user, item, request);
                                response.sendRedirect("ProductView.jsp");
                                break;
                            case "remove":
                                handleRemoveFromCart(cart, user, item);
                                response.sendRedirect("carrello.jsp");
                                break;
                            case "updateQuantity":
                                handleUpdateQuantity(cart, user, item, request, response);
                                break;
                            default:
                                response.sendRedirect("carrello.jsp");
                                break;
                        }
                    } else {
                        response.sendRedirect("carrello.jsp");
                    }
                } else {
                    response.sendRedirect("carrello.jsp");
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void handleAddToCart(Carrello cart, UserBean user, ProductBean item, HttpServletRequest request) throws Exception {
        CartBean cartItem = new CartBean();
        cartItem.setProductCode(item.getCode());
        cartItem.setQuantity(1);
        cartItem.setFrame(request.getParameter("frame"));
        cartItem.setFrameColor(request.getParameter("frameColor"));
        cartItem.setSize(request.getParameter("size"));
        cartItem.setPrice(item.getPrice());
        cart.aggiungi(cartItem);

        if (user != null) {
            cartItem.setIdUser(user.getIdUser());
            cartModel.doSave(cartItem);
        }
    }

    private void handleRemoveFromCart(Carrello cart, UserBean user, ProductBean item) throws Exception {
        CartBean cartItem = new CartBean();
        cartItem.setProductCode(item.getCode());
        cart.rimuovi(cartItem);

        if (user != null) {
            cartModel.doDelete(item.getCode());
        }
    }

    private void handleUpdateQuantity(Carrello cart, UserBean user, ProductBean item, HttpServletRequest request, HttpServletResponse response) throws Exception {
    int quantity = Integer.parseInt(request.getParameter("quantity"));
    if (quantity > item.getQuantity()) {
        quantity = item.getQuantity();
    }
    cart.aggiornaQuantita(item.getCode(), quantity);

    if (user != null) {
        CartBean cartItem = new CartBean();
        cartItem.setProductCode(item.getCode());
        cartItem.setQuantity(quantity);
        cartItem.setIdUser(user.getIdUser());
        cartModel.doSave(cartItem);
    }

    // Calcola i prezzi totali con e senza sconto
    float totalPrice = cart.getCartTotalPriceWithDiscount(cartModel);
    float totalPriceWithoutDiscount = cart.getCartTotalPriceWithoutDiscount(cartModel);
    float totalDiscount = cart.getTotalDiscount(cartModel);

    // Prepara la risposta JSON
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write("{\"success\":true, \"totalPrice\":\"" + totalPrice + "\", \"totalPriceWithoutDiscount\":\"" + totalPriceWithoutDiscount + "\", \"totalDiscount\":\"" + totalDiscount + "\"}");
}

}