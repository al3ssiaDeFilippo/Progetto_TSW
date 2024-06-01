package main.javas.model;

import main.javas.model.ProductBean;

//Oggetto che rappresenta un prodotto all'interno del carrello
public class CartBean {
    private static final long serialVersionUID = 1L;

    int code;
    int quantity;
    float price;
    int idProduct;
    int idUser;

    public CartBean() {
        code = -1;
        quantity = 0;
        price = 0;
        idProduct = 0;
        idUser = 0;

    }

    public int getIdUser() { return idUser; }

    public void setIdUser(int idUser) { this.idUser = idUser; }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "code=" + code +
                ", quantity=" + quantity +
                ", price=" + price +
                ", idProduct=" + idProduct +
                ", idUser=" + idUser +
                '}';
    }
}
