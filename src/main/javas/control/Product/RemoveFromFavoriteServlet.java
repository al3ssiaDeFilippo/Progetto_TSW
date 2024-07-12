package main.javas.control.Product;

import main.javas.bean.ProductBean;
import main.javas.bean.UserBean;
import main.javas.model.Product.FavoritesModel;

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

        String productCode = request.getParameter("productCode");

        ProductBean product = new ProductBean();
        product.setCode(Integer.parseInt(productCode));

        FavoritesModel favoritesModel = new FavoritesModel();

        try {
            favoritesModel.doDelete(product, user);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("ProductView.jsp");
    }
}