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
import java.util.Collection;

public class ReadCardServlet extends HttpServlet {
    private CreditCardModel creditCardModel;

    public void init() throws ServletException {
        super.init();
        creditCardModel = new CreditCardModel();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        int idUser = user.getIdUser();
        try {
            Collection<CreditCardBean> cards = creditCardModel.doRetrieveAll(idUser);
            session.setAttribute("cards", cards);
            response.sendRedirect("CarteUtente.jsp");
        } catch (SQLException e) {
            throw new ServletException("Error reading the credit cards");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}