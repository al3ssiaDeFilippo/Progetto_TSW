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

@WebServlet("/ProductControl")
@MultipartConfig
public class ProductControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static boolean isDataSource = true;

    static ProductModel model;

    static {
        model = new ProductModelDS();
    }

    public ProductControl() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dis = "/ProductView.jsp";

        String action = request.getParameter("action");
        System.out.println("action: " + action);

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
                        System.out.println("codeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee:" + code);
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
                        String fileName = Paths.get(photoPart.getSubmittedFileName()).getFileName().toString();
                        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                        if (!fileExtension.equalsIgnoreCase("jpg")) {
                            System.out.println("Errore: Il file deve essere in formato .jpg");
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
                        photo = new javax.sql.rowset.serial.SerialBlob(photoBytes);
                    }

                    String frame = request.getParameter("frame");
                    String frameColor = request.getParameter("frameColor");
                    String size = request.getParameter("size");

                    if("default".equals(frame)) {
                        frame = "default";
                    }
                    if("default".equals(frameColor)) {
                        frameColor = "default";
                    }
                    if("default".equals(size)) {
                        size = "default";
                    }

                    ProductBean bean = new ProductBean();
                    bean.setProductName(productName);
                    bean.setDetails(details);
                    bean.setQuantity(quantity);
                    bean.setCategory(category);
                    bean.setPrice(price);
                    bean.setIva(iva);
                    bean.setDiscount(discount);
                    bean.setFrame(frame);
                    System.out.println("frame: " + frame);
                    bean.setFrameColor(frameColor);
                    System.out.println("frameColor: " + frameColor);
                    bean.setSize(size);
                    System.out.println("size: " + size);
                    bean.setPhoto(photo);
                    int productCode = model.doSave(bean);

                    /*modifiche*/

                    String directoryPath = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads";
                    try{
                        PhotoModel PM = new PhotoModel();
                        PM.addProductAndSaveImages(productCode,directoryPath);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    /*modifiche*/

                    System.out.println("Debug: action : insert! - Product inserted!");

                    HttpSession session = request.getSession();;
                    String nextPage = (String) request.getParameter("nextPage");

                    if (nextPage == null || nextPage.isEmpty()) {
                        nextPage = "ProductView.jsp"; // Pagina predefinita se nextPage non è impostata
                    } else {
                        session.setAttribute("nextPage", nextPage);
                    }
                    System.out.println("Debug: nextPage : " + nextPage);
                    response.sendRedirect(response.encodeRedirectURL(nextPage));
                    return;

                } else if(action.equalsIgnoreCase("edit")) {
                    int code = 0;
                    String codeParam = request.getParameter("code");
                    if (codeParam != null && !codeParam.isEmpty()) {
                        code = Integer.parseInt(request.getParameter("code"));
                        ProductBean product = model.doRetrieveByKey(code);
                        if(product != null) {
                            request.setAttribute("product", product);
                            dis = "/EditProductPage.jsp";
                        }
                    }
                } else if(action.equalsIgnoreCase("update")) {
                    int code = 0;
                    String codeParam = request.getParameter("code");

                    System.out.println("code: " + codeParam);

                    if (codeParam != null && !codeParam.isEmpty()) {
                        code = Integer.parseInt(request.getParameter("code"));
                        ProductBean product = model.doRetrieveByKey(code);
                        if (product != null) {
                            // Get all the new product data from the request
                            String productName = request.getParameter("productName");
                            System.out.println("productname: " + productName);
                            String details = request.getParameter("details");
                            int quantity = Integer.parseInt(request.getParameter("quantity"));
                            String category = request.getParameter("category");
                            float price = Float.parseFloat(request.getParameter("price"));
                            int iva = Integer.parseInt(request.getParameter("iva"));
                            int discount = Integer.parseInt(request.getParameter("discount"));

                            Part photoPart = request.getPart("photoPath");

                            System.out.println("photoPath productcontrol: " + photoPart);
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


                            // Set the new product data in the ProductBean object
                            product.setProductName(productName);
                            product.setDetails(details);
                            product.setQuantity(quantity);
                            product.setCategory(category);
                            product.setPrice(price);
                            product.setIva(iva);
                            product.setDiscount(discount);
                            product.setPhoto(photo);
                            System.out.println("photo riga 237:" + photo);

                            // Call model.edit with the ProductBean object
                            model.doUpdate(product);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("File ProductControl, line 225 - Error:" + e.getMessage());
        }

        String sort = request.getParameter("sort");

        try {
            request.removeAttribute("products");

            request.setAttribute("products", model.doRetrieveAll(sort));

        } catch (SQLException e) {
            System.out.println("File ProductControl, line 234 - Error:" + e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(dis);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}