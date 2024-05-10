package main.javas.util;

import java.util.ArrayList;
import java.util.List;

import main.javas.model.CartBean;
import main.javas.model.ProductBean;

//Implementazione dei metodi per la gestione del carrello

public class Carrello {
    //lista di prodotti presenti nel carrello
    private List<ProductBean> prodotti;

    public Carrello() {
        prodotti = new ArrayList<ProductBean>();
    }

    //aggiunge un prodotto alla lista del carrello
    public void aggiungi(ProductBean prodotto) {
        prodotti.add(prodotto);
    }

    //rimuove un prodotto dalla lista del carrello
    public void rimuovi(ProductBean prodotto) {
        prodotti.remove(prodotto);
    }

    //restituisce la lista dei prodotti del carrello
    public List<ProductBean> getProdotti() {
        return prodotti;
    }

    //svuota la lista
    public void svuota() {
        prodotti.clear();
    }

    @Override
    public String toString() {
        return "Carrello [prodotti=" + prodotti + "]";
    }
}
