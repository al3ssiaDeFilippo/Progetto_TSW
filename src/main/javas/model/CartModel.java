package main.javas.model;

import main.javas.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartModel {

    public void doSave(CartBean cart) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO cart (idUser, idProduct, quantity, price) VALUES (?, ?, ?, ?)";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, cart.getIdUser());
            preparedStatement.setInt(2, cart.getIdProduct());
            preparedStatement.setInt(3, cart.getQuantity());
            preparedStatement.setFloat(4, cart.getPrice());

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void doDelete(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM cart WHERE code = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public CartBean doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        CartBean cart = new CartBean();

        String selectSQL = "SELECT * FROM cart WHERE code = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                cart.setCode(rs.getInt("code"));
                cart.setIdUser(rs.getInt("idUser"));
                cart.setIdProduct(rs.getInt("idProduct"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setPrice(rs.getFloat("price"));
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return cart;
    }

    public boolean productExists(int productId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exists = false;

        String query = "SELECT COUNT(*) FROM product WHERE code = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = count > 0;
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return exists;
    }

    public List<CartBean> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        List<CartBean> carts = new ArrayList<>();

        String selectSQL = "SELECT * FROM cart";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                CartBean cart = new CartBean();
                cart.setCode(rs.getInt("code"));
                cart.setIdUser(rs.getInt("idUser"));
                cart.setIdProduct(rs.getInt("idProduct"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setPrice(rs.getFloat("price"));
                carts.add(cart);
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return carts;
    }

    public synchronized float getDiscountedPrice(int code) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        System.out.println("Debug: mi trovo nel metodo getDiscountedPrice e il codice è: " + code);

        String query = "SELECT productName, price, discount, ROUND(price - (price * discount / 100), 2) " +
                "AS price_after_discount FROM product WHERE code = ?";


        try {
            con = DriverManagerConnectionPool.getConnection();
            con.setAutoCommit(false);
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, code);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getFloat("price_after_discount");
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

    public int getProductQuantity(int code) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int quantity = 0;

        try {
            con = DriverManagerConnectionPool.getConnection();
            con.setAutoCommit(false);
            String selectSQL = "SELECT quantity FROM product WHERE code = ?";
            preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("quantity");
            } else {
                throw new SQLException("No product found with code: " + code);
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
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

        return quantity;
    }

    // Aggiungi il metodo updateQuantity
    public void updateQuantity(int code, int newQuantity) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String updateSQL = "UPDATE cart SET quantity = ? WHERE code = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, code);

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
