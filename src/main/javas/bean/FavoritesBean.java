package main.javas.bean;

public class FavoritesBean {
    private int favId;
    private int idUser;
    private int productCode;

    public FavoritesBean() {
        this.favId = -1;
        this.idUser = 0;
        this.productCode = 0;
    }

    public void setFavId(int favId) {this.favId = favId;}
    public int getFavId() {return this.favId;}
    public void setIdUser(int idUser) {this.idUser = idUser;}
    public void setProductCode(int productCode) {this.productCode = productCode;}
    public int getIdUser() {return this.idUser;}
    public int getProductCode() {return this.productCode;}

    @Override
    public String toString() {
        return "FavoritesBean{" +
                "favId=" + favId +
                ", idUser=" + idUser +
                ", productCode=" + productCode +
                '}';
    }
}
