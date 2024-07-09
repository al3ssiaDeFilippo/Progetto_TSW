package main.javas.control.Product;

import main.javas.model.Product.PhotoModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class GetProductImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        System.out.println("action GetProductImageServlet: " + action);

        if ("get".equals(action)) {
            try {
                int code = Integer.parseInt(req.getParameter("code"));
                System.out.println("Product code: " + code);

                OutputStream out = resp.getOutputStream();
                resp.setContentType("image/jpg");

                byte[] imageBytes = null;

                // Get frame and frameColor from the request
                String frame = req.getParameter("frame");
                String frameColor = req.getParameter("frameColor");
                System.out.println("Frame: " + frame);
                System.out.println("FrameColor: " + frameColor);

                // Prende le foto customizzate dal db
                imageBytes = PhotoModel.getCustomPhotos(code, frame, frameColor);
                System.out.println("Custom photo length: " + (imageBytes != null ? imageBytes.length : "null"));

                // Prende la foto standard dal db
                if (imageBytes == null || imageBytes.length == 0) {
                    imageBytes = PhotoModel.getImageByNumeroSerie(code);
                    System.out.println("Standard photo length: " + (imageBytes != null ? imageBytes.length : "null"));
                }

                if (imageBytes != null && imageBytes.length > 0) {
                    out.write(imageBytes);
                    out.flush();
                } else {
                    System.out.println("No image found for product code: " + code);
                }

                out.close();
            } catch (NumberFormatException | IOException e) {
                System.err.println("Error processing request: " + e.getMessage());
            }
        }
    }
}
