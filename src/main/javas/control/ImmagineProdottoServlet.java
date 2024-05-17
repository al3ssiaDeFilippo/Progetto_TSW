package main.javas.control;

import main.javas.model.ModelFoto;
import main.javas.model.OrderModel;
import main.javas.model.ProductModelDS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/ImmagineProdottoServlet")
public class ImmagineProdottoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static ModelFoto model = new ModelFoto();

    public ImmagineProdottoServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        ModelFoto modelFoto = new ModelFoto();

        int code =Integer.parseInt(request.getParameter("code"));
        System.out.println("Debug: code value is " + code); // Debug print

        OutputStream out=null;
        response.setContentType("image/jpg");
        out=response.getOutputStream();

        byte[] imageBytes = modelFoto.getImageByNumeroSerie(code);
        System.out.println("Debug: imageBytes length is " + (imageBytes != null ? imageBytes.length : "null")); // Debug print

        out.write(imageBytes);

        out.flush();
        out.close();
    }
}
