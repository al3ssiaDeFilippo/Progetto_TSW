package main.javas.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.javas.model.CartBean;
import main.javas.model.CartModel;
import main.javas.model.ProductBean;
import main.javas.model.ProductModelDS;

public class Carrello {
    private List<CartBean> prodotti;

    public Carrello() {
        prodotti = new ArrayList<>();
    }

    public void aggiungi(CartBean prodotto) {
        for (CartBean p : prodotti) {
            if (p.getCode() == prodotto.getCode()) {
                // Se il prodotto è già presente, aumenta la quantità e esci dal metodo
                p.setQuantity(p.getQuantity()  + prodotto.getQuantity());
                return;
            }
        }
        // Se il prodotto non è già presente, aggiungilo al carrello
        prodotti.add(prodotto);
    }

    public void rimuovi(CartBean prodotto) {
        Iterator<CartBean> iterator = prodotti.iterator();
        while (iterator.hasNext()) {
            CartBean p = iterator.next();
            if (p.getCode() == prodotto.getCode()) {
                iterator.remove();
                break; // Esci dopo aver rimosso il prodotto
            }
        }
    }

    public void aggiornaQuantita(int code, int quantity) {
        for (CartBean prodotto : prodotti) {
            if (prodotto.getCode() == code) {
                prodotto.setQuantity(quantity);
                break;
            }
        }
    }

    public float calcolaPrezzoTotale(CartModel model) throws SQLException {
        float totalPrice = 0;
        for(CartBean prodotto : prodotti) {
            float discountedPrice = model.getDiscountedPrice(prodotto.getCode());
            totalPrice += discountedPrice * prodotto.getQuantity();
        }
        return totalPrice;
    }

    public boolean contieneProdotto(int code) {
        for (CartBean prodotto : prodotti) {
            if (prodotto.getCode() == code) {
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

    @Override
    public String toString() {
        return "Carrello [prodotti=" + prodotti + "]";
    }
}
