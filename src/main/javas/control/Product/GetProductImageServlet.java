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

        if ("get".equals(action)) {
            try {
                int code = Integer.parseInt(req.getParameter("code"));

                OutputStream out = resp.getOutputStream();
                resp.setContentType("image/jpg");

                byte[] imageBytes = null;

                // Get frame and frameColor from the request
                String frame = req.getParameter("frame");
                String frameColor = req.getParameter("frameColor");

                // Prende le foto customizzate dal db
                imageBytes = PhotoModel.getCustomPhotos(code, frame, frameColor);

                // Prende la foto standard dal db
                if (imageBytes == null || imageBytes.length == 0) {
                    imageBytes = PhotoModel.getImageByNumeroSerie(code);
                }

                if (imageBytes != null && imageBytes.length > 0) {
                    out.write(imageBytes);
                    out.flush();
                }

                out.close();
            } catch (NumberFormatException | IOException e) {
                throw new ServletException(e);
            }
        }
    }
}
