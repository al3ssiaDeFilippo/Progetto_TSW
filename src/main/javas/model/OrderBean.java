package main.javas.model;

import java.io.Serializable;

public class OrderBean {
    private static final long serialVersionUID = 1L;

    int code;
    int quantity;
    float price;
    int idProduct;

    public OrderBean() {
        code = -1;
        quantity = 0;
        price = 0;
        idProduct = -1;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "code=" + code +
                ", quantity=" + quantity +
                ", price=" + price +
                ", idProduct=" + idProduct +
                '}';
    }

}
