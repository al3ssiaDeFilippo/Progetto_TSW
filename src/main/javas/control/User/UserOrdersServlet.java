package main.javas.control.User;

import main.javas.bean.OrderBean;
import main.javas.bean.UserBean;
import main.javas.model.Order.OrderModel;
import main.javas.model.User.UserModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserOrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserBean user = (UserBean) request.getSession().getAttribute("user");
        if (user == null || !user.getAdmin()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Solo gli admin possono accedere a questa risorsa.");
            return;
        }

        String userName = request.getParameter("userName");
        List<OrderBean> orders = new ArrayList<>();
        OrderModel orderModel = new OrderModel();

        try {
            // Se è fornito un username, filtra gli ordini
            if (userName != null && !userName.isEmpty()) {
                UserModel userModel = new UserModel();
                UserBean userBean = userModel.doRetrieveByUsername(userName);
                if (userBean != null) {
                    int userId = userBean.getIdUser();
                    orders.addAll(orderModel.doRetrieveByUser(userId));
                } else {
                    request.setAttribute("errorMessage", "Utente non trovato.");
                }
            } else {
                // Altrimenti, ottieni tutti gli ordini
                orders.addAll(orderModel.doRetrieveAll());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("orders", orders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/OrderView.jsp");
        dispatcher.forward(request, response);
    }
}
