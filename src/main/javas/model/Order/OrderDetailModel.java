package main.javas.model.Order;

import main.javas.bean.CartBean;
import main.javas.bean.OrderDetailBean;
import main.javas.bean.ProductBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailModel {
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

    static final String TABLE_NAME = "orderDetails";

    public synchronized void doSave(OrderDetailBean orderDetail) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (idUser, productCode, quantity, frame, frameColor, size, price, idOrder) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setInt(1, orderDetail.getIdUser());
            preparedStatement.setInt(2, orderDetail.getProductCode());
            preparedStatement.setInt(3, orderDetail.getQuantity());
            preparedStatement.setString(4, orderDetail.getFrame());
            preparedStatement.setString(5, orderDetail.getFrameColor());
            preparedStatement.setString(6, orderDetail.getSize());
            preparedStatement.setFloat(7, orderDetail.getPrice());
            preparedStatement.setInt(8, orderDetail.getIdOrder());

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

    public synchronized List<OrderDetailBean> doRetrieveAll() throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        List<OrderDetailBean> orderDetails = new ArrayList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrderDetailBean bean = new OrderDetailBean();

                bean.setIdUser(rs.getInt("idUser"));
                bean.setProductCode(rs.getInt("productCode"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setFrame(rs.getString("frame"));
                bean.setFrameColor(rs.getString("frameColor"));
                bean.setSize(rs.getString("size"));
                bean.setPrice(rs.getFloat("price"));
                bean.setIdOrder(rs.getInt("idOrder"));

                orderDetails.add(bean);
            }

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return orderDetails;
    }

    public synchronized void doDelete(int idOrder) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE idOrder = ?";

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

    /*INIZIO MODIFICHE*/

    public synchronized OrderDetailBean doRetrieveByKey(int idUser, int productCode) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        OrderDetailBean bean = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE idUser = ? AND productCode = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, productCode);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bean = new OrderDetailBean();

                bean.setIdUser(rs.getInt("idUser"));
                bean.setProductCode(rs.getInt("productCode"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setFrame(rs.getString("frame"));
                bean.setFrameColor(rs.getString("frameColor"));
                bean.setSize(rs.getString("size"));
                bean.setPrice(rs.getFloat("price"));
                bean.setIdOrder(rs.getInt("idOrder"));
            }

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return bean;
    }

    public synchronized void doUpdateQuantity(ProductBean productBean, CartBean cart) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE product SET quantity = quantity - ? WHERE code = ?";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false); // Start transaction

            preparedStatement = con.prepareStatement(updateSQL);
            preparedStatement.setInt(1, cart.getQuantity()); // Set the quantity to subtract
            preparedStatement.setInt(2, productBean.getCode()); // Set the product code

            int rowsUpdated = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsUpdated > 0) {
                System.out.println("Product quantity updated successfully.");
            } else {
                System.out.println("Product quantity update failed. Product might not exist.");
            }

            con.commit(); // Commit transaction
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction in case of error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    /*FINE MODIFICHE*/

}