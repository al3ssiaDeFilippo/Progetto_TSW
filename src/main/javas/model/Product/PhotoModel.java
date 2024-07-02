package main.javas.model.Product;

import main.javas.DriverManagerConnectionPool;
import main.javas.bean.ProductBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
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
            System.out.println("File: PhotoModel, line 28 - Error: " + e.getMessage());
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

    public void doSavePhoto(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "INSERT INTO photo (photo, productCode, frame, frameColor) VALUES (?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setBlob(1, product.getPhoto());
            preparedStatement.setInt(2, product.getCode());
            preparedStatement.setString(3, product.getFrame());
            preparedStatement.setString(4, product.getFrameColor());

            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    public static byte[] getCustomPhotos(int code, String frame, String frameColor) {
        try(Connection con = DriverManagerConnectionPool.getConnection()) {
            System.out.println("getCustomPhotos started");
            PreparedStatement ps = con.prepareStatement("SELECT photo FROM photo WHERE productCode = ? AND frame = ? AND frameColor = ?");
            ps.setInt(1, code);
            ps.setString(2, frame);
            ps.setString(3, frameColor);

            ResultSet rs = ps.executeQuery();
            byte[] imageByte = null;
            if(rs.next()) {
                Blob imageBlob = rs.getBlob("photo");
                imageByte = imageBlob.getBytes(1, (int) imageBlob.length());

            }
            System.out.println("Retrieved image of size: " + (imageByte != null ? imageByte.length : 0));
            return imageByte;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomPhoto(ProductBean product, String frame, String frameColor, Blob photo) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DriverManagerConnectionPool.getConnection();
            con.setAutoCommit(false);
            String sql = "UPDATE photo SET photo = ? WHERE productCode = ? AND frame = ? AND frameColor = ?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBlob(1, photo);
            preparedStatement.setInt(2, product.getCode());
            preparedStatement.setString(3, frame);
            preparedStatement.setString(4, frameColor);

            System.out.println("Updating photo for product with ID: " + product.getCode());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("No product found with ID: " + product.getCode());
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

    public static boolean isValidImageFile(String filePath) {
        List<String> validExtensions = Arrays.asList(".jpg", ".jpeg"); // aggiungi altre estensioni valide se necessario
        String fileExtension = filePath.substring(filePath.lastIndexOf('.')).toLowerCase();
        return validExtensions.contains(fileExtension);
    }

    public static byte[] getImageByNumeroSerie(int code) {
        try(Connection con = DriverManagerConnectionPool.getConnection()) {
            System.out.println("getImageByNumeroSerie avviato");
            PreparedStatement ps = con.prepareStatement("SELECT photo FROM product WHERE code = ?");
            ps.setInt(1, code);
            ResultSet rs = ps.executeQuery();
            byte[] imageByte = null;
            if(rs.next()) {
                Blob imageBlob = rs.getBlob("photo");
                imageByte = imageBlob.getBytes(1, (int) imageBlob.length());
            }
            System.out.println("Retrieved image of size: " + (imageByte != null ? imageByte.length : 0));
            return imageByte;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertImage(int code, String imagePath) {
        try (Connection con = DriverManagerConnectionPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE product SET photo = ? WHERE code = ?");

            // Convert image path to InputStream
            InputStream imageStream = new FileInputStream(imagePath);

            ps.setBlob(1, imageStream);
            ps.setInt(2, code);
            int result = ps.executeUpdate();
            if (result == 0) {
                System.out.println("Image not updated");
            } else {
                System.out.println("Image updated successfully");
            }
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProductAndSaveImages(int productCode, String directoryPath) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String insertPhotoSQL = "INSERT INTO photo (photo, productCode, frame, frameColor) VALUES (?, ?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            con.setAutoCommit(false);

            ProductModelDS PM = new ProductModelDS();
            ProductBean product = PM.doRetrieveByKey(productCode);

            // Ottieni le immagini dalla directory e inseriscile nel database
            List<String> images = getImagesFromDirectory(directoryPath, product.getProductName());

            preparedStatement = con.prepareStatement(insertPhotoSQL);

            for (String imagePath : images) {

                String result = extractDetailFromProductName(imagePath);

                //divide la stringa sugli spazi
                String[] parti = result.split(" ");

                String frame = "";
                String frameColor = "";

                //controlla se l'array risultante ha esattamente due elementi
                if(parti.length == 2) {
                    frame = parti[0];
                    frameColor = parti[1];
                } else {
                    System.out.println("La stringa non contiene esattamente uno spazio");
                }

                InputStream imageStream = new FileInputStream(imagePath);

                preparedStatement.setBlob(1, imageStream);
                preparedStatement.setInt(2, productCode);
                preparedStatement.setString(3, frame);
                preparedStatement.setString(4, frameColor);
                preparedStatement.executeUpdate();
            }

            System.out.println("riga 218 in PhotoModel");

            con.commit();
        } catch (SQLException | FileNotFoundException e) {
            if (con != null) {
                con.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    // Restituisce tutte le immagini di un certo Prodotto (in base al nome del prodotto)
    private List<String> getImagesFromDirectory(String directoryPath, String productName) {
        List<String> images = new ArrayList<>();
        File directory = new File(directoryPath);

        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().startsWith(productName) && isValidImageFile(file.getName())) {
                images.add(file.getAbsolutePath());
            }
        }

        return images;
    }

    private String extractDetailFromProductName(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();

        if (fileName.contains("pvc")) {
            if (fileName.contains("black")) {
                return "pvc black";
            } else if (fileName.contains("white")) {
                return "pvc white";
            } else if (fileName.contains("brown")) {
                return "pvc brown";
            }
        } else if (fileName.contains("wood")) {
            if (fileName.contains("black")) {
                return "wood black";
            } else if (fileName.contains("white")) {
                return "wood white";
            } else if (fileName.contains("brown")) {
                return "wood brown";
            }
        }

        return null; // Restituisci null se nessuna corrispondenza viene trovata
    }
}