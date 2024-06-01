package main.javas.control;

import main.javas.model.CartBean;
import main.javas.model.CartModel;
import main.javas.util.Carrello;
import main.javas.model.ProductModelDS;
import main.javas.model.ProductBean;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
            // Svuotiamo il carrello
            cart.svuota();
            // Reindirizziamo l'utente alla pagina del carrello
            response.sendRedirect("carrello.jsp");
        } else {
            String codeStr = request.getParameter("code"); // Codice del prodotto
            if (codeStr != null && !codeStr.trim().isEmpty()) {
                try {
                    int code = Integer.parseInt(codeStr); // Converti il codice da stringa a intero

                    // Creiamo un'istanza di ProductModelDS per accedere ai dati dei prodotti
                    ProductModelDS productModel = new ProductModelDS();

                    // Otteniamo il prodotto dal database utilizzando il metodo doRetrieveByKey
                    ProductBean item = productModel.doRetrieveByKey(code);

                    if ("add".equals(action) && item != null) {
                        // Aggiungiamo il prodotto al carrello
                        CartBean cartItem = new CartBean();
                        cartItem.setCode(item.getCode());
                        cartItem.setQuantity(1); // Impostiamo la quantità a 1 per ora, puoi cambiarla a seconda delle tue esigenze
                        cartItem.setPrice(item.getPrice()); // Assumiamo che il prezzo nel carrello sia lo stesso del prodotto
                        cart.aggiungi(cartItem);

                        response.sendRedirect("ProductView.jsp");
                    } else if ("remove".equals(action) && item != null) {
                        // Rimuoviamo il prodotto dal carrello
                        CartBean cartItem = new CartBean();
                        cartItem.setCode(item.getCode());
                        cart.rimuovi(cartItem);

                        response.sendRedirect("carrello.jsp");
                    } else if ("updateQuantity".equals(action) && item != null) {
                        // Aggiorniamo la quantità del prodotto nel carrello
                        int quantity = Integer.parseInt(request.getParameter("quantity"));
                        cart.aggiornaQuantita(item.getCode(), quantity);
                        cartModel.updateQuantity(item.getCode(), quantity);

                        response.sendRedirect("carrello.jsp");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("errorpage.jsp");
                }
            } else {
                response.sendRedirect("errorpage.jsp");
            }
        }
    }
}
