package main.javas.control.Product;

import main.javas.bean.ProductBean;
import main.javas.model.Product.PhotoModel;
import main.javas.model.Product.ProductModel;
import main.javas.model.Product.ProductModelDS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;

@MultipartConfig
public class UploadImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModel model = new ProductModelDS();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int code = Integer.parseInt(request.getParameter("code"));
        Part photoPart = request.getPart("photoPath");
        Blob photo = null;

        if (photoPart != null && photoPart.getSize() > 0) {
            InputStream inputStream = photoPart.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] photoBytes = outputStream.toByteArray();
            try {
                photo = new javax.sql.rowset.serial.SerialBlob(photoBytes);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                ProductBean product = model.doRetrieveByKey(code);
                product.setPhoto(photo);
                model.doUpdate(product);
                String newImageUrl = "ProductImageServlet?action=get&code=" + code;
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"newImageUrl\": \"" + newImageUrl + "\"}");
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }
}