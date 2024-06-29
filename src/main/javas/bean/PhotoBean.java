package main.javas.bean;

import java.sql.Blob;

public class PhotoBean {

    private int idPhoto;
    private Blob photo;
    private int productCode;
    private String frame;
    private String frameColor;

    public PhotoBean() {
        idPhoto = -1;
        photo = null;
        productCode = -1;
        frame = null;
        frameColor = null;
    }

    public int getIdPhoto() {return idPhoto;}
    public Blob getPhoto() {return photo;}
    public int getProductCode() {return productCode;}
    public String getFrame() {return frame;}
    public String getFrameColor() {return frameColor;}

    public void setIdPhoto(int idPhoto) {this.idPhoto = idPhoto;}
    public void setPhoto(Blob photo) {this.photo = photo;}
    public void setProductCode(int productCode) {this.productCode = productCode;}
    public void setFrame(String frame) {this.frame = frame;}
    public void setFrameColor(String frameColor) {this.frameColor = frameColor;}

    @Override
    public String toString() {
        return "PhotoBean{" + "idPhoto=" + idPhoto + ", productCode=" + productCode + ", frame=" + frame + ", frameColor=" + frameColor + '}';
    }
}