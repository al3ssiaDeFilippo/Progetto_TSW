package main.javas.model;
import main.javas.DriverManagerConnectionPool;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import main.javas.model.CartBean;
import main.javas.model.ProductBean;
import main.javas.util.Carrello;
import main.javas.model.OrderModel;


public static byte[] getImageByNumeroSerie(int numeroSerie) {
    try(Connection con=ConPool.getConnection()) {
        PreparedStatement ps= con.prepareStatement("SELECT foto FROM prodotto WHERE numeroSerie=?");
        ps.setInt(1,numeroSerie);
        ResultSet rs=ps.executeQuery();
        byte[] imageByte=null;
        if(rs.next()){
            Blob imageBlob=rs.getBlob("foto");
            imageByte=imageBlob.getBytes(1, (int) imageBlob.length());
        }
        return imageByte;
    }catch (SQLException e){
        throw new RuntimeException(e);
    }
}

public static void insertFoto(int numeroSerie, InputStream part) {
    try(Connection con=ConPool.getConnection()){
        PreparedStatement ps = con.prepareStatement("UPDATE prodotto SET foto=? WHERE numeroSerie=?");

        ps.setBlob(1, part);
        ps.setInt(2, numeroSerie);

        int result = ps.executeUpdate();

    }catch (SQLException e){
        throw new RuntimeException(e);
    }
}

    --------------------------------------------------------------------------------------------------------------------------
@WebServlet("/addImage")
@MultipartConfig
public class AddImage extends HttpServlet {
    private static final String CARTELLA_UPLOAD ="upload";
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("username").equals("admin@admin.com")) {
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
            ProdottoDAO.insertFoto(numeroSerie, fileInputStream);

            response.sendRedirect("admin.jsp");
        }
    }
}
--------------------------------------------------------------------------------------------------------------------------
@WebServlet("/immagineProdotto")
public class ImageProduct extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        int numeroSerie=Integer.parseInt(request.getParameter("numeroSerie"));

        OutputStream out=null;
        response.setContentType("image/img");
        out=response.getOutputStream();
        out.write(ProdottoDAO.getImageByNumeroSerie(numeroSerie));
        out.flush();
        out.close();
    }

}
-------------------------------------------------------------------------------------------------------------------------------
@WebServlet("/insertNewProduct")
@MultipartConfig
public class InsertNewProduct extends HttpServlet {
    private static final String CARTELLA_UPLOAD ="upload";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getSession().getAttribute("username").equals("admin@admin.com")) {
            Part filePart = request.getPart("foto");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            String destinazione = CARTELLA_UPLOAD + File.separator + fileName;
            Path pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));

            for (int i = 2; Files.exists(pathDestinazione); i++) {
                destinazione = CARTELLA_UPLOAD + File.separator + i + "_" + fileName;
                pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));
            }

            InputStream fileInputStream = filePart.getInputStream();

            int numeroSerie = Integer.parseInt(request.getParameter("numeroSerie"));
            String nome = request.getParameter("nome");
            String tipologia = request.getParameter("tipologia");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            String descrizione = request.getParameter("descrizione");
            String materiale = request.getParameter("materiale");

            ProdottoBean newProdottoBean = new ProdottoBean(numeroSerie, nome, tipologia, prezzo, descrizione);
            ProdottoDAO.doSave(newProdottoBean);

            ComposizioneBean composizioneBean=new ComposizioneBean(numeroSerie,materiale);
            ComposizioneDAO.doSave(composizioneBean);

            ProdottoDAO.insertFoto(numeroSerie, fileInputStream);

            response.sendRedirect("index.html");
        }else {
            response.sendRedirect("login.jsp");
        }
    }
}