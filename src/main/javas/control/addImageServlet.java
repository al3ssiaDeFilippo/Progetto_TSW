package main.javas.control;

import main.javas.model.ModelFoto;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/addImageServlet")
@MultipartConfig
public class addImageServlet extends HttpServlet {

    private static final String CARTELLA_UPLOAD ="upload";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ModelFoto modelFoto = new ModelFoto();
        Part filePart = request.getPart("foto1");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String destinazione = CARTELLA_UPLOAD + File.separator + fileName;
        Path pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));

        for (int i = 2; Files.exists(pathDestinazione); i++) {
            destinazione = CARTELLA_UPLOAD + File.separator + i + "_" + fileName;
            pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));
        }

        InputStream fileInputStream = filePart.getInputStream();

        int numeroSerie = Integer.parseInt(request.getParameter("numeroSerie1"));
        modelFoto.insertImage(numeroSerie, fileInputStream);

        // Qui puoi reindirizzare l'utente a una pagina diversa, ad esempio "home.jsp"
        response.sendRedirect("DetailProductPage.jsp");
    }
}