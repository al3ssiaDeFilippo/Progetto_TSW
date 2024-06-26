package main.javas.control.Order;

import main.javas.model.Order.*;
import main.javas.model.User.UserBean;
import main.javas.model.User.UserModel;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        UserModel userModel = new UserModel();
        CartModel cartModel = new CartModel();
        ShippingModel shippingModel = new ShippingModel();
        CreditCardModel cardModel = new CreditCardModel();
        OrderModel orderModel = new OrderModel();

        try {
            // Get the cart items
            List<CartBean> cartItems = cartModel.doRetrieveAll(user.getIdUser());
            for(CartBean cartItem : cartItems) {
                System.out.println("Cart Item: " + cartItem);
            }

            OrderBean order = new OrderBean();
            order.setIdUser(user.getIdUser());
            order.setIdShipping(shippingModel.doRetrieveByKey(user.getIdUser()).getIdShipping());
            order.setIdCreditCard(cardModel.doRetrieveByUser(user.getIdUser()).getIdCard());
            order.setOrderDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            order.setTotalPrice(cartModel.getTotalPriceWithDiscount(cartItems));

            orderModel.doSave(order);

            // Clear the cart
            for (CartBean cartItem : cartItems) {
                cartModel.doDelete(cartItem.getProductCode());
            }

            // Redirect the user to a success page
            response.sendRedirect("OrderConfirmation.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}