package main.javas.control.Product;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import main.javas.bean.ProductBean;
import main.javas.model.Product.ProductModelDS;

@WebServlet("/DetailControl")
public class DetailControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        if(productId != null && !productId.isEmpty()) {
            try {
                ProductModelDS productModel = new ProductModelDS();
                ProductBean product = productModel.doRetrieveByKey(Integer.parseInt(productId));
                if (product != null) {
                    // Retrieve product specifications from request parameters
                    String frame = request.getParameter("frame");
                    System.out.println("Frame: " + frame);
                    String frameColor = request.getParameter("frameColor");
                    System.out.println("Frame color: " + frameColor);
                    String size = request.getParameter("size");
                    System.out.println("Size: " + size);

                    // Set product specifications in the ProductBean object
                    if (frame != null) {
                        product.setFrame(frame);
                    }
                    if (frameColor != null) {
                        product.setFrameColor(frameColor);
                    }
                    if (size != null) {
                        product.setSize(size);
                    }

                    request.setAttribute("product", product);
                    Collection<?> products = productModel.doRetrieveAll(null);
                    request.setAttribute("products", products);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("DetailProductPage.jsp");
                    dispatcher.forward(request, response);
                } else {
                    throw new ServletException("Product not found");
                }
            } catch(Exception e) {
                throw new ServletException(e);
            }
        } else {
            throw new ServletException("Missing parameter");
        }
    }
}