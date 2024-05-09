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

//La classe OrderModel implementa l'interfaccia OrderInterface
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

//Il metodo doSave salva un oggetto CartBean nel database.
    @Override
    public synchronized void doSave(CartBean order) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + OrderModel.TABLE_NAME
                + "(QUANTITY, PRICE, IDPRODUCT) +  VALUES (?, ?, ?)";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setInt(1, order.getQuantity());
            preparedStatement.setFloat(2, order.getPrice());
            preparedStatement.setInt(3, order.getIdProduct());
            preparedStatement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
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

//il metodo doDelete elimina un oggetto CartBean dal database.l
    @Override
    public synchronized boolean doDelete(int code) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + OrderModel.TABLE_NAME + " WHERE CODE = ?";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);

            result = preparedStatement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
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

    //Il metodo doRetrieveByKey recupera un oggetto CartBean dal database.
    @Override
    public synchronized CartBean doRetrieveByKey(int code) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        CartBean order = new CartBean();

        System.out.println("CODE preso in input da doRetrieveByKey --> " + code);

        String selectSQL = "SELECT * FROM " + OrderModel.TABLE_NAME + " WHERE CODE = ?";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                order.setQuantity(rs.getInt("QUANTITY"));
                order.setPrice(rs.getFloat("PRICE"));
                order.setIdProduct(rs.getInt("IDPRODUCT"));
            }
            System.out.print("Riga 122, file OrderModel.java, stampa dell'order --> code --> " + order.getCode());

            con.commit();
            con.setAutoCommit(true);
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

    //Il metodo doRetrieveAll recupera tutti gli oggetti CartBean dal database.
    @Override
    public synchronized Collection<CartBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Collection<CartBean> orders = new LinkedList<CartBean>();

        String selectSQL = "SELECT * FROM " + OrderModel.TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                CartBean bean = new CartBean();
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