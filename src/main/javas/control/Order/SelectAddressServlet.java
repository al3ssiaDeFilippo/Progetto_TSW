package main.javas.control.Order;

import main.javas.bean.ShippingBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.CreditCardModel;
import main.javas.model.Order.ShippingModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class SelectAddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ShippingModel shippingModel;

    public void init() throws ServletException {
        super.init();
        shippingModel = new ShippingModel();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        String selectedAddressId = request.getParameter("selectedAddress");

        if (selectedAddressId == null) {
            throw new ServletException("Missing selectedAddress parameter");
        }

        try {
            int idAddress = Integer.parseInt(selectedAddressId);
            ShippingBean selectedAddress = shippingModel.doRetrieveByKey(user.getIdUser());
            if (selectedAddress == null) {
                throw new ServletException("Invalid address ID");
            }

            session.setAttribute("selectedAddress", selectedAddress);
            response.sendRedirect("CheckoutCard.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
