package main.javas.model.Order;

import main.javas.bean.ShippingBean;
import main.javas.bean.UserBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class ShippingModel {
    private static DataSource ds;

    public ShippingModel() {
        try {
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/storage");
        } catch (NamingException e) {
            throw new RuntimeException("Cannot get the data source", e);
        }
    }

    private static final String TABLE_NAME = "shipping";

    public void doSave(ShippingBean shipping) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO shipping (recipientName, address, city, cap, idUser) VALUES (?, ?, ?, ?, ?)";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setString(1, shipping.getRecipientName());
            preparedStatement.setString(2, shipping.getAddress());
            preparedStatement.setString(3, shipping.getCity());
            preparedStatement.setInt(4, shipping.getCap());
            preparedStatement.setInt(5, shipping.getIdUser());

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

    public void doUpdate(UserBean idUser) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ShippingBean shipping = new ShippingBean();
        ShippingModel shippingModel = new ShippingModel();
        shipping = shippingModel.doRetrieveByKey(idUser.getIdUser());

        String updateSQL = "UPDATE shipping SET recipientName = ?, address = ?, city = ?, cap = ? WHERE idUser = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(updateSQL);
            preparedStatement.setString(1, shipping.getRecipientName());
            preparedStatement.setString(2, shipping.getAddress());
            preparedStatement.setString(3, shipping.getCity());
            preparedStatement.setInt(4, shipping.getCap());
            preparedStatement.setInt(5, shipping.getIdUser());


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

    public ShippingBean doRetrieveByKey(int idUser) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ShippingBean shipping = new ShippingBean();

        String selectSQL = "SELECT * FROM shipping WHERE idUser = ?";
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD " + idUser);
        try {
            System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSS ");
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUser);

            ResultSet rs = preparedStatement.executeQuery();
            con.commit();

            if (rs.next()) {
                shipping.setIdShipping(rs.getInt("idShipping"));
                shipping.setRecipientName(rs.getString("recipientName"));
                System.out.println("Recipient name: " + rs.getString("recipientName"));
                shipping.setAddress(rs.getString("address"));
                shipping.setCity(rs.getString("city"));
                shipping.setCap(rs.getInt("cap"));
                shipping.setIdUser(rs.getInt("idUser"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return shipping;
    }

    public static void doDelete(int idAddress, int idUser) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM shipping WHERE idShipping = ? AND idUser = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idAddress);
            preparedStatement.setInt(2, idUser);

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
    public synchronized Collection<ShippingBean> doRetrieveAll(int idUser) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ShippingBean> addresses = new LinkedList<ShippingBean>();

        String selectSQL = "SELECT * FROM " + ShippingModel.TABLE_NAME + " WHERE idUser = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUser);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ShippingBean shipping = new ShippingBean();
                shipping.setIdShipping(rs.getInt("idShipping"));
                shipping.setRecipientName(rs.getString("recipientName"));
                shipping.setAddress(rs.getString("address"));
                shipping.setCity(rs.getString("city"));
                shipping.setCap(rs.getInt("cap"));
                shipping.setIdUser(rs.getInt("idUser"));
                addresses.add(shipping);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return addresses;
    }

//    public ShippingBean doRetrieveByUser(int idUser) throws SQLException {
//        Connection con = null;
//        PreparedStatement preparedStatement = null;
//        ShippingBean shipping = new ShippingBean();
//
//        String selectSQL = "SELECT * FROM shipping WHERE idUser = ?";
//
//        try {
//            con = ds.getConnection();
//            preparedStatement = con.prepareStatement(selectSQL);
//            preparedStatement.setInt(1, idUser);
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            if (rs.next()) {
//                shipping.setIdShipping(rs.getInt("idShipping"));
//                shipping.setRecipientName(rs.getString("recipientName"));
//                shipping.setAddress(rs.getString("address"));
//                shipping.setCity(rs.getString("city"));
//                shipping.setCap(rs.getInt("cap"));
//                shipping.setIdUser(rs.getInt("idUser"));
//            }
//        } finally {
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }
//
//        return shipping;
//    }

}