package main.javas.control.User;

import main.javas.bean.OrderBean;
import main.javas.model.Order.OrderModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

public class UserDateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        Collection<OrderBean> orders = null;
        String errorMessage = null;

        // Verifica che le date non siano null e non siano vuote
        if (startDateStr != null && !startDateStr.isEmpty() && endDateStr != null && !endDateStr.isEmpty()) {
            try {
                // Converti le date in java.sql.Date
                Date startDate = Date.valueOf(startDateStr);
                Date endDate = Date.valueOf(endDateStr);

                OrderModel orderModel = new OrderModel();
                orders = orderModel.doRetrieveByDateRange(startDate, endDate);
            } catch (SQLException e) {
                errorMessage = "Errore nel recupero degli ordini: " + e.getMessage();
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                errorMessage = "Formato data non valido. Assicurati che le date siano nel formato yyyy-mm-dd.";
                e.printStackTrace();
            }
        } else {
            errorMessage = "Inserisci entrambe le date.";
        }

        request.setAttribute("orders", orders);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/OrderView.jsp").forward(request, response);
    }
}
