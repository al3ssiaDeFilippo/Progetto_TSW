package main.javas.model.Order;

import java.io.Serializable;

public class ShippingBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idShipping;
    private String recipientName;
    private String address;
    private String city;
    private int cap;
    private int idUser;

    public ShippingBean(){
        idShipping = -1;
        recipientName = "";
        address = "";
        city = "";
        cap = 0;
        idUser = -1;
    }

    public int getIdShipping() {return idShipping;}
    public String getRecipientName() {return recipientName;}
    public String getAddress() {return address;}
    public String getCity() {return city;}
    public int getIdUser() {return idUser;}
    public int getCap() {return cap;}

    public void setIdShipping(int idShipping) {this.idShipping = idShipping;}
    public void setRecipientName(String recipientName) {this.recipientName = recipientName;}
    public void setAddress(String address) {this.address = address;}
    public void setCity(String city) {this.city = city;}
    public void setIdUser(int idUser) {this.idUser = idUser;}
    public void setCap(int cap) {this.cap = cap;}

    @Override
    public String toString(){
        return "ShippingBean{" +
                "idShipping=" + idShipping +
                ", recipientName='" + recipientName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", cap=" + cap +
                ", idUser=" + idUser +
                '}';
    }
}
