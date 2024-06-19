package main.javas.control;

import main.javas.model.OrderBean;
import main.javas.model.OrderModel;
import main.javas.model.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private OrderModel orderModel;

    public void init() throws ServletException {
        super.init();
        // Initialize the order model
        orderModel = new OrderModel();
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

        if (action.equals("add")) {
            addOrder(request, response, user);
        } else {
            throw new ServletException("Invalid action parameter.");
        }
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean userSession = (UserBean) session.getAttribute("user");

        // Retrieve the form data
        // Assuming that the form data includes idUser, idCreditCard, idShipping, idCart
        int idShipping = Integer.parseInt(request.getParameter("idShipping"));
        String idCreditCard = request.getParameter("idCreditCard");
        int idCart = Integer.parseInt(request.getParameter("idCart"));
        String orderDateString = request.getParameter("orderDate");

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date orderDate = null;
        try {
            // Parse the order date from the string
            orderDate = dateFormat.parse(orderDateString);
        } catch (ParseException e) {
            // Handle the exception if the date format is invalid
            System.err.println("Invalid date format: " + e.getMessage());
            // You might want to re-throw the exception or handle it in some way
        }

        float totalPrice = Float.parseFloat(request.getParameter("totalprice"));

        // Create a new OrderBean with the form data
        OrderBean order = new OrderBean();
        order.setIdUser(userSession.getIdUser());
        order.setIdShipping(idShipping);
        order.setIdCreditCard(idCreditCard);
        order.setIdCart(idCart);
        order.setOrderDate(orderDate);
        order.setTotalPrice(totalPrice);

        try {
            // Insert the data into the database
            orderModel.doSave(order);
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage());
        }

        // Redirect to a confirmation page
        response.sendRedirect("OrderConfirmation.jsp");
    }
}