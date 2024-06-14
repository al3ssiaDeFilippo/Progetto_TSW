package main.javas.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CartModel {

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

    //salva un prodotto nel carrello del db
    public synchronized void doSave(CartBean cart) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT * FROM " + CartModel.TABLE_NAME
                + " WHERE IDUSER = ? AND PRODUCTCODE = ?";
        String insertSQL = "INSERT INTO " + CartModel.TABLE_NAME
                + " (IDUSER, PRODUCTCODE, QUANTITY, PRICE) VALUES (?, ?, ?, ?)";
        String updateSQL = "UPDATE " + CartModel.TABLE_NAME
                + " SET QUANTITY = ? WHERE IDUSER = ? AND PRODUCTCODE = ?";

        try {
            con = ds.getConnection();
            con.setAutoCommit(false);  // Disable auto-commit

            // Check if the product already exists in the user's cart
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, cart.getIdUser());
            preparedStatement.setInt(2, cart.getProductCode());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // If the product exists, update the quantity
                preparedStatement = con.prepareStatement(updateSQL);
                preparedStatement.setInt(1, cart.getQuantity());
                preparedStatement.setInt(2, cart.getIdUser());
                preparedStatement.setInt(3, cart.getProductCode());
            } else {
                // If the product does not exist, insert a new row
                preparedStatement = con.prepareStatement(insertSQL);
                preparedStatement.setInt(1, cart.getIdUser());
                preparedStatement.setInt(2, cart.getProductCode());
                preparedStatement.setInt(3, cart.getQuantity());
                preparedStatement.setFloat(4, cart.getPrice());
            }

            preparedStatement.executeUpdate();

            con.commit();  // Commit the transaction
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } finally {
                if (con != null) {
                    con.setAutoCommit(true);  // Re-enable auto-commit
                    con.close();
                }
            }
        }
    }

    //cerca i prodotti in base all'utente
    public synchronized List<CartBean> doRetrieveByUser(int userId) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        List<CartBean> cartItems = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + CartModel.TABLE_NAME + " WHERE IDUSER = ?";

        try {
            con = ds.getConnection();
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                CartBean bean = new CartBean();

                bean.setIdCart(rs.getInt("idCart"));
                bean.setIdUser(rs.getInt("idUser"));
                bean.setProductCode(rs.getInt("productCode"));
                bean.setQuantity(rs.getInt("quantity"));
                bean.setPrice(rs.getFloat("price"));

                cartItems.add(bean);
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
        return cartItems;
    }

    //elimina un prodotto dal carrello
    public synchronized boolean doDelete(int productCode) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + CartModel.TABLE_NAME + " WHERE PRODUCTCODE = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, productCode);

            result = preparedStatement.executeUpdate();

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
        return (result != 0);
    }

    //restituisce il prezzo totale del carrello, considerando lo sconto
    public float getDiscountedTotalPrice(List<CartBean> cartItems) throws SQLException {
        float totalPrice = 0.0f;
        ProductModelDS productModel = new ProductModelDS();

        for (CartBean cartItem : cartItems) {
            // Retrieve the associated product for each cart item
            ProductBean product = productModel.doRetrieveByKey(cartItem.getProductCode());

            // Calculate the discounted price
            float discount = product.getDiscount() / 100.0f;
            float discountedPrice = product.getPrice() * (1 - discount);

            // Add the total price for this product to the total price for all products
            totalPrice += discountedPrice * cartItem.getQuantity();
        }

        // Round the total price to two decimal places
        totalPrice = Math.round(totalPrice * 100.0f) / 100.0f;

        return totalPrice;
    }

    //restituisce il prezzo scontato di un prodotto
    public float getSingleProductDiscountedPrice(CartBean cartItem) throws SQLException {
        float totalPrice = 0.0f;
        ProductModelDS productModel = new ProductModelDS();

        // Retrieve the associated product for the cart item
        ProductBean product = productModel.doRetrieveByKey(cartItem.getProductCode());

        // Calculate the discount as a percentage
        float discount = product.getDiscount() / 100.0f;

        // Calculate the discounted price for one unit of the product
        float discountedPrice = product.getPrice() * (1 - discount);

        // Calculate the total price for the cart item (discounted price * quantity)
        totalPrice = discountedPrice * cartItem.getQuantity();

        // Round the total price to two decimal places
        totalPrice = Math.round(totalPrice * 100.0f) / 100.0f;

        return totalPrice;
    }

    //restituisce il prezzo totale del carrello senza considerare lo sconto
    public float getTotalPriceWithDiscount(List<CartBean> cartItems) throws SQLException {
        float totalPrice = 0.0f;

        for (CartBean cartItem : cartItems) {

            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        // Round the total price to two decimal places
        totalPrice = Math.round(totalPrice * 100.0f) / 100.0f;

        return totalPrice;
    }

    public boolean checkDiscount(CartBean cartItem) throws SQLException {
        ProductModelDS productModel = new ProductModelDS();
        ProductBean product = productModel.doRetrieveByKey(cartItem.getProductCode());
        return product.getDiscount() > 0;
    }

    //controlla se un prodotto esiste
    public boolean productExists(int productCode) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT COUNT(*) FROM product WHERE code = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, productCode);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // If the count is greater than 0, then the product exists
                return rs.getInt(1) > 0;
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

        return false;
    }

    //restituisce la quantità massima di un prodotto
    public int ProductMaxQuantity(CartBean cartItem) throws SQLException {
        ProductModelDS productModel = new ProductModelDS();
        ProductBean product = productModel.doRetrieveByKey(cartItem.getProductCode());
        int productQuantity = product.getQuantity();
        return productQuantity;
    }


    public synchronized List<CartBean> doRetrieveAll(int idUser) throws SQLException {
    Connection con = null;
    PreparedStatement preparedStatement = null;
    List<CartBean> cartItems = new ArrayList<CartBean>();

    String selectSQL = "SELECT * FROM " + CartModel.TABLE_NAME + " WHERE idUser = ?";

    try {
        con = ds.getConnection();
        preparedStatement = con.prepareStatement(selectSQL);
        preparedStatement.setInt(1, idUser);

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            CartBean cartItem = new CartBean();
            cartItem.setIdCart(rs.getInt("idCart"));
            cartItem.setProductCode(rs.getInt("productCode"));
            cartItem.setQuantity(rs.getInt("quantity"));
            cartItem.setPrice(rs.getFloat("price"));
            cartItem.setIdUser(rs.getInt("idUser")); // Use 'idUser' instead of 'IDUSER'
            cartItems.add(cartItem);
        }
    } finally {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (con != null) {
            con.close();
        }
    }
    return cartItems;
    }



}