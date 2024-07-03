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

        /*Modifiche iniziano qui*/
        if ("clear".equals(action)) {
            System.out.println("Received request to clear cart");
            cart.svuota();
            if(session.getAttribute("user") != null) {
                System.out.println("User is logged in, deleting all cart items from database");
                try {
                    cartModel.doDeleteAllByUser(((UserBean) session.getAttribute("user")).getIdUser());
                } catch (Exception e) {
                    System.out.println("Error deleting all cart items from database, adding them back to the cart");
                    response.sendRedirect("../errorPages/error500.jsp");
                }
            }
            response.sendRedirect("carrello.jsp");
        /*Modifiche finiscono qui*/
        } else {
            String codeStr = request.getParameter("code");
            System.out.println("Product code: " + codeStr);

            if (codeStr != null && !codeStr.trim().isEmpty()) {
                try {
                    int code = Integer.parseInt(codeStr);
                    ProductModelDS productModel = new ProductModelDS();
                    ProductBean item = productModel.doRetrieveByKey(code);

                    if ("add".equals(action) && item != null) {
                        CartBean cartItem = new CartBean();
                        cartItem.setProductCode(item.getCode());
                        cartItem.setQuantity(1);

                        String frame = request.getParameter("frame");
                        String frameColor = request.getParameter("frameColor");
                        String size = request.getParameter("size");

                        cartItem.setFrame(frame != null ? frame : item.getFrame());

                        if(frameColor == null) {
                            cartItem.setFrameColor("no color");
                        } else {
                            cartItem.setFrameColor(frameColor);
                        }

                        cartItem.setSize(size != null ? size : item.getSize());

                        cartItem.setPrice(item.getPrice());
                        System.out.println("Product price: " + item.getPrice());
                        System.out.println("Cart item price: " + cartItem.getPrice());

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
                                response.sendRedirect("../errorPages/error500.jsp");
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
                    e.printStackTrace();
                    response.sendRedirect("../errorPages/error500.jsp");
                }
            } else {
                System.out.println("Product code is null or empty");
                response.sendRedirect("../errorPages/error500.jsp");
            }
        }
    }
}