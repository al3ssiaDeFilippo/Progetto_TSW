package main.javas.control.Product;

import main.javas.model.Product.ProductModel;
import main.javas.model.Product.ProductModelDS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModel model = new ProductModelDS();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int code = Integer.parseInt(request.getParameter("code"));
        try {
            model.disableForeignKeyCheck();
            model.doDelete(code);
            model.enableForeignKeyCheck();
            response.sendRedirect("ProductView.jsp");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Errore nell'eliminazione del prodotto.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProductView.jsp");
            dispatcher.forward(request, response);
        }
    }
}