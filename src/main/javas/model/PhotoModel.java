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
import java.util.Arrays;
import java.util.List;

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
            String sql = "SELECT PHOTO FROM PRODUCT WHERE code = ?";
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

    public synchronized static void updatePhoto(ProductBean product, String photoPath) throws SQLException, IOException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        FileInputStream fis = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            con.setAutoCommit(false);
            String sql = "UPDATE PRODUCT SET PHOTO = ? WHERE code = ?";

            File file = new File(photoPath);
            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + photoPath);
            }

            // Aggiungi il controllo sul tipo di immagine qui
            if (!isValidImageFile(photoPath)) {
                throw new IllegalArgumentException("Invalid image type: " + photoPath);
            }

            fis = new FileInputStream(file);

            // Converti il file in un array di byte
            byte[] photoBytes = new byte[(int) file.length()];
            fis.read(photoBytes);

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBytes(1, photoBytes);
            preparedStatement.setInt(2, product.getCode());

            System.out.println("Updating photo for product with ID: " + product.getCode());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("No product found with ID: " + product.getCode());
            }

            con.commit();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.out.println("Failed to close FileInputStream: " + e.getMessage());
                }
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public static boolean isValidImageFile(String filePath) {
        List<String> validExtensions = Arrays.asList(".jpg", ".jpeg"); // aggiungi altre estensioni valide se necessario
        String fileExtension = filePath.substring(filePath.lastIndexOf('.')).toLowerCase();
        return validExtensions.contains(fileExtension);
    }
}