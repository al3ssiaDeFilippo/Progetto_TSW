package main.javas.control.Product;

import main.javas.bean.ProductBean;
import main.javas.model.Product.ProductModel;
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
import java.sql.Blob;
import java.sql.SQLException;

@MultipartConfig
public class UpdateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductModel model = new ProductModelDS();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        int code = Integer.parseInt(request.getParameter("code"));

        ProductBean product = null;
        try {
            product = model.doRetrieveByKey(code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (action.equalsIgnoreCase("edit")) {
            if (product != null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/EditProductPage.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/ProductNotFound.jsp");
            }
        } else if (action.equalsIgnoreCase("update")) {
            if (product != null) {
                try {
                    // Prendi i dati aggiornati dal form
                    String productName = request.getParameter("productName");
                    String details = request.getParameter("details");
                    String category = request.getParameter("category");

                    int quantity = 0;
                    String quantityParam = request.getParameter("quantity");
                    if (quantityParam != null && !quantityParam.isEmpty()) {
                        quantity = Integer.parseInt(quantityParam);
                    }

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

                    // Gestione dell'immagine del prodotto
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
                    } else {
                        String currentPhoto = request.getParameter("currentPhoto");
                        if (currentPhoto != null && currentPhoto.equals("true")) {
                            photo = product.getPhoto();
                        }
                    }

                    // Aggiorna l'oggetto ProductBean con i nuovi valori
                    product.setProductName(productName);
                    product.setDetails(details);
                    product.setQuantity(quantity);
                    product.setCategory(category);
                    product.setPrice(price);
                    product.setIva(iva);
                    product.setDiscount(discount);
                    product.setPhoto(photo);

                    // Esegui l'aggiornamento nel database
                    model.doUpdate(product);

                    // Reindirizza alla vista del prodotto aggiornato
                    response.sendRedirect(request.getContextPath() + "/ProductView.jsp?code=" + code);
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/ProductNotFound.jsp");
            }
        }
    }
}