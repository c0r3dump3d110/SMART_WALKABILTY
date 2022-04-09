package com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs;

public class IdsSiteBlob {
    private String Token;
    private int id_site = 0;
    private int[] site_ids;
    private int id_organisation = 0;

    public IdsSiteBlob() {
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getId_site() {
        return id_site;
    }

    public void setId_site(int id_site) {
        this.id_site = id_site;
    }

    public int[] getSite_ids() {
        return site_ids;
    }

    public void setSite_ids(int[] site_ids) {
        this.site_ids = site_ids;
    }

    public int getId_organisation() {
        return id_organisation;
    }

    public void setId_organisation(int id_organisation) {
        this.id_organisation = id_organisation;
    }
}
