package main.javas.model.Product;

import main.javas.bean.ProductBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchModel {
    private DataSource ds;

    public SearchModel() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/storage"); // Replace with your DataSource name
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public List<ProductBean> searchInDatabase(String query) {
        List<ProductBean> results = new ArrayList<>();
        // Corrected SQL string
        String sql = "SELECT * FROM product WHERE productName LIKE ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            // Correctly setting the parameter with percentage signs
            ps.setString(1, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductBean product = new ProductBean();
                    product.setCode(rs.getInt("code"));
                    product.setProductName(rs.getString("productName"));
                    product.setDetails(rs.getString("details"));
                    product.setPrice(rs.getFloat("price"));
                    product.setCategory(rs.getString("category"));
                    // Set other properties as needed
                    results.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}