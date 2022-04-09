package com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs;

public class CatIdsBlob {
    String Token;
    int id_cat = 0;

    public CatIdsBlob() {
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }
}
