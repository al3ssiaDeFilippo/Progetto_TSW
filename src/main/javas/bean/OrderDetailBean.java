package main.javas.bean;

public class OrderDetailBean {
    private int idUser;
    private int productCode;
    private int quantity;
    private String frame;
    private String frameColor;
    private String size;
    private float price;
    private int idOrder;

    public OrderDetailBean(){
        idUser = 1;
        productCode = 0;
        quantity = 0;
        frame = "";
        frameColor = "";
        size = "";
        price = 0;
        idOrder = 0;
    }

    public int getIdUser() {return idUser;}
    public int getProductCode() {return productCode;}
    public int getQuantity() {return quantity;}
    public String getFrame() {return frame;}
    public String getFrameColor() {return frameColor;}
    public String getSize() {return size;}
    public float getPrice() {return price;}
    public int getIdOrder() {return idOrder;}

    public void setIdUser(int idUser) {this.idUser = idUser;}
    public void setProductCode(int productCode) {this.productCode = productCode;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setFrame(String frame) {this.frame = frame;}
    public void setFrameColor(String frameColor) {this.frameColor = frameColor;}
    public void setSize(String size) {this.size = size;}
    public void setPrice(float price) {this.price = price;}
    public void setIdOrder(int idOrder) {this.idOrder = idOrder;}

    /*EFFETTURE UN CONTROLLO SULLA CORNICE CHE STA SEMPRE IMPOSTATA A BIANCO ANCHE SE NON è PRESENTE*/


}
