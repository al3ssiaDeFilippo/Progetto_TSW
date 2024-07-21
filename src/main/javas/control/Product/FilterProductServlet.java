package main.javas.control.Product;

import main.javas.bean.ProductBean;
import main.javas.model.Product.ProductModelDS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/FilterProductServlet")
public class FilterProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ProductModelDS model = new ProductModelDS();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filter = request.getParameter("filter");
        String dis = "/ProductView.jsp";

        try {
            Collection<ProductBean> products;

            // Se il filtro è null o vuoto, carica tutti i prodotti
            if (filter == null || filter.isEmpty() || filter.equals("all")) {
                products = model.getAllProducts();
            } else {
                switch (filter) {
                    case "discounted":
                        products = model.GetDiscountedProductsList();
                        break;
                    case "up_to_50":
                        products = model.getProductsByPriceRange(0, 50);
                        break;
                    case "up_to_100":
                        products = model.getProductsByPriceRange(50, 100);
                        break;
                    case "over_100":
                        products = model.getProductsByPriceRange(100, Float.MAX_VALUE);
                        break;
                    default:
                        products = model.getAllProducts();
                        break;
                }
            }

            // Se products è null, inizializza come una lista vuota
            if (products == null) {
                products = new ArrayList<>();
            }

            request.setAttribute("products", products);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(dis);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
