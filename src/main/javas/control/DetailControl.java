package main.javas.control;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import main.javas.model.ProductBean;
import main.javas.model.ProductModelDS;

@WebServlet("/DetailControl")
public class DetailControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        if(productId != null && !productId.isEmpty()) {
            try {
                ProductModelDS productModel = new ProductModelDS();
                ProductBean product = productModel.doRetrieveByKey(Integer.parseInt(productId));
                if (product != null) {
                    request.setAttribute("product", product);
                    Collection<?> products = productModel.doRetrieveAll(null);
                    request.setAttribute("products", products);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("DetailProductPage.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("error.jsp?message=Product+not+found");
                }
            } catch(Exception e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp?message=Error+occurred");
            }
        } else {
            response.sendRedirect("error.jsp?message=Product+ID+not+found");
        }
    }
}
