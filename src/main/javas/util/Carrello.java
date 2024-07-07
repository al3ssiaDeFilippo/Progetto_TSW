package main.javas.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.javas.bean.CartBean;
import main.javas.model.Order.CartModel;
import main.javas.bean.ProductBean;
import main.javas.model.Product.ProductModelDS;

public class Carrello {
    private List<CartBean> prodotti;

    public Carrello() {
        prodotti = new ArrayList<>();
    }

    public void aggiungi(CartBean prodotto) {
        ProductModelDS productModel = new ProductModelDS();
        ProductBean product = null;
        try {
            product = productModel.doRetrieveByKey(prodotto.getProductCode());

            for (CartBean p : prodotti) {
                if (p.getProductCode() == prodotto.getProductCode() &&
                        p.getFrame().equals(prodotto.getFrame()) &&
                        p.getFrameColor().equals(prodotto.getFrameColor()) &&
                        p.getSize().equals(prodotto.getSize())) {
                    // If the product with the same attributes is already in the cart, increase the quantity
                    int newQuantity = p.getQuantity() + prodotto.getQuantity();
                    if (newQuantity > product.getQuantity()) {
                        p.setQuantity(product.getQuantity());
                        System.out.println("Requested quantity exceeds available quantity. Set to maximum available quantity.");
                    } else {
                        p.setQuantity(newQuantity);
                    }
                    return;
                }
            }
            // If the product is not already in the cart, add it
            if (prodotto.getQuantity() > product.getQuantity()) {
                prodotto.setQuantity(product.getQuantity());
                System.out.println("Requested quantity exceeds available quantity. Set to maximum available quantity.");
            }
            prodotti.add(prodotto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rimuovi(CartBean prodotto) {
        Iterator<CartBean> iterator = prodotti.iterator();
        while (iterator.hasNext()) {
            CartBean p = iterator.next();
            if (p.getProductCode() == prodotto.getProductCode()) {
                iterator.remove();
                break; // Esci dopo aver rimosso il prodotto
            }
        }
    }

    public void aggiornaQuantita(int code, int quantity) {
        for (CartBean prodotto : prodotti) {
            if (prodotto.getProductCode() == code) {
                prodotto.setQuantity(quantity);
                break;
            }
        }
    }

    // Restituisce il prezzo totale del carrello considerando lo sconto
    public float getCartTotalPriceWithDiscount(CartModel model) throws SQLException {
        float totalPrice = 0;
        for (CartBean prodotto : prodotti) {
            float discountedPrice = model.getSingleProductDiscountedPrice(prodotto);
            totalPrice += discountedPrice;
        }
        return totalPrice;
    }

    // Restituisce il prezzo totale del carrello senza considerare lo sconto
    public float getCartTotalPriceWithoutDiscount(CartModel cart) {
        float totalPrice = 0;
        for (CartBean prodotto : prodotti) {
            totalPrice += prodotto.getPrice() * prodotto.getQuantity();
        }
        return totalPrice;
    }

    public boolean contieneProdotto(int code) {
        for (CartBean prodotto : prodotti) {
            if (prodotto.getProductCode() == code) {
                return true;
            }
        }
        return false;
    }

    public List<CartBean> getProdotti() {
        return prodotti;
    }

    public void svuota() {
        prodotti.clear();
    }

    public CartBean getCartItem(CartBean cartItem) {
        for (CartBean item : this.prodotti) {
            if (item.getProductCode() == cartItem.getProductCode() &&
                    item.getFrame().equals(cartItem.getFrame()) &&
                    item.getFrameColor().equals(cartItem.getFrameColor()) &&
                    item.getSize().equals(cartItem.getSize())) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Carrello [prodotti=" + prodotti + "]";
    }
}
