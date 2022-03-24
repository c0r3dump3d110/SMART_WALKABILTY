package com.fstm.coredumped.smartwalkabilty.web.Model.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Annonce implements Serializable
{
    private int id;
    private Date dateDebut,dateFin;
    private String titre,description;
    private String urlPrincipalImage;
    private List<Image> subImgs =new ArrayList<Image>();
    private List<Site> sites=new ArrayList<Site>();
    public Annonce(){}
    public Annonce(Date dateDebut, Date dateFin, String titre, String description, String urlPrincipalImage) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.titre = titre;
        this.description = description;
        this.urlPrincipalImage = urlPrincipalImage;
    }
    public List<Site> getSites() {
        return sites;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlPrincipalImage() {
        return urlPrincipalImage;
    }

    public List<Image> getSubImgs() {
        return subImgs;
    }
    public void AddImage(Image img){
        subImgs.add(img);
    }
    public void AddSite(Site site){
        sites.add(site);
    }
    public void setUrlPrincipalImage(String urlPrincipalImage) {
        this.urlPrincipalImage = urlPrincipalImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Annonce annonce = (Annonce) o;
        return id == annonce.id;
    }
    public Annonce CreateCopyWithoutSites_Images(){
        Annonce annonce=new Annonce(dateDebut,dateFin,titre,description,urlPrincipalImage);
        annonce.setId(this.getId());
        return annonce;
    }
}
