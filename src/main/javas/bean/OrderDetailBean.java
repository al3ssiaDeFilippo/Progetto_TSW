package main.javas.bean;

public class OrderDetailBean {
    private int idUser;
    private int productCode;
    private int quantity;
    private String frame;
    private String frameColor;
    private String size;
    private float price;
    /*Inizio modifiche qui*/
    private int iva;
    /*Fine modifiche qui*/
    private int idOrder;

    public OrderDetailBean(){
        idUser = 1;
        productCode = 0;
        quantity = 0;
        frame = "";
        frameColor = "";
        size = "";
        price = 0;
        /*Inizio modifiche qui*/
        iva = 0;
        /*Fine modifiche qui*/
        idOrder = 0;
    }

    public int getIdUser() {return idUser;}
    public int getProductCode() {return productCode;}
    public int getQuantity() {return quantity;}
    public String getFrame() {return frame;}
    public String getFrameColor() {return frameColor;}
    public String getSize() {return size;}
    public float getPrice() {return price;}
    /*Inizio modifiche qui*/
    public int getIva() {return iva;}
    /*Fine modifiche qui*/
    public int getIdOrder() {return idOrder;}

    public void setIdUser(int idUser) {this.idUser = idUser;}
    public void setProductCode(int productCode) {this.productCode = productCode;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setFrame(String frame) {this.frame = frame;}
    public void setFrameColor(String frameColor) {this.frameColor = frameColor;}
    public void setSize(String size) {this.size = size;}
    public void setPrice(float price) {this.price = price;}
    /*Inizio modifiche qui*/
    public void setIva(int iva) {this.iva = iva;}
    /*Fine modifiche qui*/
    public void setIdOrder(int idOrder) {this.idOrder = idOrder;}
}
