package main.javas.control.Product;

import main.javas.bean.ProductBean;
import main.javas.bean.UserBean;
import main.javas.model.Product.FavoritesModel;
import main.javas.model.Product.ProductModelDS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RemoveFromFavoriteServlet")
public class RemoveFromFavoriteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        if (user == null) {
            // Se l'utente non è loggato, reindirizza alla pagina di login
            response.sendRedirect(request.getContextPath() + "/LogIn.jsp");
            return;
        }

        String productCode = request.getParameter("productCode");
        ProductBean product = null;
        ProductModelDS productModel = new ProductModelDS();
        FavoritesModel favoritesModel = new FavoritesModel();

        try {
            product = productModel.doRetrieveByKey(Integer.parseInt(productCode));
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException("Errore durante il recupero del prodotto", e);
        }

        if (product == null) {
            throw new ServletException("Prodotto non trovato con il codice: " + productCode);
        }

        try {
            favoritesModel.doDelete(product, user);
        } catch (SQLException e) {
            throw new ServletException("Errore durante la rimozione dai preferiti", e);
        }

        // Reindirizzamento alla pagina dei preferiti dopo la rimozione
        response.sendRedirect(request.getContextPath() + "/Favorites.jsp");
    }
}