package main.javas.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardModel {
    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) initCtx.lookup("jdbc/storage");
        } catch (NamingException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private static final String TABLE_NAME = "creditCard";

    public synchronized void doSave(CreditCardBean creditCard) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO" + CreditCardModel.TABLE_NAME + "(idCard, ownerCard, expitation , cvv) VALUES (?, ?, ?, ?)";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setInt(1, creditCard.getIdCard());
            preparedStatement.setString(2, creditCard.getOwnerCard());
            preparedStatement.setDate(3, creditCard.getExpirationDate());
            preparedStatement.setInt(4, creditCard.getCvv());

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

    public synchronized boolean doDelete(int idCard) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + CreditCardModel.TABLE_NAME + " WHERE idCard = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idCard);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public synchronized CreditCardBean doRetriveByKey(int idCard) throws SQLException, SQLException {
        Connection con = null;
        PreparedStatement preparedStatement= null;
        CreditCardBean creditCard = null;

        String selectSQL = "SELECT * FROM" + CreditCardModel.TABLE_NAME + " WHERE idCard = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idCard);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                creditCard = new CreditCardBean();
                creditCard.setIdCard(rs.getInt("idCard"));
                creditCard.setExpirationDate(rs.getDate("scadenza"));
                creditCard.setCvv(rs.getInt("cvv"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return creditCard;
    }
}
