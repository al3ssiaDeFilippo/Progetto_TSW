package main.javas.control;

import main.javas.model.*;
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
        System.out.println("Entering doPost method");

        HttpSession session = request.getSession();
        Carrello cart = (Carrello) session.getAttribute("cart");

        if (cart == null) {
            System.out.println("Cart is null, creating a new cart");
            cart = new Carrello();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        System.out.println("Action: " + action);

        if ("clear".equals(action)) {
            System.out.println("Clearing the cart");
            cart.svuota();
            response.sendRedirect("carrello.jsp");
        } else {
            String codeStr = request.getParameter("code");
            System.out.println("Product code: " + codeStr);

            if (codeStr != null && !codeStr.trim().isEmpty()) {
                try {
                    int code = Integer.parseInt(codeStr);
                    ProductModelDS productModel = new ProductModelDS();
                    ProductBean item = productModel.doRetrieveByKey(code);

                    if ("add".equals(action) && item != null) {
                        System.out.println("Adding product to cart");
                        CartBean cartItem = new CartBean();
                        cartItem.setProductCode(item.getCode());
                        cartItem.setQuantity(1);

                        String frame = request.getParameter("frame");
                        String frameColor = request.getParameter("frameColor");
                        String size = request.getParameter("size");

                        cartItem.setFrame(frame != null ? frame : item.getFrame());
                        cartItem.setFrameColor(frameColor != null ? frameColor : item.getFrameColor());
                        cartItem.setSize(size != null ? size : item.getSize());

                        // Always add a new row to the cart
                        cart.aggiungi(cartItem);

                        if (session.getAttribute("user") != null) {
                            System.out.println("User is logged in, saving cart item to database");
                            cartItem.setIdUser(((UserBean) session.getAttribute("user")).getIdUser());
                            cartModel.doSave(cartItem);
                        }

                        response.sendRedirect("ProductView.jsp");
                    } else if ("remove".equals(action) && item != null) {
                        System.out.println("Removing product from cart");
                        CartBean cartItem = new CartBean();
                        cartItem.setProductCode(item.getCode());
                        cart.rimuovi(cartItem);

                        if (session.getAttribute("user") != null) {
                            System.out.println("User is logged in, deleting cart item from database");
                            try {
                                cartModel.doDelete(item.getCode());
                            } catch (Exception e) {
                                System.out.println("Error deleting cart item from database, adding it back to the cart");
                                cart.aggiungi(cartItem);
                                throw e;
                            }
                        }

                        response.sendRedirect("carrello.jsp");
                    } else if ("updateQuantity".equals(action) && item != null) {
                        System.out.println("Updating product quantity in cart");
                        int quantity = Integer.parseInt(request.getParameter("quantity"));
                        if(quantity > item.getQuantity()) {
                            quantity = item.getQuantity();
                        }
                        cart.aggiornaQuantita(item.getCode(), quantity);

                        if (session.getAttribute("user") != null) {
                            System.out.println("User is logged in, saving updated cart item to database");
                            CartBean cartItem = new CartBean();
                            cartItem.setProductCode(item.getCode());
                            cartItem.setQuantity(quantity);
                            cartItem.setIdUser(((UserBean) session.getAttribute("user")).getIdUser());
                            cartModel.doSave(cartItem);
                        }

                        response.sendRedirect("carrello.jsp");
                    }
                } catch (Exception e) {
                    System.out.println("Exception caught: " + e.getMessage());
                    e.printStackTrace();
                    response.sendRedirect("errorpage.jsp");
                }
            } else {
                System.out.println("Product code is null or empty");
                response.sendRedirect("errorpage.jsp");
            }
        }

        System.out.println("Exiting doPost method");
    }
}