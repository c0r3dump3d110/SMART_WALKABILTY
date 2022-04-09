package com.fstm.coredumped.smartwalkabilty.web.Model.Service;

import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Categorie;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.CatIdsBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOCategorie;
import com.google.gson.Gson;

public class CategoriesService
{
    JWTgv jwTgv=new JWTgv();
    public String getServ(CatIdsBlob blob){
        Gson s=new Gson();
        if(jwTgv.verifyToken(blob.getToken())) {
            if (blob.getId_cat()!=0) {
                Categorie categorie= DAOCategorie.getDaoCategorie().getById(blob.getId_cat());
                return s.toJson(categorie);
            }
            else
            {
                 return s.toJson(DAOCategorie.getDaoCategorie().Retrieve());
            }
        }else {
            return  "{ \"mes\":\" invalid Token  \" }";
        }
    }
}
