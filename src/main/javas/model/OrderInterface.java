package main.javas.model;

import java.sql.SQLException;
import java.util.Collection;

public interface OrderInterface {
    public void doSave(CartBean order) throws SQLException;
    public boolean doDelete(int code) throws SQLException;
    public CartBean doRetrieveByKey(int code) throws SQLException;
    public Collection<CartBean> doRetrieveAll(String order) throws SQLException;
}
