package main.javas.control.Product;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import main.javas.model.Product.ProductModelDS;
import main.javas.bean.ProductBean;

import static java.lang.Integer.parseInt;

@MultipartConfig
public class UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductModelDS model = new ProductModelDS();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            try {
                updateProduct(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error updating product", e);
            }
        } else {
            response.sendRedirect("error.jsp"); // Redirect if action is not 'update'
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String codeParam = request.getParameter("code");
        if (codeParam == null || codeParam.isEmpty()) {
            response.sendRedirect("error.jsp"); // Redirect to error if code is missing
            return;
        }

        int code;
        try {
            code = parseInt(codeParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp"); // Redirect to error if code is invalid
            return;
        }

        String productName = request.getParameter("productName");
        String details = request.getParameter("details");
        int quantity = parseInteger(request.getParameter("quantity"));
        String category = request.getParameter("category");
        float price = (float) parseFloat(request.getParameter("price"));
        int iva = parseInteger(request.getParameter("iva"));
        int discount = parseInteger(request.getParameter("discount"));
        Part photoPart = request.getPart("photo");

        // Retrieve the existing product
        ProductBean product = model.doRetrieveByKey(code);
        if (product == null) {
            response.sendRedirect("error.jsp"); // Redirect to error if product is not found
            return;
        }

        // Update product details
        product.setProductName(productName);
        product.setDetails(details);
        product.setQuantity(quantity);
        product.setCategory(category);
        product.setPrice(price);
        product.setIva(iva);
        product.setDiscount(discount);

        // Handle the photo
        if (photoPart != null && photoPart.getSize() > 0) {
            try (InputStream photoInputStream = photoPart.getInputStream()) {
                // Create Blob from InputStream
                Blob photoBlob = new javax.sql.rowset.serial.SerialBlob(photoInputStream.readAllBytes());
                product.setPhoto(photoBlob); // Ensure ProductBean has this method
            } catch (Exception e) {
                throw new ServletException("Error handling photo upload", e);
            }
        }

        // Update product in the database
        model.doUpdate(product);

        // Redirect to success page or confirmation
        response.sendRedirect("success.jsp");
    }

    private static int parseInteger(String value) {
        try {
            return value != null && !value.isEmpty() ? parseInt(value) : 0;
        } catch (NumberFormatException e) {
            return 0; // Or any default value you prefer
        }
    }

    private static double parseFloat(String value) {
        try {
            return value != null && !value.isEmpty() ? Double.parseDouble(value) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0; // Or any default value you prefer
        }
    }
}
