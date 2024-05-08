package main.javas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class OrderModel implements OrderInterface{

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

    private static final String TABLE_NAME = "cart";

    @Override
    public synchronized void doSave(OrderBean order) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + OrderModel.TABLE_NAME
                + "(QUANTITY, PRICE, IDPRODUCT) +  VALUES (?, ?, ?)";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setInt(1, order.getQuantity());
            preparedStatement.setDouble(2, order.getPrice());
            preparedStatement.setInt(3, order.getIdProduct());
            preparedStatement.executeUpdate();
            con.commit();
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
               if (con != null) {
                   con.close();
               }
            }
        }
    }

    @Override
    public synchronized OrderBean doRetrieveByKey(int code) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        OrderBean order = new OrderBean();

        String selectSQL = "SELECT * FROM " + OrderModel.TABLE_NAME + " WHERE CODE = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                order.setQuantity(rs.getInt("QUANTITY"));
                order.setPrice(rs.getFloat("PRICE"));
                order.setIdProduct(rs.getInt("IDPRODUCT"));
            }
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return order;
    }

    @Override
    public synchronized boolean doDelete(int code) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + OrderModel.TABLE_NAME + " WHERE CODE = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);

            result = preparedStatement.executeUpdate();
            con.commit();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        }

        return (result != 0);
    }

    @Override
    public synchronized Collection<OrderBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Collection<OrderBean> orders = new LinkedList<OrderBean>();

        String selectSQL = "SELECT * FROM " + OrderModel.TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrderBean bean = new OrderBean();
                bean.setQuantity(rs.getInt("QUANTITY"));
                bean.setPrice(rs.getFloat("PRICE"));
                bean.setIdProduct(rs.getInt("IDPRODUCT"));
                orders.add(bean);
            }
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (con != null) {
                    con.close();
                }
            }
        }
        return orders;
    }
}
