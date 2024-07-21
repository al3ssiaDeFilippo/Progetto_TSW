package main.javas.control.Order;

import main.javas.bean.ShippingBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.ShippingModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class AddAddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ShippingModel shippingModel = new ShippingModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("LogIn.jsp");
            return;
        }

        String recipientName = request.getParameter("recipientName");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String capString = request.getParameter("cap");
        int cap;
        if (capString != null && !capString.isEmpty()) {
            cap = Integer.parseInt(capString);
        } else {
            throw new ServletException("Invalid CAP");
        }

        boolean saveAddress = request.getParameter("saveAddress") != null;
        int idUser = user.getIdUser();

        ShippingBean shipping = new ShippingBean();
        shipping.setRecipientName(recipientName);
        shipping.setAddress(address);
        shipping.setCity(city);
        shipping.setCap(cap);
        shipping.setIdUser(idUser);

        try {
            if (saveAddress) {
                shippingModel.doSave(shipping);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        session.setAttribute("selectedAddress", shipping);
        String nextPage = request.getParameter("nextPage");
        if (nextPage == null || nextPage.isEmpty()) {
            nextPage = request.getContextPath() + "HomePage.jsp";
        }
        response.sendRedirect(response.encodeRedirectURL(nextPage));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
