package main.javas.util;

import java.util.ArrayList;
import java.util.List;

import main.javas.model.CartBean;

//Implementazione dei metodi per la gestione del carrello

public class Carrello {
    //lista di prodotti presenti nel carrello
    private List<CartBean> prodotti;

    public Carrello() {
        prodotti = new ArrayList<CartBean>();
    }

    //aggiunge un prodotto alla lista del carrello
    public void aggiungi(CartBean prodotto) {
        prodotti.add(prodotto);
    }

    //rimuove un prodotto dalla lista del carrello
    public void rimuovi(CartBean prodotto) {
        prodotti.remove(prodotto);
    }

    //restituisce la lista dei prodotti del carrello
    public List<CartBean> getProdotti() {
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
