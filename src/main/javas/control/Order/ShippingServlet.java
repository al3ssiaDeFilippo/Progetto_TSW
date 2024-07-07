package main.javas.control.Order;

import main.javas.bean.ShippingBean;
import main.javas.model.Order.ShippingModel;
import main.javas.bean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

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
            throw new ServletException("Missing action parameter");
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
            case "retrieve":
                retrieveAddresses(request, response, user);
                break;
            case "select":
                selectAddress(request, response, user);
                break;
            default:
                throw new ServletException("Invalid action");
        }
    }

    private void addAddress(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {

        // Retrieve the form data
        HttpSession session = request.getSession();
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
            if (saveAddress) {
                // Insert the data into the database
                shippingModel.doSave(shipping);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // Set the "shippingAddress" attribute in the session
        session.setAttribute("selectedAddress", shipping);

        // Retrieve the nextPage parameter
        String nextPage = request.getParameter("nextPage");

        if (nextPage == null || nextPage.isEmpty()) {
            nextPage = "ProductView.jsp"; // Pagina predefinita se nextPage non è impostata
        }
        System.out.println("Redirecting to " + nextPage);
        response.sendRedirect(response.encodeRedirectURL(nextPage));
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
            response.sendRedirect("IndirizziUtente.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void deleteAddress(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        // Retrieve the form data
        String idAddressString = request.getParameter("idAddress");
        int idAddress;
        if (idAddressString != null && !idAddressString.isEmpty()) {
            idAddress = Integer.parseInt(idAddressString);
        } else {
            throw new ServletException("Invalid address ID");
        }

        // Use the user's ID from the session instead of getting it from the form
        int idUser = user.getIdUser();

        try {
            // Delete the data in the database
            shippingModel.doDelete(idAddress, idUser);
            response.sendRedirect("IndirizziUtente.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void retrieveAddresses(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        int idUser = user.getIdUser();
        try {
            Collection<ShippingBean> addresses = shippingModel.doRetrieveAll(idUser);
            HttpSession session = request.getSession();
            session.setAttribute("addresses", addresses);
            response.sendRedirect("IndirizziUtente.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void selectAddress(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        String selectedAddressId = request.getParameter("selectedAddress");

        if (selectedAddressId == null) {
            throw new ServletException("Missing selectedAddress parameter");
        }

        try {
            int idAddress = Integer.parseInt(selectedAddressId);
            System.out.println("Selected address ID: " + idAddress);
            ShippingBean selectedAddress = shippingModel.doRetrieveByKey(user.getIdUser());
            if (selectedAddress == null) {
                throw new ServletException("Invalid address ID");
            }

            HttpSession session = request.getSession();
            session.setAttribute("selectedAddress", selectedAddress);
            response.sendRedirect("CheckoutCard.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
