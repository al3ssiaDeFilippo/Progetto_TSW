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

        if ("get".equals(action)) {
            int code = Integer.parseInt(req.getParameter("code"));
            OutputStream out = null;
            resp.setContentType("image/jpg");
            out = resp.getOutputStream();

            byte[] imageBytes = null;

            // Check if we should get custom photos
            String customParam = req.getParameter("custom");
            boolean custom = customParam != null && customParam.equals("true");

            if (custom) {
                // Get frame and frameColor from the request
                String frame = req.getParameter("frame");
                String frameColor = req.getParameter("frameColor");

                // Get custom photos
                imageBytes = modelFoto.getCustomPhotos(code, frame, frameColor);

            } else {
                // Get product photo
                imageBytes = modelFoto.getImageByNumeroSerie(code);
            }

            out.write(imageBytes);

            out.flush();
            out.close();

        } else if ("update".equals(action)) {
            String StringId = req.getParameter("id");
            String photoPath = req.getServletContext().getRealPath("/") + "Gallery/Giochi/" + req.getParameter("photoPath");

            if(StringId != null && photoPath != null) {
                int id = Integer.parseInt(StringId);
                if (id <= 0) {
                    throw new IllegalArgumentException("Invalid product ID: " + id);
                }
                try {
                    ProductBean product = prMod.doRetrieveByKey(id);
                    if(product != null) {
                        modelFoto.insertImage(product.getCode(), photoPath);
                    } else {
                        throw new NullPointerException("No product found with ID: " + id);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if(action.equalsIgnoreCase("updateCustomPhotos")) {
            int code = 0;
            String codeParam = req.getParameter("code");
            if (codeParam != null && !codeParam.isEmpty()) {
                code = Integer.parseInt(req.getParameter("code"));
                ProductBean product = null;
                try {
                    product = prMod.doRetrieveByKey(code);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if(product != null) {
                    String[] frames = {"frame1", "frame2", "frame3"}; // sostituisci con i tuoi frame
                    String[] frameColors = {"color1", "color2", "color3"}; // sostituisci con i tuoi colori del frame

                    for (String frame : frames) {
                        for (String frameColor : frameColors) {
                            // Ottieni la foto per la combinazione corrente di frame e colore del frame
                            Part photoPart = req.getPart(frame + "_" + frameColor);
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
                            }

                            // Aggiorna la foto per la combinazione corrente di frame e colore del frame
                            PhotoModel photoModel = new PhotoModel();
                            try {
                                photoModel.updateCustomPhoto(product, frame, frameColor, photo);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
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