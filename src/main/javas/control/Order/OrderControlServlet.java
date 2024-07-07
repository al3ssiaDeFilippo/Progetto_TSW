package main.javas.control.Order;

import main.javas.model.Order.OrderModel;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/OrderControlServlet")
public class OrderControlServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOrder = Integer.parseInt(request.getParameter("idOrder"));
        OrderModel orderModel = new OrderModel();

        try {
            orderModel.doDelete(idOrder);
            response.sendRedirect("OrderView.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}