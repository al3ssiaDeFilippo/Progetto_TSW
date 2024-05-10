package main.javas.model;

import main.javas.model.ProductBean;

//Oggetto che rappresenta un prodotto all'interno del carrello
public class CartBean {
    private static final long serialVersionUID = 1L;

    int code;
    int quantity;
    float price;
    int idProduct;

    private static int countQuantity = 0;

    public CartBean() {
        code = -1;
        quantity = 0;
        price = 0;
        idProduct = 0;

        countQuantity++;
    }

    //restituisce il prezzo del prodotto
    public float getPrice(ProductBean product) {
        return product.getPrice();
    }

    public void setPrice(ProductBean product) {
        price = product.getPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity() {
        quantity = countQuantity;
    }

    public int getCode() {
        return code;
    }

    public int getIdProduct(ProductBean product) {
        return product.getCode();
    }

    public void setIdProduct(ProductBean product) { idProduct = product.getCode(); }

    @Override
    public String toString() {
        return "CartBean{" +
                "code=" + code +
                ", quantity=" + quantity +
                ", price=" + price +
                ", idProduct=" + idProduct +
                '}';
    }
}
