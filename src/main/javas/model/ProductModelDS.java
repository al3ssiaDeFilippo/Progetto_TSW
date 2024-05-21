package main.javas.model;

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

public class ProductModelDS implements ProductModel {

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

    private static final String TABLE_NAME = "product";

    @Override
    public synchronized void doSave(ProductBean product) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + ProductModelDS.TABLE_NAME
                + " (PRODUCTNAME, DETAILS, QUANTITY, CATEGORY, PRICE, IVA, DISCOUNT, FRAME, FRAMECOLOR, SIZE, PHOTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getDetails());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getCategory());
            preparedStatement.setFloat(5, product.getPrice());
            preparedStatement.setInt(6, product.getIva());
            preparedStatement.setInt(7, product.getDiscount());
            preparedStatement.setString(8, product.getFrame());
            preparedStatement.setString(9, product.getFrameColor());
            preparedStatement.setString(10, product.getSize());
            preparedStatement.setBlob(11, product.getPhoto());

            preparedStatement.executeUpdate();

            connection.commit();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
    }

    @Override
    public synchronized ProductBean doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ProductBean bean = new ProductBean();

        String selectSQL = "SELECT * FROM " + ProductModelDS.TABLE_NAME + " WHERE CODE = ?";

        System.out.println("Mi trovo nel metodo doSave. Questa è la query: " + selectSQL + ". Questo è il codice: " + code + ".\n");

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.setCode(rs.getInt("CODE"));
                bean.setProductName(rs.getString("PRODUCTNAME"));
                bean.setDetails(rs.getString("DETAILS"));
                bean.setQuantity(rs.getInt("QUANTITY"));
                bean.setCategory(rs.getString("CATEGORY"));
                bean.setPrice(rs.getFloat("PRICE"));
                bean.setIva(rs.getInt("IVA"));
                bean.setDiscount(rs.getInt("DISCOUNT"));
                bean.setFrame(rs.getString("FRAME"));
                bean.setFrameColor(rs.getString("FRAMECOLOR"));
                bean.setSize(rs.getString("SIZE"));
                bean.setPhoto(rs.getBlob("PHOTO"));
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
        return bean;
    }

    @Override
    public synchronized boolean doDelete(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + ProductModelDS.TABLE_NAME + " WHERE CODE = ?";

        System.out.println("Debug: sto eliminando il prodotto con codice " + code);

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);

            result = preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
        return (result != 0);
    }

    @Override
    public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> products = new LinkedList<ProductBean>();

        String selectSQL = "SELECT * FROM " + ProductModelDS.TABLE_NAME;
        System.out.println(selectSQL);

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductBean bean = new ProductBean();

                bean.setCode(rs.getInt("CODE"));
                bean.setProductName(rs.getString("PRODUCTNAME"));
                bean.setDetails(rs.getString("DETAILS"));
                bean.setQuantity(rs.getInt("QUANTITY"));
                bean.setCategory(rs.getString("CATEGORY"));
                bean.setPrice(rs.getFloat("PRICE"));
                bean.setIva(rs.getInt("IVA"));
                bean.setDiscount(rs.getInt("DISCOUNT"));
                bean.setFrame(rs.getString("FRAME"));
                bean.setFrameColor(rs.getString("FRAMECOLOR"));
                bean.setSize(rs.getString("SIZE"));
                bean.setPhoto(rs.getBlob("PHOTO"));
                products.add(bean);
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                if (connection != null)
                    connection.close();
            }
        }
        return products;
    }

}
