package com.fstm.coredumped.smartwalkabilty.web.Model.bo;

public class Image {
    private int id;
    private String urlImage;
    private Annonce annonce;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Image(String urlImage,Annonce annonce) {
        this.urlImage = urlImage;
        this.annonce=annonce;
    }
    public Image(){

    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }
}
