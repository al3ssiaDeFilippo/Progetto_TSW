package main.javas.model;

import main.javas.DriverManagerConnectionPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PhotoModel {

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

    public synchronized static byte[] load(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        byte[] bt = null;

        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            String sql = "SELECT PHOTO FROM PRODUCT WHERE ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, product.getCode());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                bt = resultSet.getBytes("photo");
            }

            connection.commit();

        } catch (SQLException e) {
            System.out.println(e);
        }
            finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch(SQLException e) {
                System.out.println(e);
            } finally {
                if(connection != null) {
                    DriverManagerConnectionPool.releaseConnection(connection);
                }
            }
        }
        return bt;
    }

    public synchronized static void updatePhoto(ProductBean product) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            con.setAutoCommit(false);
            String sql = "UPDATE PRODUCT SET PHOTO = ? WHERE ID = ?";

            File file = new File(String.valueOf(product.getPhoto()));
            try {

                FileInputStream fis = new FileInputStream(file);

                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setByte(1, product.getPhoto());
                preparedStatement.setInt(2, product.getCode());
                preparedStatement.executeUpdate();


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            con.commit();
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
