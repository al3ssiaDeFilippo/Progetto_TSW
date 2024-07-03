package main.javas.control.Order;

import main.javas.bean.CreditCardBean;
import main.javas.model.Order.CreditCardModel;
import main.javas.bean.UserBean;

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

        System.out.println("Sono in CreditCardServlet");

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("../errorPages/error500.jsp");
        }

        switch (action) {
            case "add":
                System.out.println("add");
                addCard(request, response, user);
                break;
            case "delete":
                System.out.println("delete");
                deleteCard(request, response, user);
                break;
            case "read":
                System.out.println("read");
                readCard(request, response, user);
                break;
            case "select":
                System.out.println("select");
                selectCard(request, response, user);
                break;
            default:
                response.sendRedirect("../errorPages/error500.jsp");
                return;
        }
    }

    private void addCard(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        // The existing code for adding a card goes here
        // Retrieve the form data
        HttpSession session = request.getSession();
        String nextPage = (String) request.getParameter("nextPage");
        System.out.println("Next page: " + nextPage);
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
            response.sendRedirect("../errorPages/error500.jsp");
            return;
        }

        // Convert the cvv string to an integer
        int cvv = 0;
        try {
            cvv = Integer.parseInt(cvvString);
        } catch (NumberFormatException e) {
            response.sendRedirect("../errorPages/error500.jsp");
            return;
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
            response.sendRedirect("../errorPages/SQLException.jsp");
        }

        // Set the "selectedCard" attribute in the session
        session.setAttribute("selectedCard", creditCard);

        // Set the "cardInfo" attribute in the session
        session.setAttribute("cardInfo", creditCard);

        // Redirect to the next page
        if (nextPage == null || nextPage.isEmpty()) {
            nextPage = "ProductView.jsp"; // Default page if nextPage is not set
        }

        System.out.println("Redirecting to " + nextPage);
        response.sendRedirect(response.encodeRedirectURL(nextPage));
    }

    private void deleteCard(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        String cardId = request.getParameter("cardId");

        if (cardId == null) {
            response.sendRedirect("../errorPages/error500.jsp");
        }

        try {
            creditCardModel.doDelete(cardId);
            response.sendRedirect("CarteUtente.jsp");
        } catch (SQLException e) {
            response.sendRedirect("../errorPages/SQLException.jsp");
        }
    }

    //recupera le carte di credito dell'utente
    private void readCard(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        // Code for reading a card goes here
        int idUser = user.getIdUser();
        try {
            Collection<CreditCardBean> cards = creditCardModel.doRetrieveAll(idUser);
            HttpSession session = request.getSession();
            session.setAttribute("cards", cards);
            response.sendRedirect("CarteUtente.jsp");
        } catch (SQLException e) {
            response.sendRedirect("../errorPages/error500.jsp");
        }
    }

    //seleziona la carta di credito
    private void selectCard(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        String selectedCardId = request.getParameter("selectedCard");

        if (selectedCardId == null) {
            response.sendRedirect("../errorPages/error500.jsp");
        }

        try {
            CreditCardBean selectedCard = creditCardModel.doRetrieveByKey(selectedCardId);
            if (selectedCard == null) {
                response.sendRedirect("../errorPages/error404.jsp");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("selectedCard", selectedCard);
            response.sendRedirect("RiepilogoOrdine.jsp");
        } catch (SQLException e) {
            response.sendRedirect("../errorPages/SQLException.jsp");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}