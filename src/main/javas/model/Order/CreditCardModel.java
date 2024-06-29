package main.javas.model.Order;

import main.javas.bean.CreditCardBean;
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

public class CreditCardModel {
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

    private static final String TABLE_NAME = "card";

    public synchronized void doSave(CreditCardBean creditCard) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + CreditCardModel.TABLE_NAME + " (idCard, ownerCard, expirationDate, cvv, idUser) VALUES (?, ?, ?, ?, ?)";
        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setString(1, creditCard.getIdCard());
            preparedStatement.setString(2, creditCard.getOwnerCard());
            preparedStatement.setDate(3, creditCard.getExpirationDate());
            preparedStatement.setInt(4, creditCard.getCvv());
            preparedStatement.setInt(5, creditCard.getIdUser());

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

    public synchronized boolean doDelete(String idCard) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + CreditCardModel.TABLE_NAME + " WHERE idCard = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(deleteSQL);
            preparedStatement.setString(1, idCard);

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

    public synchronized CreditCardBean doRetrieveByKey(String idCard) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement= null;
        CreditCardBean creditCard = null;

        String selectSQL = "SELECT * FROM " + CreditCardModel.TABLE_NAME + " WHERE idCard = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, idCard);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                creditCard = new CreditCardBean();
                creditCard.setIdCard(rs.getString("idCard"));
                creditCard.setOwnerCard(rs.getString("ownerCard"));
                creditCard.setExpirationDate(rs.getDate("expirationDate"));
                creditCard.setCvv(rs.getInt("cvv"));
                creditCard.setIdUser(rs.getInt("idUser"));
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

    public synchronized boolean SavedCard(UserBean user) {
        Connection con = null;
        PreparedStatement preparedStatement= null;
        CreditCardBean creditCard = null;

        String selectSQL = "SELECT COUNT(*) FROM card WHERE idUser = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, user.getIdUser());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public synchronized Collection<CreditCardBean> doRetrieveAll(int idUser) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<CreditCardBean> cards = new LinkedList<CreditCardBean>();

        String selectSQL = "SELECT * FROM " + CreditCardModel.TABLE_NAME + " WHERE idUser = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUser);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                CreditCardBean card = new CreditCardBean();
                card.setIdCard(rs.getString("idCard"));
                card.setOwnerCard(rs.getString("ownerCard"));
                card.setExpirationDate(rs.getDate("expirationDate"));
                card.setCvv(rs.getInt("cvv"));
                card.setIdUser(rs.getInt("idUser"));
                cards.add(card);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return cards;
    }

    public synchronized CreditCardBean doRetrieveByUser(int idUser) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CreditCardBean card = null;

        String selectSQL = "SELECT * FROM " + CreditCardModel.TABLE_NAME + " WHERE idUser = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUser);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                card = new CreditCardBean();
                card.setIdCard(rs.getString("idCard"));
                card.setOwnerCard(rs.getString("ownerCard"));
                card.setExpirationDate(rs.getDate("expirationDate"));
                card.setCvv(rs.getInt("cvv"));
                card.setIdUser(rs.getInt("idUser"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return card;
    }
}