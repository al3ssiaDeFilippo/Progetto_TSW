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
            response.sendRedirect("LogIn.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            throw new ServletException("Missing action parameter.");
        }

        switch (action) {
            case "add":
                addAddress(request, response, user);
                break;
            case "edit":
                editAddress(request, response, user);
                break;
            case "delete":
                deleteAddress(request, response, user);
                break;
            default:
                throw new ServletException("Invalid action parameter.");
        }
    }

    private void addAddress(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        // Retrieve the form data
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

        HttpSession session = request.getSession();
        // Set the "shippingAddress" attribute in the session
        session.setAttribute("shippingAddress", shipping);

        // Redirect to UserAddresses.jsp
        response.sendRedirect("UserAddresses.jsp");
    }

    private void editAddress(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        // Retrieve the form data
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
            // Update the data in the database
            shippingModel.doUpdate(user);
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage());
        }

        // Redirect to UserAddresses.jsp
        response.sendRedirect("UserAddresses.jsp");
    }

    private void deleteAddress(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        // Retrieve the form data
        String addressId = request.getParameter("addressId");

        if (addressId == null) {
            throw new ServletException("Missing addressId parameter.");
        }

        try {
            // Delete the address from the database
            shippingModel.doDelete(user.getIdUser());
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage());
        }

        // Redirect to UserAddresses.jsp
        response.sendRedirect("UserAddresses.jsp");
    }



    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    }*/
}