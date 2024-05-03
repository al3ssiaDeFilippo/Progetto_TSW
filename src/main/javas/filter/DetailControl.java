package main.javas.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import main.javas.model.ProductBean;
import main.javas.model.ProductModelDS;

public class DetailControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId"); // Ottieni l'ID del prodotto dalla richiesta GET

        // Verifica se l'ID del prodotto è stato passato correttamente
        if(productId != null && !productId.isEmpty()) {
            try {
                // Chiamata al metodo doRetrieveByKey di ProductModelDS per ottenere i dettagli del prodotto dal database
                ProductModelDS productModel = new ProductModelDS();
                ProductBean product = productModel.doRetrieveByKey(Integer.parseInt(productId));

                // Verifica se il prodotto è stato trovato nel database
                if (product != null) {
                    // Imposta l'attributo "product" nella richiesta con il prodotto ottenuto
                    request.setAttribute("product", product);

                    // Inoltra la richiesta alla pagina DetailProductPage.jsp per visualizzare i dettagli del prodotto
                    RequestDispatcher dispatcher = request.getRequestDispatcher("DetailProductPage.jsp");
                    dispatcher.forward(request, response);
                } else {
                    // Se il prodotto non è stato trovato nel database, reindirizza a una pagina di errore
                    response.sendRedirect("error.jsp");
                }
            } catch(Exception e) {
                e.printStackTrace();
                // Se si verifica un'eccezione durante il recupero dei dettagli del prodotto, reindirizza a una pagina di errore
                response.sendRedirect("error.jsp");
            }
        } else {
            // Se l'ID del prodotto non è stato passato, reindirizza a una pagina di errore
            response.sendRedirect("error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implementa il metodo doPost solo se necessario
        doGet(request, response);
    }
}
