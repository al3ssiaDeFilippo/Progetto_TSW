package main.javas.control.Order;

import main.javas.bean.ShippingBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.ShippingModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class RetrieveAddressesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ShippingModel shippingModel = new ShippingModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("LogIn.jsp");
            return;
        }

        int idUser = user.getIdUser();
        try {
            Collection<ShippingBean> addresses = shippingModel.doRetrieveAll(idUser);
            session.setAttribute("addresses", addresses);
            response.sendRedirect("IndirizziUtente.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
