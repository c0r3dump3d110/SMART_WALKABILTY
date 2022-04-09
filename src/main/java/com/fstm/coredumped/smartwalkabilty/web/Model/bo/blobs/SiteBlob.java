package com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Organisation;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SiteBlob {
    private int id;
    private String name, datecreated, Token;
    private int id_organisation;
    private GeoPoint geoLocation;

    public boolean verifyInfos() {
        if (name == null || datecreated == null || id_organisation == 0 || geoLocation == null || Token == null)
            return false;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getId_organisation() {
        return id_organisation;
    }

    public void setId_organisation(int id_organisation) {
        this.id_organisation = id_organisation;
    }

    public GeoPoint getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoPoint geoLocation) {
        this.geoLocation = geoLocation;
    }
    public void FillSiteFromBlob(Site site) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        site.setName(getName());
        site.setDateCreated(dateFormat.parse(getDatecreated()));
        // site.setOrganisation(Blob.id_organisation);
        Organisation organisation = new Organisation();
        organisation.setId(getId_organisation());
        site.setOrganisation(organisation);
        site.setLocalisation(getGeoLocation());
    }
}
