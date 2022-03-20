package com.fstm.coredumped.smartwalkabilty.web.Model.bo;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Site
{
    private int id;
    private String Name;
    private Date dateCreated;
    private Organisation organisation;
    private GeoPoint localisation;

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    private List<Annonce> annonces = new LinkedList<Annonce>();

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }


    public void setLocalisation(GeoPoint gp) {
        this.localisation = gp;
    }


    public GeoPoint getLocalisation() {
        return localisation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Site() {
    }

    public Site(String name, Date dateCreated) {
        Name = name;
        this.dateCreated = dateCreated;
    }
    public void AddAnnonce(Annonce annonce)
    {
        annonces.add(annonce);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return id == site.id;
    }
    public Site CreateCopyWithoutAnnonce_organisation()
    {
        Site site = new Site(this.Name,this.dateCreated);
        site.setLocalisation(this.localisation);
        site.setId(this.id);
        return site;
    }


}
