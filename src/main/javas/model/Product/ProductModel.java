package main.javas.model.Product;

import main.javas.bean.ProductBean;

import java.sql.SQLException;
import java.util.Collection;

public interface ProductModel {
    public int doSave(ProductBean product) throws SQLException;

    public boolean doDelete(int code) throws SQLException;

    public ProductBean doRetrieveByKey(int code) throws SQLException;

    public Collection<ProductBean> doRetrieveAll(String order) throws SQLException;

    public void doUpdate(ProductBean product) throws SQLException;

    /*Modifiche Iniziano qui*/
    public void enableForeignKeyCheck() throws SQLException;

    public void disableForeignKeyCheck() throws SQLException;
    /*Modifiche finiscono qui*/
}