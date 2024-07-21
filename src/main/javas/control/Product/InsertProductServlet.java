package main.javas.control.Product;

import main.javas.bean.ProductBean;
import main.javas.model.Product.PhotoModel;
import main.javas.model.Product.ProductModel;
import main.javas.model.Product.ProductModelDS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;

@MultipartConfig
public class InsertProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModel model = new ProductModelDS();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Ensures the request encoding is set to UTF-8

        String productName = request.getParameter("productName");

        String details = request.getParameter("details");

        int quantity = 0;
        String quantityParam = request.getParameter("quantity");
        if (quantityParam != null && !quantityParam.isEmpty()) {
            quantity = Integer.parseInt(quantityParam);
        }

        String category = request.getParameter("category");

        float price = 0;
        String priceParam = request.getParameter("price");
        if (priceParam != null && !priceParam.isEmpty()) {
            price = Float.parseFloat(priceParam);
        }

        int iva = 0;
        String ivaParam = request.getParameter("iva");
        if (ivaParam != null && !ivaParam.isEmpty()) {
            iva = Integer.parseInt(ivaParam);
        }

        int discount = 0;
        String discountParam = request.getParameter("discount");
        if (discountParam != null && !discountParam.isEmpty()) {
            discount = Integer.parseInt(discountParam);
        }

        Part photoPart = request.getPart("photoPath");
        Blob photo = null;
        if (photoPart != null && photoPart.getSize() > 0) {
            String fileName = Paths.get(photoPart.getSubmittedFileName()).getFileName().toString();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!fileExtension.equalsIgnoreCase("jpg")) {
                request.setAttribute("errorMessage", "Errore: Il file deve essere in formato .jpg");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/InsertPage.jsp");
                dispatcher.forward(request, response);
                return;
            }

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

        int productCode = 0;
        try {
            productCode = model.doSave(bean);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String directoryPath = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads";
        try {
            PhotoModel PM = new PhotoModel();
            PM.addProductAndSaveImages(productCode, directoryPath);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        HttpSession session = request.getSession();
        String nextPage = request.getParameter("nextPage");

        if (nextPage == null || nextPage.isEmpty()) {
            nextPage = "ProductView.jsp"; // Default page if nextPage is not set
        } else {
            session.setAttribute("nextPage", nextPage);
        }
        response.sendRedirect(response.encodeRedirectURL(nextPage));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
