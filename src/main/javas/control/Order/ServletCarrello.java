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

        if (cart == null) {
            cart = new Carrello();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");

        if ("clear".equals(action)) {
            cart.svuota();
            if(session.getAttribute("user") != null) {
                try {
                    cartModel.doDeleteAllByUser(((UserBean) session.getAttribute("user")).getIdUser());
                } catch (Exception e) {
                    response.sendRedirect("../errorPages/error500.jsp");
                    return;
                }
            }
            response.sendRedirect("carrello.jsp");
        } else {
            String codeStr = request.getParameter("code");

            if (codeStr != null && !codeStr.trim().isEmpty()) {
                try {
                    int code = Integer.parseInt(codeStr);
                    ProductModelDS productModel = new ProductModelDS();
                    ProductBean item = productModel.doRetrieveByKey(code);

                    if ("add".equals(action) && item != null) {
                        CartBean cartItem = new CartBean();
                        cartItem.setProductCode(item.getCode());
                        cartItem.setQuantity(1);
                        cartItem.setFrame(request.getParameter("frame"));
                        cartItem.setFrameColor(request.getParameter("frameColor"));
                        cartItem.setSize(request.getParameter("size"));
                        cartItem.setPrice(item.getPrice());
                        cart.aggiungi(cartItem);

                        if (session.getAttribute("user") != null) {
                            cartItem.setIdUser(((UserBean) session.getAttribute("user")).getIdUser());
                            cartModel.doSave(cartItem);
                        }

                        response.sendRedirect("ProductView.jsp");
                    } else if ("remove".equals(action) && item != null) {
                        CartBean cartItem = new CartBean();
                        cartItem.setProductCode(item.getCode());
                        cart.rimuovi(cartItem);

                        if (session.getAttribute("user") != null) {
                            try {
                                cartModel.doDelete(item.getCode());
                            } catch (Exception e) {
                                cart.aggiungi(cartItem);
                                response.sendRedirect("../errorPages/error500.jsp");
                                return;
                            }
                        }

                        response.sendRedirect("carrello.jsp");
                    } else if ("updateQuantity".equals(action) && item != null) {
                        int quantity = Integer.parseInt(request.getParameter("quantity"));
                        if(quantity > item.getQuantity()) {
                            quantity = item.getQuantity();
                        }
                        cart.aggiornaQuantita(item.getCode(), quantity);

                        if (session.getAttribute("user") != null) {
                            CartBean cartItem = new CartBean();
                            cartItem.setProductCode(item.getCode());
                            cartItem.setQuantity(quantity);
                            cartItem.setIdUser(((UserBean) session.getAttribute("user")).getIdUser());
                            cartModel.doSave(cartItem);
                        }

                        float totalPrice = cartModel.getDiscountedTotalPrice(cart.getProdotti());
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"success\":true, \"totalPrice\":\"" + totalPrice + "\"}");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("../errorPages/error500.jsp");
                }
            } else {
                response.sendRedirect("../errorPages/error500.jsp");
            }
        }
    }
}

