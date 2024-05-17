package main.javas.model;

import java.io.Serializable;
import java.sql.Blob;

public class ProductBean implements Serializable {

    private Blob foto;

    private static final long serialVersionUID = 1L;

    //Attributi della tabella 'product'
    int code;
    String productName;
    String details;
    int quantity;
    String category;
    float price;
    int iva;
    int discount;
    String frame;
    String frameColor;
    String size;
    Blob photo;

    //costruttore di ProductBean()
    public ProductBean() {
        code = -1;
        productName = "";
        details = "";
        quantity = 0;
        category = "";
        price = 0;
        iva = 0;
        discount = 0;
        frame = "";
        frameColor = "";
        size = "";
        photo = null;
    }

    //metodi get
    public int getCode(){return code;}
    public String getProductName(){return productName;}
    public String getDetails(){return details;}
    public int getQuantity(){return quantity;}
    public String getCategory(){return category;}
    public float getPrice(){return price;}
    public int getIva(){return iva;}
    public int getDiscount(){return discount;}
    public String getFrame(){return frame;}
    public String getFrameColor(){return frameColor;}
    public String getSize(){return size;}
    public Blob getPhoto() {return photo;}

    //metodi set
    public void setCode(int code){this.code = code;}
    public void setProductName(String productName){this.productName=productName;}
    public void setDetails(String details){this.details=details;}
    public void setQuantity(int quantity){this.quantity=quantity;}
    public void setCategory(String category){this.category=category;}
    public void setPrice(float price){this.price=price;}
    public void setIva(int iva){this.iva=iva;}
    public void setDiscount(int discount){this.discount=discount;}
    public void setFrame(String frame){this.frame=frame;}
    public void setFrameColor(String frameColor){this.frameColor=frameColor;}
    public void setSize(String size){this.size=size;}
    public void setPhoto(Blob photo) {this.photo = photo;}

    //ToString


    @Override
    public String toString() {
        return "ProductBean{" +
                "code=" + code +
                ", productName='" + productName + '\'' +
                ", details='" + details + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", iva=" + iva +
                ", discount=" + discount +
                ", frame='" + frame + '\'' +
                ", frameColor='" + frameColor + '\'' +
                ", size='" + size + '\'' +
                ", photo=" + photo +
                '}';
    }
}
