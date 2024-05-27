package main.javas.control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.javas.model.CartBean;
import main.javas.model.ProductBean;
import main.javas.util.Carrello;
import main.javas.model.CartModel;

import javax.servlet.annotation.WebServlet;

@WebServlet("/UpdateQuantityServlet")
public class UpdateQuantityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static CartModel model = new CartModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ottieni i parametri dalla richiesta
        String codeParam = request.getParameter("code");
        String quantityParam = request.getParameter("quantity");

        if (codeParam != null && quantityParam != null) {
            int code = Integer.parseInt(codeParam);
            int quantity = Integer.parseInt(quantityParam);

            // Ottieni il carrello dalla sessione
            Carrello carrello = (Carrello) request.getSession().getAttribute("cart");
            if (carrello != null) {
                // Trova il prodotto nel carrello e aggiorna la quantità
                for (CartBean item : carrello.getProdotti()) {
                    if (item.getCode() == code) {
                        ProductBean productInCart = new ProductBean();
                        productInCart.setCode(code);
                        try {
                            int availableQuantity = model.getAvailableQuantity(code);
                            if (quantity > availableQuantity) {
                                request.setAttribute("errorMessage", "Quantità richiesta superiore alla quantità disponibile");
                                item.setQuantity(availableQuantity);
                                model.updateQuantity(productInCart, availableQuantity);
                                request.getRequestDispatcher("/carrello.jsp").forward(request, response);
                                return;
                            } else if (quantity == 0) {
                                model.doDelete(productInCart); // Elimina il prodotto se la quantità è 0
                                carrello.rimuovi(item); // Rimuovi il prodotto dal carrello
                            } else {
                                model.updateQuantity(productInCart, quantity);
                                item.setQuantity(quantity); // Aggiorna la quantità nel carrello
                            }
                        } catch (SQLException e) {
                            throw new ServletException(e);
                        }
                        break;
                    }
                }
                // Aggiorna il carrello nella sessione
                request.getSession().setAttribute("cart", carrello);
            }
        }
        // Reindirizza alla pagina del carrello dopo l'aggiornamento
        response.sendRedirect(request.getContextPath() + "/carrello.jsp");
    }
}