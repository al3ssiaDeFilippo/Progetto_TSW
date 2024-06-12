package main.javas.control;

import main.javas.model.CreditCardBean;
import main.javas.model.CreditCardModel;
import main.javas.model.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/CreditCardServlet")
public class CreditCardServlet extends HttpServlet {
    private CreditCardModel creditCardModel;

    public void init() throws ServletException {
        super.init();
        // Initialize the credit card model and the database connection
        creditCardModel = new CreditCardModel();
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
        String cardNumber = request.getParameter("cardNumber");
        String cardHolder = request.getParameter("cardHolder");
        String expiryDateString = request.getParameter("expiryDate");
        String cvvString = request.getParameter("cvv");

        // Convert the expiry date string to a Date object
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date expiryDate;
        try {
            java.util.Date parsed = format.parse(expiryDateString);
            expiryDate = new Date(parsed.getTime());
        } catch (ParseException e) {
            throw new ServletException("Invalid date format. Please use yyyy-MM.", e);
        }

        // Convert the cvv string to an integer
        int cvv;
        try {
            cvv = Integer.parseInt(cvvString);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid CVV. Please enter a number.", e);
        }

        // Convert the card number string to an integer
        int cardNumberInt;
        try {
            cardNumberInt = Integer.parseInt(cardNumber);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid card number. Please enter a number.", e);
        }

        // Get the idUser from the user object in the session
        int idUser = user.getIdUser();

        // Create a new CreditCardBean with the form data
        CreditCardBean creditCard = new CreditCardBean();
        creditCard.setIdCard(cardNumberInt);
        creditCard.setOwnerCard(cardHolder);
        creditCard.setExpirationDate(expiryDate);
        creditCard.setCvv(cvv);
        creditCard.setIdUser(idUser); // Set the idUser

        try {
            // Insert the data into the database
            creditCardModel.doSave(creditCard);
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage());
        }

        // Imposta l'attributo "cardInfo" nella sessione
        session.setAttribute("cardInfo", creditCard);

        // Redirect to a confirmation page or back to the card management page
        response.sendRedirect("RiepilogoOrdine.jsp");
    }
}