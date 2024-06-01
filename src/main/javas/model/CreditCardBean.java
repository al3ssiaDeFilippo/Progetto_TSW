package main.javas.model;

import java.sql.Date;

public class CreditCardBean {
    private static final long serialVersionUID = 1L;

    int idCard;
    String ownerCard;
    Date expirationDate;
    int cvv;

    public CreditCardBean(){
        this.idCard = 0;
        this.ownerCard = "";
        this.expirationDate = null;
        this.cvv = 0;
    }

    public int getIdCard(){return idCard;}
    public String getOwnerCard(){return ownerCard;}
    public Date getExpirationDate(){return expirationDate;}
    public int getCvv(){return cvv;}


    public void setIdCard(int idCard){this.idCard = idCard;}
    public void setOwnerCard(String ownerCard){this.ownerCard = ownerCard;}
    public void setExpirationDate(Date expitaionDate){this.expirationDate = expirationDate;}
    public void setCvv(int cvv){this.cvv = cvv;}

    @Override
    public String toString() {
        return "creditCardBean{" +
                "idCard=" + idCard +
                ", ownerCard='" + ownerCard + '\'' +
                ", expirationDate=" + expirationDate +
                ", cvv=" + cvv +
                '}';
    }
}