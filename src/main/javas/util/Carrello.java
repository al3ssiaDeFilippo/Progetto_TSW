package main.javas.util;

import java.util.ArrayList;
import java.util.List;
import main.javas.model.ProductBean;

public class Carrello {
    private List<ProductBean> prodotti;

    public Carrello() {
        prodotti = new ArrayList<ProductBean>();
    }

    public void aggiungi(ProductBean prodotto) {
        prodotti.add(prodotto);
    }

    public void rimuovi(ProductBean prodotto) {
        prodotti.remove(prodotto);
    }

    public List<ProductBean> getProdotti() {
        return prodotti;
    }

    public void svuota() {
        prodotti.clear();
    }

    public void removeProduct(ProductBean product) {
        prodotti.remove(product);
    }

    @Override
    public String toString() {
        return "Carrello [prodotti=" + prodotti + "]";
    }
}
