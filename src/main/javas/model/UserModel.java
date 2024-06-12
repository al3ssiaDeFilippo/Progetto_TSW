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

public class UserModel {
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
    private static final String TABLE_NAME = "user";

    public synchronized void doSave(UserBean user) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + UserModel.TABLE_NAME + " (SURNAME, NAME, USERNAME, BIRTHDATE, ADDRESS, EMAIL, PASSWORD, TELNUMBER, ADMIN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(insertSQL);
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setDate(4, user.getBirthDate());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getTelNumber());
            preparedStatement.setBoolean(9, user.getAdmin());

            preparedStatement.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public synchronized void updateUser(UserBean user) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        String updateSQL = "UPDATE " + TABLE_NAME + " SET surname = ?, name = ?, username = ?, BirthDate = ?, address = ?, email = ?, TelNumber = ?, admin = ? WHERE idUser = ?";
        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(updateSQL);
            preparedStatement.setString(1, user.getSurname());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setDate(4, user.getBirthDate());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getTelNumber());
            preparedStatement.setBoolean(8, user.getAdmin());
            preparedStatement.setInt(9, user.getIdUser());
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

    public synchronized UserBean doRetreiveByKey(int idUser) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        UserBean user = null;

        String selectSQL = "SELECT * FROM " + UserModel.TABLE_NAME + " WHERE IDUSER = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUser);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new UserBean();
                user.setSurname(rs.getString("SURNAME"));
                user.setName(rs.getString("NAME"));
                user.setUsername(rs.getString("USERNAME"));
                user.setBirthDate(rs.getDate("BIRTHDATE"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setTelNumber(rs.getString("TELNUMBER"));
                user.setAdmin(rs.getBoolean("ADMIN"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return user;
    }


    public synchronized boolean doDelete(UserBean user) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        int result = 0;
        UserBean existingUser = doRetreiveByKey(user.getIdUser());
        if(existingUser == null) {
            System.out.println("Utente non esistente");
            return false;
        } else {
            String deleteSQL = "DELETE FROM " + UserModel.TABLE_NAME + "WHERE IDUSER = ?";
            try {
                con = ds.getConnection();
                con.setAutoCommit(false);
                preparedStatement = con.prepareStatement(deleteSQL);
                preparedStatement.setInt(1, user.getIdUser());
                result = preparedStatement.executeUpdate();
                con.commit();
                con.setAutoCommit(true);
            } finally {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(con != null) {
                    con.close();
                }
            }
        }
        return (result != 0);
    }

    public synchronized Collection<UserBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Collection<UserBean> users = new LinkedList<UserBean>();

        String selectSQL = "SELECT * FROM " + UserModel.TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                UserBean user = new UserBean();
                user.setIdUser(rs.getInt("IDUSER"));
                user.setSurname(rs.getString("SURNAME"));
                user.setName(rs.getString("NAME"));
                user.setUsername(rs.getString("USERNAME"));
                user.setBirthDate(rs.getDate("BIRTHDATE"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setTelNumber(rs.getString("TELNUMBER"));
                user.setAdmin(rs.getBoolean("ADMIN"));
                users.add(user);
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
        return users;
    }

    public synchronized UserBean doRetrieveByUsername(String username) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        UserBean user = null;

        String selectSQL = "SELECT * FROM " + UserModel.TABLE_NAME + " WHERE USERNAME = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new UserBean();
                user.setIdUser(rs.getInt("IDUSER"));
                user.setSurname(rs.getString("SURNAME"));
                user.setName(rs.getString("NAME"));
                user.setUsername(rs.getString("USERNAME"));
                user.setBirthDate(rs.getDate("BIRTHDATE"));
                user.setAddress(rs.getString("ADDRESS"));
                user.setEmail(rs.getString("EMAIL"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setTelNumber(rs.getString("TELNUMBER"));
                user.setAdmin(rs.getBoolean("ADMIN"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return user;
    }
}