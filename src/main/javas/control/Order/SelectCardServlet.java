package main.javas.control.Order;

import main.javas.bean.CreditCardBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.CreditCardModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class SelectCardServlet extends HttpServlet {
    private CreditCardModel creditCardModel;

    public void init() throws ServletException {
        super.init();
        creditCardModel = new CreditCardModel();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        String selectedCardId = request.getParameter("cardId");

        if (selectedCardId == null) {
            throw new ServletException("Missing cardId parameter");
        }

        try {
            CreditCardBean selectedCard = creditCardModel.doRetrieveByKey(selectedCardId);
            if (selectedCard == null) {
                throw new ServletException("Card not found");
            }

            session.setAttribute("selectedCard", selectedCard);
            response.sendRedirect("RiepilogoOrdine.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error selecting the credit card", e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
