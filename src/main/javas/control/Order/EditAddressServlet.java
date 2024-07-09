package main.javas.control.Order;

import main.javas.bean.ShippingBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.ShippingModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class EditAddressServlet extends HttpServlet {
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

        int idUser = user.getIdUser();

        ShippingBean shipping = new ShippingBean();
        shipping.setRecipientName(recipientName);
        shipping.setAddress(address);
        shipping.setCity(city);
        shipping.setCap(cap);
        shipping.setIdUser(idUser);

        try {
            shippingModel.doUpdate(user);
            response.sendRedirect("IndirizziUtente.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
