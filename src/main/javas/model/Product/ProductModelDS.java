package main.javas.model.Product;

import main.javas.bean.ProductBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductModelDS implements ProductModel {

    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/storage");

        } catch (NamingException e) {
            System.out.println("File ProductModelDS, line 26 - Error:" + e.getMessage());
        }
    }

    private static final String TABLE_NAME = "product";

    @Override
    public synchronized int doSave(ProductBean product) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + ProductModelDS.TABLE_NAME
                + " (PRODUCTNAME, DETAILS, QUANTITY, CATEGORY, PRICE, IVA, DISCOUNT, FRAME, FRAMECOLOR, SIZE, PHOTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getProductName());
            System.out.println("Debug: " + product.getProductName());
            preparedStatement.setString(2, product.getDetails());
            System.out.println("Debug: " + product.getDetails());
            preparedStatement.setInt(3, product.getQuantity());
            System.out.println("Debug: " + product.getQuantity());
            preparedStatement.setString(4, product.getCategory());
            System.out.println("Debug: " + product.getCategory());
            preparedStatement.setFloat(5, product.getPrice());
            System.out.println("Debug: " + product.getPrice());
            preparedStatement.setInt(6, product.getIva());
            System.out.println("Debug: " + product.getIva());
            preparedStatement.setInt(7, product.getDiscount());
            System.out.println("Debug: " + product.getDiscount());
            preparedStatement.setString(8, product.getFrame());
            System.out.println("Debug: " + product.getFrame());
            preparedStatement.setString(9, product.getFrameColor());
            System.out.println("Debug: " + product.getFrameColor());
            preparedStatement.setString(10, product.getSize());
            System.out.println("Debug: " + product.getSize());
            preparedStatement.setBlob(11, product.getPhoto());
            System.out.println("Debug: " + product.getPhoto());
            preparedStatement.executeUpdate();
            connection.commit();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained");
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

        String deleteProductSQL = "DELETE FROM " + ProductModelDS.TABLE_NAME + " WHERE CODE = ?";
        String deleteFromCartSQL = "DELETE FROM cart WHERE productCode = ?";

        System.out.println("Debug: sto eliminando il prodotto con codice " + code);

        try {
            connection = ds.getConnection();

            // Elimina il prodotto dalla tabella cart
            preparedStatement = connection.prepareStatement(deleteFromCartSQL);
            preparedStatement.setInt(1, code);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            // Elimina il prodotto dalla tabella product
            preparedStatement = connection.prepareStatement(deleteProductSQL);
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

    public void doUpdate(ProductBean product) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE product SET productName = ?, details = ?, quantity = ?, category = ?, price = ?, iva = ?, discount = ?, photo = ? WHERE code = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, product.getProductName());
            System.out.println("Debug: " + product.getProductName());
            preparedStatement.setString(2, product.getDetails());
            System.out.println("Debug: " + product.getDetails());
            preparedStatement.setInt(3, product.getQuantity());
            System.out.println("Debug: " + product.getQuantity());
            preparedStatement.setString(4, product.getCategory());
            System.out.println("Debug: " + product.getCategory());
            preparedStatement.setFloat(5, product.getPrice());
            System.out.println("Debug: " + product.getPrice());
            preparedStatement.setInt(6, product.getIva());
            System.out.println("Debug: " + product.getIva());
            preparedStatement.setInt(7, product.getDiscount());
            System.out.println("Debug: " + product.getDiscount());
            preparedStatement.setBlob(8, product.getPhoto());
            System.out.println("Debug: " + product.getPhoto());
            preparedStatement.setInt(9, product.getCode());
            System.out.println("Debug: " + product.getCode());


            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    /*Modifiche iniziano qui*/
    public void disableForeignKeyCheck() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ds.getConnection();
            statement = connection.createStatement();
            statement.execute("SET FOREIGN_KEY_CHECKS = 0;");
        } finally {
            try {
                if (statement != null) statement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
    }

    public void enableForeignKeyCheck() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ds.getConnection();
            statement = connection.createStatement();
            statement.execute("SET FOREIGN_KEY_CHECKS = 1;");
        } finally {
            try {
                if (statement != null) statement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
    }
    /*Modifiche finiscono qui*/

    public synchronized float getPriceWithoutIVA(int productId) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String selectSQL = "SELECT price, iva FROM product WHERE code = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productId);

            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                float priceWithIVA = rs.getFloat("price");
                int ivaPercentage = rs.getInt("iva");
                // Calcola il prezzo senza IVA
                return priceWithIVA / (1 + (ivaPercentage / 100.0f));
            } else {
                throw new SQLException("Errore nel recupero del prezzo senza IVA del prodotto");
            }
        } catch (SQLException e) {
            throw new SQLException("Errore nel recupero del prezzo senza IVA del prodotto", e);
        } finally {
            // Assicurati di chiudere le risorse in un blocco finally per evitare perdite di memoria
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    public synchronized Collection<ProductBean> doRetrieveByCategory(String category) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> products = new LinkedList<ProductBean>();

        String selectSQL = "SELECT * FROM product WHERE category = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, category); // Imposta il parametro della categoria

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

    public float calculateDiscountedPrice(int productCode) throws SQLException {
        ProductBean PB = new ProductBean();
        PB = doRetrieveByKey(productCode);
        float price = PB.getPrice();
        float discount = PB.getDiscount();
        return price - (price * (discount / 100));
    }

    public Collection<ProductBean> getAllProducts() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> products = new LinkedList<ProductBean>();

        String selectSQL = "SELECT * FROM " + ProductModelDS.TABLE_NAME;

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
    } // fine metodo


    public Collection<ProductBean> GetDiscountedProductsList() throws SQLException {
        // Ottieni tutti i prodotti
        Collection<ProductBean> allProducts = this.getAllProducts();

        // Filtra la lista per includere solo i prodotti con uno sconto maggiore di zero
        return allProducts.stream()
                .filter(product -> product.getDiscount() > 0)
                .collect(Collectors.toList());
    }

    public Collection<ProductBean> getRandomDiscountedProducts() throws SQLException {
        // Ottieni tutti i prodotti scontati
        List<ProductBean> allDiscountedProducts = new ArrayList<>(this.GetDiscountedProductsList());

        // Mescola la lista
        Collections.shuffle(allDiscountedProducts);

        // Seleziona i primi 5 prodotti
        List<ProductBean> randomProducts = allDiscountedProducts.subList(0, Math.min(7, allDiscountedProducts.size()));

        return randomProducts;
    }

    public Collection<ProductBean> getRandomProducts() throws SQLException {
        // Ottieni tutti i prodotti
        List<ProductBean> allProducts = new ArrayList<>(this.getAllProducts());

        System.out.println("Debug: " + allProducts.size());

        // Mescola la lista
        Collections.shuffle(allProducts);

        // Seleziona i primi 5 prodotti
        List<ProductBean> randomProducts = allProducts.subList(0, allProducts.size());

        return randomProducts;
    }



}
