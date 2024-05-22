package main.javas.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.javas.model.CartBean;
import main.javas.model.ProductBean;
import main.javas.model.ProductModelDS;
import main.javas.util.Carrello;
import main.javas.model.OrderModel;

import javax.servlet.annotation.WebServlet;

@WebServlet("/ServletCarrello")
public class ServletCarrello extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static OrderModel model = new OrderModel();
    static ProductModelDS prMod = new ProductModelDS();

    public ServletCarrello() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if (action != null) {
                if (action.equals("add")) {
                    int code = 0;
                    int quantity = 0;

                    String codeParameter = req.getParameter("code");
                    String quantityParameter = req.getParameter("quantity");

                    if (codeParameter != null && !codeParameter.isEmpty()) {
                        code = Integer.parseInt(codeParameter);
                    }

                    if (quantityParameter != null && !quantityParameter.isEmpty()) {
                        quantity = Integer.parseInt(quantityParameter);
                    }

                    ProductBean product = prMod.doRetrieveByKey(code);
                    if (product != null) {
                        Carrello cart = (Carrello) req.getSession().getAttribute("cart");
                        if (cart == null) {
                            cart = new Carrello();
                        }
                        CartBean cartItem = new CartBean();
                        cartItem.setCode(product.getCode());
                        cartItem.setPrice(product.getPrice());
                        cartItem.setQuantity(quantity);
                        cart.aggiungi(cartItem);
                        model.doSave(product, quantity);
                        req.getSession().setAttribute("cart", cart);

                        req.setAttribute("product", product);
                        String dis = req.getContextPath() + "/ProductView.jsp";
                        resp.sendRedirect(dis);
                    } else {
                        System.out.println("Prodotto non esistente");
                    }

                } else if (action.equals("delete")) {
                    int code = 0;
                    String codeParameter = req.getParameter("code");
                    if (codeParameter != null && !codeParameter.isEmpty()) {
                        code = Integer.parseInt(codeParameter);
                    }
                    Carrello cart = (Carrello) req.getSession().getAttribute("cart");
                    if (cart != null) {
                        ProductBean product = prMod.doRetrieveByKey(code);
                        CartBean cartItem = new CartBean();
                        cartItem.setCode(product.getCode());
                        model.doDelete(product);
                        cart.rimuovi(cartItem);
                        req.getSession().setAttribute("cart", cart);
                    }
                    List<CartBean> updatedProducts = cart.getProdotti();
                    req.setAttribute("arrayArticoli", updatedProducts);
                    req.getRequestDispatcher("/carrello.jsp").forward(req, resp);
                }
            }
        } catch (NumberFormatException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
