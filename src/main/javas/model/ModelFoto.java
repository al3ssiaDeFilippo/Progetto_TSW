package main.javas.model;

import main.javas.DriverManagerConnectionPool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;


public class ModelFoto {
    public static byte[] getImageByNumeroSerie(int code ) {
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

    public void insertImage(int code, InputStream imageStream) {
        try (Connection con = DriverManagerConnectionPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE product SET photo = ? WHERE code = ?");
            ps.setBlob(1, imageStream);
            ps.setInt(2, code);
            int result = ps.executeUpdate();
            if (result == 0) {
                System.out.println("Image not inserted");
            } else {
                System.out.println("Image inserted successfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


