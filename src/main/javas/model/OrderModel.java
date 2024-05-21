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

public class OrderModel implements OrderInterface{
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
    private static final String TABLE_NAME = "cart";

    //metodo che salva un prodotto nel db nella tabella cart (salva un prodotto nel carrello)
    @Override
    public synchronized void doSave(ProductBean productInCart, int quantityToAdd) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            System.out.println("QuantityToAdd: " + quantityToAdd);
            con = ds.getConnection();
            con.setAutoCommit(false);
            if (isProductInCart(productInCart.getCode())) {
                updateQuantity(productInCart, quantityToAdd);
            } else {
                // Se il prodotto non è presente nel carrello, inserisce un nuovo record
                String insertSQL = "INSERT INTO " + OrderModel.TABLE_NAME + "(IDPRODUCT, QUANTITY, PRICE) VALUES (?, ?, ?)";
                preparedStatement = con.prepareStatement(insertSQL);
                preparedStatement.setInt(1, productInCart.getCode());
                preparedStatement.setInt(2, quantityToAdd);
                preparedStatement.setFloat(3, productInCart.getPrice());
                preparedStatement.executeUpdate();
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



    //elimina un prodotto dal carrello nel database
    @Override
    public synchronized boolean doDelete(ProductBean productInCart) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        ProductBean existingProduct = doRetrieveByKey(productInCart.getCode());
        if(existingProduct == null) {
            System.out.println("Prodotto non esistente nel carrello!");
            return false;
        } else {

            String insertSQL = "DELETE FROM " + OrderModel.TABLE_NAME + " WHERE IDPRODUCT = ?";

            try {
                con = ds.getConnection();
                con.setAutoCommit(false);
                preparedStatement = con.prepareStatement(insertSQL);
                preparedStatement.setInt(1, productInCart.getCode());
                result = preparedStatement.executeUpdate();
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
        return (result != 0);
    }

    @Override
    public synchronized ProductBean doRetrieveByKey(int code) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ProductBean product = null;

        String selectSQL = "SELECT * FROM " + OrderModel.TABLE_NAME + " WHERE IDPRODUCT = ?";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                product = new ProductBean();
                product.setQuantity(rs.getInt("QUANTITY"));
                product.setPrice(rs.getFloat("PRICE"));
                product.setCode(rs.getInt("IDPRODUCT"));
            }

            con.commit();
            con.setAutoCommit(true);

            if (product != null) {
                System.out.println("Prodotto recuperato con successo: " + product.getCode());
            } else {
                System.out.println("Nessun prodotto trovato con il codice: " + code);
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
        return product;
    }

    @Override
    public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        Collection<ProductBean> products = new LinkedList<ProductBean>();

        String selectSQL = "SELECT * FROM " + OrderModel.TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ProductBean bean = new ProductBean();
                bean.setQuantity(rs.getInt("QUANTITY"));
                bean.setPrice(rs.getFloat("PRICE"));
                bean.setCode(rs.getInt("IDPRODUCT"));
                products.add(bean);
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
        return products;
    }

    // Metodo per verificare se un prodotto è presente nel carrello
    private boolean isProductInCart(int productId) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        boolean exists = false;

        String selectSQL = "SELECT * FROM " + OrderModel.TABLE_NAME + " WHERE IDPRODUCT = ?";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                exists = true;
            }

            con.commit();
            con.setAutoCommit(true);
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
        return exists;
    }

    public synchronized float getTotalPrice() throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        float totalPrice = 0;

        String selectSQL = "SELECT ROUND(SUM(PRICE * QUANTITY), 2) AS TOTAL FROM " + OrderModel.TABLE_NAME;

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                totalPrice = rs.getFloat("TOTAL");
            }

            con.commit();
            con.setAutoCommit(true);
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
        return totalPrice;
    }

    public synchronized float getPriceWithoutIVA(int code) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String query = "SELECT PRICE, IVA, ROUND((PRICE / (1 + IVA / 100)), 2) AS PRICE_WITHOUT_IVA " +
                "FROM PRODUCT WHERE CODE = ?";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, code);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getFloat("PRICE_WITHOUT_IVA");
            } else {
                throw new SQLException("No product found with code: " + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
    }


    public synchronized void updateQuantity(ProductBean productInCart, int quantityToAdd) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);

            String updateSQL = "UPDATE " + OrderModel.TABLE_NAME + " SET QUANTITY = ? WHERE IDPRODUCT = ?";

            System.out.println("ciaooo");

            preparedStatement = con.prepareStatement(updateSQL);
            preparedStatement.setInt(1, quantityToAdd);
            preparedStatement.setInt(2, productInCart.getCode());
            preparedStatement.executeUpdate();

            con.commit();
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }
}