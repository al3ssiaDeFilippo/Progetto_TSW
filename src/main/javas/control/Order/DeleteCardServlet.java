package main.javas.control.Order;

import main.javas.model.Order.CreditCardModel;
import main.javas.bean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteCardServlet extends HttpServlet {
    private CreditCardModel creditCardModel;

    public void init() throws ServletException {
        super.init();
        creditCardModel = new CreditCardModel();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        String cardId = request.getParameter("cardId");

        if (cardId == null) {
            throw new ServletException("Missing cardId parameter");
        }

        try {
            boolean res = creditCardModel.doDelete(cardId);
            response.sendRedirect("CarteUtente.jsp");
        } catch (SQLException e) {
            e.printStackTrace(); // This will print detailed information about the SQLException
            throw new ServletException("Error deleting the credit card");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}