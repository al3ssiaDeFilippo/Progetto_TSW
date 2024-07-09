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
        System.out.println("Debug: Entered doPost method");

        String action = req.getParameter("action");
        System.out.println("Debug: Action = " + action);

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
            System.out.println("Debug: Frame = " + frame);

            String frameColor = req.getParameter("frameColor");
            System.out.println("Debug: Frame Color = " + frameColor);

            String productId = req.getParameter("productCode");
            System.out.println("Debug: Product ID = " + productId);

            // Create a ProductBean object
            ProductBean product = new ProductBean();
            product.setCode(Integer.parseInt(productId));
            product.setFrame(frame);
            product.setFrameColor(frameColor);
            product.setPhoto(blob);  // Set the Blob here

            // Pass the ProductBean object to the doSavePhoto method
            PhotoModel photoModel = new PhotoModel();
            try {
                System.out.println("Debug: Attempting to save photo");
                photoModel.doSavePhoto(product);
                System.out.println("Debug: Photo saved successfully");
            } catch (SQLException e) {
                System.out.println("Debug: Error while saving photo");
                throw new RuntimeException(e);
            }

            System.out.println("Debug: Redirecting to InsertPhotosPage.jsp");
            resp.sendRedirect("InsertPhotosPage.jsp");
        }
    }
}
