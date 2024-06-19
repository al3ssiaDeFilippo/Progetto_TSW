package main.javas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderModel {

    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/storage");
        } catch (NamingException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public synchronized void doSave(OrderBean order) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO orders (idUser, idShipping, idCreditCard, idCart, orderDate, totalPrice) VALUES (?,?,?,?,?,?)";

/*
        ShippingBean shipping = new ShippingBean();
        ShippingModel shippingM = new ShippingModel();
        shipping = shippingM.doRetrieveByKey(user.idUser);

        CreditCardBean creditCardBean = new CreditCardBean();
        CreditCardModel creditCardModel = new CreditCardModel();
        creditCardBean = creditCardModel.doRetrieveByKey(creditCardBean.idCard);
*/

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setInt(1,order.getIdUser());
            preparedStatement.setInt(2,order.getIdShipping());
            preparedStatement.setString(3,order.getIdCreditCard());
            preparedStatement.setInt(4,order.getIdCart());
            preparedStatement.setDate(5,order.getOrderDate());
            preparedStatement.setFloat(6,order.getTotalPrice());

            preparedStatement.executeQuery();
        } finally {
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (con != null){
                con.close();
            }
        }
    }

    public synchronized OrderBean doRetrieveByUser(int idUser) throws SQLException{
        Connection con = null;
        PreparedStatement preparedStatement = null;
        OrderBean orderBean = new OrderBean();

        String selectSQL = "SELECT * FROM orders WHERE idUser = ?";
        try{
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1,idUser);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                orderBean.setIdOrder(rs.getInt("idOrder"));
                orderBean.setIdUser(rs.getInt("idUser"));
                orderBean.setIdShipping(rs.getInt("idShipping"));
                orderBean.setIdCreditCard(rs.getString("idCreditCard"));
                orderBean.setIdCart(rs.getInt("idCart"));
                orderBean.setOrderDate(rs.getDate("orderDate"));
                orderBean.setTotalPrice(rs.getFloat("totalPrice"));
            }
        }finally {
            if(con != null){
                con.close();
            }
            if(preparedStatement != null){
                preparedStatement.close();
            }
        }
        return orderBean;
    }
}
