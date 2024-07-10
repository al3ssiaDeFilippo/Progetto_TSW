package main.javas.control.Order;

import main.javas.bean.CreditCardBean;
import main.javas.model.Order.CreditCardModel;
import main.javas.bean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddCardServlet extends HttpServlet {
    private CreditCardModel creditCardModel;

    public void init() throws ServletException {
        super.init();
        creditCardModel = new CreditCardModel();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("LogIn.jsp");
            return;
        }

        String nextPage = request.getParameter("nextPage");
        String cardNumber = request.getParameter("cardNumber");
        String cardHolder = request.getParameter("cardHolder");
        String expiryDateString = request.getParameter("expiryDate");
        String cvvString = request.getParameter("cvv");

        boolean saveCard = request.getParameter("saveCard") != null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date expiryDate;
        try {
            java.util.Date parsed = format.parse(expiryDateString);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(parsed);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set to the last day of the month
            expiryDate = new Date(calendar.getTimeInMillis());
        } catch (ParseException e) {
            throw new ServletException("Invalid expiry date format");
        }

        int cvv = 0;
        try {
            cvv = Integer.parseInt(cvvString);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid CVV format");
        }

        int idUser = user.getIdUser();

        CreditCardBean creditCard = new CreditCardBean();
        creditCard.setIdCard(cardNumber);
        creditCard.setOwnerCard(cardHolder);
        creditCard.setExpirationDate(expiryDate);
        creditCard.setCvv(cvv);
        creditCard.setIdUser(idUser);

        try {
            // Insert the data into the database only if the saveCard checkbox is checked
            if (saveCard) {
                creditCardModel.doSave(creditCard);
            }
        } catch (SQLException e) {
            throw new ServletException("Error saving the credit card");
        }

        // Set the "selectedCard" attribute in the session
        session.setAttribute("selectedCard", creditCard);

        // Set the "cardInfo" attribute in the session
        session.setAttribute("cardInfo", creditCard);

        // Redirect to the next page
        if (nextPage == null || nextPage.isEmpty()) {
            nextPage = "ProductView.jsp"; // Default page if nextPage is not set
        }

        response.sendRedirect(response.encodeRedirectURL(nextPage));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}