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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Collection;

@WebServlet("/CreditCardServlet")
public class CreditCardServlet extends HttpServlet {
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

        String action = request.getParameter("action");

        if (action == null) {
            throw new ServletException("Missing action parameter.");
        }

        switch (action) {
            case "add":
                addCard(request, response, user);
                break;
            case "delete":
                deleteCard(request, response, user);
                break;
            case "read":
                readCard(request, response, user);
                break;
            default:
                throw new ServletException("Invalid action parameter.");
        }
    }

    private void addCard(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        // The existing code for adding a card goes here
        // Retrieve the form data
        HttpSession session = request.getSession();
        String nextPage = (String) session.getAttribute("nextPage");
        String cardNumber = request.getParameter("cardNumber");
        String cardHolder = request.getParameter("cardHolder");
        String expiryDateString = request.getParameter("expiryDate");
        String cvvString = request.getParameter("cvv");

        // Check if the checkbox is selected
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
            throw new ServletException("Invalid date format. Please use yyyy-MM.", e);
        }

        // Convert the cvv string to an integer
        int cvv = 0;
        try {
            cvv = Integer.parseInt(cvvString);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid CVV. Please enter a number.", e);
        }

        // Get the idUser from the user object in the session
        int idUser = user.getIdUser();

        // Create a new CreditCardBean with the form data
        CreditCardBean creditCard = new CreditCardBean();
        creditCard.setIdCard(cardNumber);
        creditCard.setOwnerCard(cardHolder);
        creditCard.setExpirationDate(expiryDate);
        creditCard.setCvv(cvv);
        creditCard.setIdUser(idUser); // Set the idUser

        try {
            // Insert the data into the database only if the saveCard checkbox is checked
            if (saveCard) {
                creditCardModel.doSave(creditCard);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage());
        }

        session = request.getSession();
        // Imposta l'attributo "cardInfo" nella sessione
        session.setAttribute("cardInfo", creditCard);

        // Reindirizzamento alla pagina successiva
        if (nextPage == null || nextPage.isEmpty()) {
            nextPage = "ProductView.jsp"; // Pagina predefinita se nextPage non è impostata
        }
        System.out.println("Redirecting to " + nextPage);
        response.sendRedirect(response.encodeRedirectURL(nextPage));
    }

    private void deleteCard(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        String cardId = request.getParameter("cardId");

        if (cardId == null) {
            throw new ServletException("Missing cardId parameter.");
        }

        try {
            creditCardModel.doDelete(Integer.parseInt(cardId));
            response.sendRedirect("CarteUtente.jsp");
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage());
        }
    }

    private void readCard(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        // Code for reading a card goes here
        int idUser = user.getIdUser();
        try {
            Collection<CreditCardBean> cards = creditCardModel.doRetrieveAll(idUser);
            request.setAttribute("cards", cards);
            request.getRequestDispatcher("CarteUtente.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage());
        }
    }
}