package main.javas.control.Product;

import main.javas.bean.ProductBean;
import main.javas.model.Product.PhotoModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@MultipartConfig
public class AddProductImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            Part filePart = req.getPart("photo");
            InputStream fileInputStream = filePart.getInputStream();

            // Read the file data into a byte array
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = fileInputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] byteArray = buffer.toByteArray();

            // Create a Blob using the byte array
            Blob blob;
            try {
                blob = new javax.sql.rowset.serial.SerialBlob(byteArray);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String frame = req.getParameter("frame");

            String frameColor = req.getParameter("frameColor");

            String productId = req.getParameter("productCode");

            // Create a ProductBean object
            ProductBean product = new ProductBean();
            product.setCode(Integer.parseInt(productId));
            product.setFrame(frame);
            product.setFrameColor(frameColor);
            product.setPhoto(blob);  // Set the Blob here

            // Pass the ProductBean object to the doSavePhoto method
            PhotoModel photoModel = new PhotoModel();
            try {
                photoModel.doSavePhoto(product);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            resp.sendRedirect("InsertPhotosPage.jsp");
        }
    }
}
