package main.javas.filter;

import main.javas.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ProductControl")
public class ProductControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ProductModelDS usa il DataSource
    // ProductModelDM usa il DriverManager
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
        String dis = "/ProductView.jsp"; //serve per fare l'invio dei dati alla view
        System.out.println(dis);

        //recupera il parametro action dalla richiesta HTTP
        String action = request.getParameter("action"); //action indica l'azione da eseguire sul prodotto (read, delete o insert)

        try {
            //se action è nullo, non viene eseguita nessuna azione
            if (action != null) {
                //controllo per determinare quale azione deve essere eseguita in base al suo valore
                if (action.equalsIgnoreCase("read")) {
                    //viene recuperato il parametro code dalla richiesta HTTP e viene convertito in un numero intero

                    int code = 0;
                    String codeParam = request.getParameter("code");
                    if (codeParam != null && !codeParam.isEmpty()) {
                        code = Integer.parseInt(request.getParameter("code"));
                        request.removeAttribute("product");
                    }
                    //viene chiamato il metodo doRetreiveByKey per recuperare il prodotto con il codice specificato
                    //e il prodotto viene impostato come attributo della richiesta con request.setAttribute
                    request.setAttribute("product", model.doRetrieveByKey(code));
                    dis = "/ProductView.jsp";

                } else if (action.equalsIgnoreCase("delete")) {
                    //viene recuperato il parametro code dalla richiesta
                    int code = 0;
                    String codeParam = request.getParameter("code");
                    if (codeParam != null && !codeParam.isEmpty()) {
                        code = Integer.parseInt(request.getParameter("code"));
                        model.doDelete(code);
                    }

                } else if (action.equalsIgnoreCase("insert")) {
                    String productName = request.getParameter("productName");
                    String details = request.getParameter("details");

                    int quantity = 0;
                    //controlli sull'attributo quantity
                    String quantityParam = request.getParameter("quantity");
                    if(quantityParam != null && !quantityParam.isEmpty()) {
                        quantity = Integer.parseInt(request.getParameter("quantity"));
                    }
                    String category = request.getParameter("category");

                    //controlli sull'attributo price
                    float price = 0;
                    String priceParam = request.getParameter("price");
                    if(priceParam != null && !priceParam.isEmpty()) {
                        price = Float.parseFloat(request.getParameter("price"));
                    }

                    int iva = Integer.parseInt(request.getParameter("iva"));

                    //controlli sull'attributo discount
                    int discount=0;
                    String discountParam = request.getParameter("discount");
                    if(discountParam != null && !discountParam.isEmpty()) {
                        discount = Integer.parseInt(request.getParameter("discount"));
                    }

                    //controlli sull'attributo frame
                    String frame = request.getParameter("frame");
                    String frameColor = request.getParameter("frameColor");
                    String size = request.getParameter("frameSize");

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
            System.out.println(model.doRetrieveAll(sort));
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
