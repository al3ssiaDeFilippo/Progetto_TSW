package main.javas.control;

import main.javas.model.PhotoModel;
import main.javas.model.ProductBean;
import main.javas.model.ProductModelDS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/GetPictureServlet")
public class GetPictureServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static ProductModelDS prMod = new ProductModelDS();

    public GetPictureServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String StringId = req.getParameter("id");
        String photoPath = req.getServletContext().getRealPath("/") + "Gallery/Giochi/" + req.getParameter("photoPath"); // Ottieni il percorso completo dell'immagine dal parametro della richiesta

        System.out.println(photoPath);

        if(StringId != null && photoPath != null) {
            int id = Integer.parseInt(StringId);
            if (id <= 0) {
                throw new IllegalArgumentException("Invalid product ID: " + id);
            }
            try {
                ProductBean product = prMod.doRetrieveByKey(id);
                System.out.println(product.getCode());
                if(product != null) {
                    PhotoModel.updatePhoto(product, photoPath); // Aggiorna l'immagine del prodotto nel database
                } else {
                    throw new NullPointerException("No product found with ID: " + id);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}