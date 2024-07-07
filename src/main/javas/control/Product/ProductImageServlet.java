package main.javas.control.Product;

import main.javas.model.Product.PhotoModel;
import main.javas.bean.ProductBean;
import main.javas.model.Product.ProductModelDS;

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
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

@WebServlet("/ProductImageServlet")
@MultipartConfig
public class ProductImageServlet extends HttpServlet {

    static ProductModelDS prMod = new ProductModelDS();
    static PhotoModel modelFoto = new PhotoModel();

    public ProductImageServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println("action productimageservlet: " + action);

        if ("get".equals(action)) {
            int code = Integer.parseInt(req.getParameter("code"));
            OutputStream out = null;
            resp.setContentType("image/jpg");
            out = resp.getOutputStream();

            byte[] imageBytes = null;


            // Get frame and frameColor from the request
            String frame = req.getParameter("frame");
            String frameColor = req.getParameter("frameColor");

            // Prende le foto customizzate dal db
            imageBytes = PhotoModel.getCustomPhotos(code, frame, frameColor);



            // Prende la foto standard dal db
            imageBytes = PhotoModel.getImageByNumeroSerie(code);


            out.write(imageBytes);

            out.flush();
            out.close();
        }
    }

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
            Blob blob = null;
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

            System.out.println("Debug: Redirecting to ProductView.jsp");
            resp.sendRedirect("InsertPhotosPage.jsp");
        }
    }
}