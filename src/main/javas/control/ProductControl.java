package main.javas.control;

import main.javas.model.*;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/ProductControl")
@MultipartConfig
public class ProductControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static boolean isDataSource = true;

    static ProductModel model;

    static {
        if (isDataSource) {
            model = new ProductModelDS();
        } else {
            model = new ProductModelDM();
        }
    }

    public ProductControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dis = "/ProductView.jsp";

        String action = request.getParameter("action");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("read")) {
                    int code = 0;
                    String codeParam = request.getParameter("code");
                    if (codeParam != null && !codeParam.isEmpty()) {
                        code = Integer.parseInt(request.getParameter("code"));
                        request.removeAttribute("product");
                    }

                    request.setAttribute("product", model.doRetrieveByKey(code));
                    dis = "/DetailProductPage.jsp";

                } else if (action.equalsIgnoreCase("delete")) {
                    int code = 0;
                    String codeParam = request.getParameter("code");
                    if (codeParam != null && !codeParam.isEmpty()) {
                        code = Integer.parseInt(request.getParameter("code"));
                        model.doDelete(code);
                    }

                } else if (action.equalsIgnoreCase("insert")) {

                    System.out.println("Debug: action : insert!");

                    String productName = request.getParameter("productName");
                    String details = request.getParameter("details");

                    int quantity = 0;
                    String quantityParam = request.getParameter("quantity");
                    if (quantityParam != null && !quantityParam.isEmpty()) {
                        quantity = Integer.parseInt(request.getParameter("quantity"));
                    }
                    String category = request.getParameter("category");

                    float price = 0;
                    String priceParam = request.getParameter("price");
                    if (priceParam != null && !priceParam.isEmpty()) {
                        price = Float.parseFloat(request.getParameter("price"));
                    }

                    String ivaParam = request.getParameter("iva");
                    int iva = 0;
                    if (ivaParam != null && !ivaParam.isEmpty()) {
                        iva = Integer.parseInt(ivaParam);
                    }

                    int discount = 0;
                    String discountParam = request.getParameter("discount");
                    if (discountParam != null && !discountParam.isEmpty()) {
                        discount = Integer.parseInt(request.getParameter("discount"));
                    }

                    Part photoPart = request.getPart("photoPath");
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
                        photo = new javax.sql.rowset.serial.SerialBlob(photoBytes);
                    }

                    String frame = request.getParameter("frame");
                    String frameColor = request.getParameter("frameColor");
                    String size = request.getParameter("size");

                    ProductBean bean = new ProductBean();
                    bean.setProductName(productName);
                    bean.setDetails(details);
                    bean.setQuantity(quantity);
                    bean.setCategory(category);
                    bean.setPrice(price);
                    bean.setIva(iva);
                    bean.setDiscount(discount);
                    bean.setFrame(frame);
                    bean.setFrameColor(frameColor);
                    bean.setSize(size);
                    bean.setPhoto(photo);
                    model.doSave(bean);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        String sort = request.getParameter("sort");

        try {
            request.removeAttribute("products");

            request.setAttribute("products", model.doRetrieveAll(sort));

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(dis);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
