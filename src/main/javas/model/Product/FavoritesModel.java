package main.javas.model.Product;

import main.javas.bean.FavoritesBean;
import main.javas.bean.ProductBean;
import main.javas.bean.UserBean;

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

public class FavoritesModel {

    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/storage");

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private static final String TABLE_NAME = "favorites";

    public synchronized int doSave(ProductBean product, UserBean user) throws SQLException {
        Connection con = null;
        PreparedStatement prep = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME + " (idUser, productCode) VALUES (?, ?)";

        try {
            con = ds.getConnection();
            prep = con.prepareStatement(insertSQL);
            prep.setInt(1, user.getIdUser());
            prep.setInt(2, product.getCode());

            return prep.executeUpdate();
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized int doDelete(ProductBean product, UserBean user) throws SQLException {
        Connection con = null;
        PreparedStatement prep = null;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE idUser = ? AND productCode = ?";

        try {
            con = ds.getConnection();
            prep = con.prepareStatement(deleteSQL);
            prep.setInt(1, user.getIdUser());
            prep.setInt(2, product.getCode());

            return prep.executeUpdate();
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Collection<FavoritesBean> doRetrieveAll(UserBean user) throws SQLException {
    Connection con = null;
    PreparedStatement prep = null;
    ResultSet rs = null;

    String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE idUser = ?";

    try {
        con = ds.getConnection();
        prep = con.prepareStatement(selectSQL);
        prep.setInt(1, user.getIdUser());

        rs = prep.executeQuery();

        Collection<FavoritesBean> favorites = new LinkedList<FavoritesBean>();

        while (rs.next()) {
            FavoritesBean bean = new FavoritesBean();
            bean.setFavId(rs.getInt("favId"));
            bean.setIdUser(rs.getInt("idUser"));
            bean.setProductCode(rs.getInt("productCode"));
            favorites.add(bean);
        }

        return favorites;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean isFavorite(ProductBean product, UserBean user) throws SQLException {
        Connection con = null;
        PreparedStatement prep = null;
        ResultSet rs = null;

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE idUser = ? AND productCode = ?";

        try {
            con = ds.getConnection();
            prep = con.prepareStatement(selectSQL);
            prep.setInt(1, user.getIdUser());
            prep.setInt(2, product.getCode());

            rs = prep.executeQuery();

            return rs.next();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (prep != null) {
                    prep.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
