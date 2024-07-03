package main.javas.model.Order;

import main.javas.bean.OrderBean;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
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

    public synchronized int doSave(OrderBean order) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO orders (idUser, idShipping, idCreditCard, orderDate, totalPrice) VALUES (?,?, ?,?,?)";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,order.getIdUser());
            preparedStatement.setInt(2,order.getIdShipping());
            preparedStatement.setString(3,order.getIdCreditCard());
            preparedStatement.setDate(4,order.getOrderDate());
            preparedStatement.setFloat(5,order.getTotalPrice());

            // Use executeUpdate() instead of executeQuery()
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained");
            }

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

    public synchronized Collection<OrderBean> getOrdersByDate() throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Collection<OrderBean> orders = new LinkedList<>();

        String selectSQL = "SELECT * FROM orders ORDER BY orderDate DESC";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrderBean order = new OrderBean();
                order.setIdOrder(rs.getInt("idOrder"));
                order.setIdUser(rs.getInt("idUser"));
                order.setIdShipping(rs.getInt("idShipping"));
                order.setIdCreditCard(rs.getString("idCreditCard"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setTotalPrice(rs.getFloat("totalPrice"));

                orders.add(order);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return orders;
    }

    public synchronized void doDelete(int idOrder) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM orders WHERE idOrder = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idOrder);

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public synchronized Collection<OrderBean> doRetrieveByUserId(int userId) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Collection<OrderBean> orders = new LinkedList<>();

        String selectSQL = "SELECT * FROM orders WHERE idUser = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrderBean bean = new OrderBean();
                bean.setIdOrder(rs.getInt("idOrder"));
                bean.setIdUser(rs.getInt("idUser"));
                bean.setIdShipping(rs.getInt("idShipping"));
                bean.setIdCreditCard(rs.getString("idCreditCard"));
                bean.setOrderDate(rs.getDate("orderDate"));
                bean.setTotalPrice(rs.getFloat("totalPrice"));
                orders.add(bean);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return orders;
    }





}
