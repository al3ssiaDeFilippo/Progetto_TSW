package main.javas.model.Order;

import java.util.Date;

public class OrderBean {
    int idOrder;
    int idUser;
    int idShipping;
    String idCreditCard;
    Date orderDate;
    float totalPrice;

    // Getters
    public int getIdOrder(){return idOrder;}
    public int getIdUser(){return idUser;}
    public int getIdShipping(){return idShipping;}
    public String getIdCreditCard(){return idCreditCard;}
    public java.sql.Date getOrderDate() {return new java.sql.Date(orderDate.getTime());}
    public float getTotalPrice(){return totalPrice;}

    // Setters
    public void setIdOrder(int idOrder){this.idOrder = idOrder;}
    public void setIdUser(int idUser){this.idUser = idUser;}
    public void setIdShipping(int idShipping){this.idShipping = idShipping;}
    public void setIdCreditCard(String idCreditCard){this.idCreditCard = idCreditCard;}
    public void setOrderDate(Date orderDate){this.orderDate = orderDate;}
    public void setTotalPrice(float totalPrice){this.totalPrice = totalPrice;}

    @Override
    public String toString() {
        return "OrderBean{" +
                "idOrder=" + idOrder +
                ", idUser=" + idUser +
                ", idShipping=" + idShipping +
                ", idCreditCard='" + idCreditCard + '\'' +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}