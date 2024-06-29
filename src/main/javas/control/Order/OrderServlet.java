package main.javas.control.Order;

import main.javas.bean.CartBean;
import main.javas.bean.OrderBean;
import main.javas.bean.OrderDetailBean;
import main.javas.model.Order.*;
import main.javas.bean.ProductBean;
import main.javas.model.Product.ProductModelDS;
import main.javas.bean.UserBean;
import main.javas.model.User.UserModel;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;

/*MODIFICHE*/
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        UserModel userModel = new UserModel();
        CartModel cartModel = new CartModel();
        ShippingModel shippingModel = new ShippingModel();
        CreditCardModel cardModel = new CreditCardModel();
        OrderModel orderModel = new OrderModel();
        OrderDetailModel orderDetailModel = new OrderDetailModel(); // Aggiunto

        try {
            // Get the cart items
            List<CartBean> cartItems = cartModel.doRetrieveAll(user.getIdUser());

            // Create an order
            OrderBean order = new OrderBean();
            order.setIdUser(user.getIdUser());
            order.setIdShipping(shippingModel.doRetrieveByKey(user.getIdUser()).getIdShipping());
            order.setIdCreditCard(cardModel.doRetrieveByUser(user.getIdUser()).getIdCard());
            order.setOrderDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            order.setTotalPrice(cartModel.getTotalPriceWithDiscount(cartItems));

            // Save the order
            int idOrder = orderModel.doSave(order);


            // Update product quantities and save order details
            for (CartBean cartItem : cartItems) {

                ProductBean product = new ProductBean();

                ProductModelDS PM = new ProductModelDS();
                product = PM.doRetrieveByKey(cartItem.getProductCode());

                // Update the product quantity in the database
                orderDetailModel.doUpdateQuantity(product,cartItem);

                // CreateOrderServlet an order detail for each cart item
                OrderDetailBean orderDetail = new OrderDetailBean();
                orderDetail.setIdUser(user.getIdUser());
                orderDetail.setProductCode(cartItem.getProductCode());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setFrame(cartItem.getFrame());
                orderDetail.setFrameColor(cartItem.getFrameColor());
                orderDetail.setSize(cartItem.getSize());
                orderDetail.setPrice(cartItem.getPrice() * cartItem.getQuantity());
                orderDetail.setIdOrder(idOrder);

                // Save order detail
                orderDetailModel.doSave(orderDetail);
            }

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
    /*MODIFICHE*/

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}