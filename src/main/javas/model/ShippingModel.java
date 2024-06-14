package main.javas.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippingModel {
    private DataSource ds;

    public ShippingModel() {
        try {
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/storage");
        } catch (NamingException e) {
            throw new RuntimeException("Cannot get the data source", e);
        }
    }

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

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUser);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                shipping.setIdShipping(rs.getInt("idShipping"));
                shipping.setRecipientName(rs.getString("recipientName"));
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

    public void doDelete(int idUser) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM shipping WHERE idUser = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idUser);

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
}