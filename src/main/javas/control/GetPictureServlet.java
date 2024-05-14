package main.javas.control;

import main.javas.model.OrderModel;
import main.javas.model.PhotoModel;
import main.javas.model.ProductBean;
import main.javas.model.ProductModelDS;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getPicture")
public class GetPictureServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static OrderModel model = new OrderModel();
    static ProductModelDS prMod = new ProductModelDS();

    public GetPictureServlet() {super();}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String StringId = (String) req.getParameter("id");
        if(StringId != null) {
            int id = Integer.parseInt(StringId);
            if (id <= 0) {
                throw new IllegalArgumentException("Invalid product ID: " + id);
            }
            try {
                ProductBean product = prMod.doRetrieveByKey(id);
                if(product != null) {
                    byte[] bt = PhotoModel.load(product);

                    ServletOutputStream out = resp.getOutputStream();
                    if(bt != null) {
                        out.write(bt);
                        resp.setContentType("image/jpg");
                    }
                    out.close();
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