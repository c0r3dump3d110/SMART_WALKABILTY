package com.fstm.coredumped.smartwalkabilty.web.Model.bo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Categorie implements Serializable {
    private int id;
    private String categorie;
    private List<Annonce> annonces=new LinkedList<>();

    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }
}
