package main.javas.model.Order;

import java.sql.Date;

public class CreditCardBean {
    private static final long serialVersionUID = 1L;

    String idCard;
    String ownerCard;
    Date expirationDate;
    int cvv;
    int idUser; // New field

    public CreditCardBean(){
        this.idCard = "";
        this.ownerCard = "";
        this.expirationDate = null;
        this.cvv = 0;
        this.idUser = 0; // Initialize the new field
    }

    public String getIdCard(){return idCard;}
    public String getOwnerCard(){return ownerCard;}
    public Date getExpirationDate(){return expirationDate;}
    public int getCvv(){return cvv;}
    public int getIdUser(){return idUser;} // New getter

    public void setIdCard(String idCard){this.idCard = idCard;}
    public void setOwnerCard(String ownerCard){this.ownerCard = ownerCard;}
    public void setExpirationDate(Date expirationDate){this.expirationDate = expirationDate;}
    public void setCvv(int cvv){this.cvv = cvv;}
    public void setIdUser(int idUser){this.idUser = idUser;} // New setter



    @Override
    public String toString() {
        return "CreditCardBean{" +
                "idCard=" + idCard +
                ", ownerCard='" + ownerCard + '\'' +
                ", expirationDate=" + expirationDate +
                ", cvv=" + cvv +
                ", idUser=" + idUser + // Include the new field in the toString method
                '}';
    }
}