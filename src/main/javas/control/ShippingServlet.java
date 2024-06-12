package main.javas.control;

import main.javas.model.ShippingBean;
import main.javas.model.ShippingModel;
import main.javas.model.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ShippingServlet")
public class ShippingServlet extends HttpServlet {
    private ShippingModel shippingModel;

    public void init() throws ServletException {
        super.init();
        // Initialize the shipping model and the database connection
        shippingModel = new ShippingModel();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        if (user == null) {
            // The user is not logged in, redirect to the login page
            response.sendRedirect("LogIn.jsp");
            return;
        }

        // Retrieve the form data
        String recipientName = request.getParameter("recipientName");
        String address = request.getParameter("address");
        String city = request.getParameter("city");

        String capString = request.getParameter("cap");
        int cap;
        if (capString != null && !capString.isEmpty()) {
            cap = Integer.parseInt(capString);
        } else {
            // Handle the case where capString is null or empty
            throw new ServletException("Invalid CAP");
        }

        // Use the user's ID from the session instead of getting it from the form
        int idUser = user.getIdUser();

        // Create a new ShippingBean with the form data
        ShippingBean shipping = new ShippingBean();
        shipping.setRecipientName(recipientName);
        shipping.setAddress(address);
        shipping.setCity(city);
        shipping.setCap(cap);
        shipping.setIdUser(idUser);

        try {
            // Insert the data into the database
            shippingModel.doSave(shipping);
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage());
        }

        // Set the "shippingAddress" attribute in the session
        session.setAttribute("shippingAddress", shipping);

        // Redirect to CheckoutCard.jsp
        response.sendRedirect("CheckoutCard.jsp");
    }
}