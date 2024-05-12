package main.javas.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.javas.model.CartBean;
import main.javas.util.Carrello;

import javax.servlet.annotation.WebServlet;

@WebServlet("/UpdateQuantityServlet")
public class UpdateQuantityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
                        item.setQuantity(quantity);
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
