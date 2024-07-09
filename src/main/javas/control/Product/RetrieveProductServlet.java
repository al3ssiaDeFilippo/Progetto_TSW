package main.javas.control.Product;

import main.javas.model.Product.ProductModel;
import main.javas.model.Product.ProductModelDS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class RetrieveProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModel model = new ProductModelDS();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String dis = "/ProductView.jsp";

        try {
            if (action != null && action.equalsIgnoreCase("read")) {
                int code = Integer.parseInt(request.getParameter("code"));
                request.setAttribute("product", model.doRetrieveByKey(code));
                dis = "/DetailProductPage.jsp";
            } else {
                String sort = request.getParameter("sort");
                request.setAttribute("products", model.doRetrieveAll(sort));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(dis);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
