package main.javas.model.Order;

//Oggetto che rappresenta un prodotto all'interno del carrello
public class CartBean {
    private static final long serialVersionUID = 1L;

    int idCart;
    int idUser;
    int productCode;
    int quantity;
    String frame;
    String frameColor;
    String size;
    float price;

    public CartBean() {
        idCart = -1;
        idUser = -1;
        productCode = -1;
        quantity = 1;
        frame = "";
        frameColor = "";
        size = "";
        price = 0.0f;
    }

    public int getIdCart() { return idCart; }
    public void setIdCart(int idCart) { this.idCart = idCart; }
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public int getProductCode() { return productCode; }
    public void setProductCode(int productCode) { this.productCode = productCode; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getFrame() { return frame; }
    public void setFrame(String frame) { this.frame = frame; }
    public String getFrameColor() { return frameColor; }
    public void setFrameColor(String frameColor) { this.frameColor = frameColor; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String toString() {
        return "CartBean{" +
                "idCart=" + idCart +
                ", idUser=" + idUser +
                ", productCode=" + productCode +
                ", quantity=" + quantity +
                ", frame='" + frame + '\'' +
                ", frameColor='" + frameColor + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }

}