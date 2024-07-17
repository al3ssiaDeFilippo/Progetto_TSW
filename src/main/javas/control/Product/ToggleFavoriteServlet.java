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

@WebServlet("/ToggleFavoriteServlet")
public class ToggleFavoriteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        if (user == null) {
            // Gestire il caso in cui l'utente non è loggato
            response.sendRedirect(request.getContextPath() + "/LogIn.jsp");
            return;
        }

        String productCode = request.getParameter("productCode");
        ProductBean product;
        ProductModelDS prodMod = new ProductModelDS();
        try {
            product = prodMod.doRetrieveByKey(Integer.parseInt(productCode));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FavoritesModel favMod = new FavoritesModel();
        try {
            if (favMod.isFavorite(product, user)) {
                favMod.doDelete(product, user);
                response.getWriter().write("removed");
            } else {
                favMod.doSave(product, user);
                response.getWriter().write("added");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
