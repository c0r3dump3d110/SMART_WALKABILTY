package com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs;

public class IdsBlob {
    private String Token;
    private int id_annonce = 0;
    private int id_organisation = 0;
    private int[] anonnce_ids;

    public IdsBlob() {
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(int id_annonce) {
        this.id_annonce = id_annonce;
    }

    public int getId_organisation() {
        return id_organisation;
    }

    public void setId_organisation(int id_organisation) {
        this.id_organisation = id_organisation;
    }

    public int[] getAnonnce_ids() {
        return anonnce_ids;
    }

    public void setAnonnce_ids(int[] anonnce_ids) {
        this.anonnce_ids = anonnce_ids;
    }
}
