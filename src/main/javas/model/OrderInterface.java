package main.javas.model;

import java.sql.SQLException;
import java.util.Collection;

public interface OrderInterface {
    public void doSave(ProductBean product, int quantityToAdd) throws SQLException;
    public boolean doDelete(ProductBean product) throws SQLException;
    public ProductBean doRetrieveByKey(int code) throws SQLException;
    public Collection<ProductBean> doRetrieveAll(String order) throws SQLException;
}
