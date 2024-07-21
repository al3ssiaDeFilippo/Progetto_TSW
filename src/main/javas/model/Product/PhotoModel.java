package main.javas.model.Product;

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
            throw new RuntimeException("Error in database connection");
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
            throw new RuntimeException(e);
        }
        finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch(SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if(connection != null) {
                    connection.close();
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
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT photo FROM photo WHERE productCode = ? AND frame = ? AND frameColor = ?";

        byte[] imageByte = null;

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);
            preparedStatement.setString(2, frame);
            preparedStatement.setString(3, frameColor);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                Blob imageBlob = rs.getBlob("photo");
                imageByte = imageBlob.getBytes(1, (int) imageBlob.length());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch(SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if(con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return imageByte;
    }

    public static boolean isValidImageFile(String filePath) {
        List<String> validExtensions = Arrays.asList(".jpg", ".jpeg"); // aggiungi altre estensioni valide se necessario
        String fileExtension = filePath.substring(filePath.lastIndexOf('.')).toLowerCase();
        return validExtensions.contains(fileExtension);
    }

    public static byte[] getImageByNumeroSerie(int code) {

        try(Connection con = ds.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT photo FROM product WHERE code = ?");
            ps.setInt(1, code);
            ResultSet rs = ps.executeQuery();
            byte[] imageByte = null;
            if(rs.next()) {
                Blob imageBlob = rs.getBlob("photo");
                imageByte = imageBlob.getBytes(1, (int) imageBlob.length());
            }
            return imageByte;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addProductAndSaveImages(int productCode, String directoryPath) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        String insertPhotoSQL = "INSERT INTO photo (photo, productCode, frame, frameColor) VALUES (?, ?, ?, ?)";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);

            ProductModelDS PM = new ProductModelDS();
            ProductBean product = PM.doRetrieveByKey(productCode);

            List<String> images = getImagesFromDirectory(directoryPath, product.getProductName());

            preparedStatement = con.prepareStatement(insertPhotoSQL);

            for (String imagePath : images) {
                String result = extractDetailFromProductName(imagePath);

                if (result == null) {
                    continue; // Salta questa immagine se non ha i dettagli necessari
                }

                String[] parti = result.split(" ");
                String frame = "";
                String frameColor = "";

                if(parti.length == 2) {
                    frame = parti[0];
                    frameColor = parti[1];
                } else {
                    continue; // Salta questa immagine se il formato dei dettagli non è corretto
                }

                try (InputStream imageStream = new FileInputStream(imagePath)) {
                    preparedStatement.setBlob(1, imageStream);
                    preparedStatement.setInt(2, productCode);
                    preparedStatement.setString(3, frame);
                    preparedStatement.setString(4, frameColor);
                    preparedStatement.executeUpdate();
                }
            }

            con.commit();
        } catch (SQLException | IOException e) {
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

    private List<String> getImagesFromDirectory(String directoryPath, String productName) {
        List<String> images = new ArrayList<>();
        File directory = new File(directoryPath);

        File[] files = directory.listFiles();
        if (files == null) {
            return images;
        }

        for (File file : files) {
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