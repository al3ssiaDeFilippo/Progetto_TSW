package main.javas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

/*
La classe ProductModelDM implementava l'interfaccia ProductModel
e forniva un'implementazione dei suoi metodi utilizzando una
connessione diretta tramite DriverManager per accedere al database.
*/
public class ProductModelDM implements ProductModel {

    //nome della tabella in sql (prodotto)
    private static final String TABLE_NAME = "product";

    //Metodo che salva un oggetto ProductBean nel database.
    //Prende i dati dal ProductBean e li inserisce nella tabella del database corrispondente.
    @Override
    public synchronized void doSave(ProductBean product) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        //Query per aggiungere un prodotto all'interno del database
        String insertSQL = "INSERT INTO " + ProductModelDM.TABLE_NAME
                + " (PRODUCTNAME, DETAILS, QUANTITY, CATEGORY, PRICE, IVA, DISCOUNT) VALUES (?, ?, ?, ?, ?, ?, ?)";

        //Assegna i valori passati dall'utente, ai segnaposti della query
        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getDetails());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getCategory());
            preparedStatement.setFloat(5, product.getPrice());
            preparedStatement.setInt(6, product.getIva());
            preparedStatement.setInt(7, product.getDiscount());

            //Esegue la query
            preparedStatement.executeUpdate();

            connection.commit();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
    }

    //Metodo che recupera un oggetto ProductBean dal database utilizzando il codice del prodotto come chiave.
    //Estrae i dati relativi al prodotto corrispondente al codice dalla tabella del database
    //e restituisce un oggetto ProductBean popolato con tali dati.
    @Override
    public synchronized ProductBean doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ProductBean bean = new ProductBean();

        //Query per filtrare i prodotti in base al codice prodotto
        String selectSQL = "SELECT * FROM " + ProductModelDM.TABLE_NAME + " WHERE CODE = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
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
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return bean;
    }

    //Metodo che elimina un prodotto dal database utilizzando il codice del prodotto come chiave.
    //Rimuove dalla tabella del database il record corrispondente al codice specificato
    //e restituisce true se l'eliminazione ha avuto successo, altrimenti false.
    @Override
    public synchronized boolean doDelete(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + ProductModelDM.TABLE_NAME + " WHERE CODE = ?";

        try {
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, code);

            result = preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return (result != 0);
    }

    //Metodo che recupera tutti i prodotti dal database e li restituisce come una collezione di oggetti ProductBean.
    @Override
    public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<ProductBean> products = new LinkedList<ProductBean>();

        String selectSQL = "SELECT * FROM " + ProductModelDM.TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = DriverManagerConnectionPool.getConnection();
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

                products.add(bean); // Aggiungi l'oggetto ProductBean alla collezione
            }

        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(connection);
            }
        }
        return products;
    }
}