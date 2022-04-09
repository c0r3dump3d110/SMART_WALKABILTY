package com.fstm.coredumped.smartwalkabilty.web.Model.Service;

import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.AnnonceBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.IdsBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOAnnonce;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.LinkedList;

public class AnnouncesService {
    JWTgv jwTgv=new JWTgv();
    public String getServ(IdsBlob blob)
    {
        Gson s =new Gson();
        if(!jwTgv.verifyToken(blob.getToken()))return "{ \"mes\":\" invalid Token  \" }";
        if (blob.getId_annonce()!=0) {
            int id = blob.getId_annonce();
            Annonce annonce = DAOAnnonce.getDAOAnnonce().findById(id);
            return s.toJson(annonce);
        }
        else if(blob.getId_organisation()!=0)
        {
            int id = blob.getId_organisation();
            Collection<Annonce> annonces=DAOAnnonce.getDAOAnnonce().RetrieveOrganisationAnnonces(id);
            return s.toJson(annonces);
        }
        return "{ \"mes\":\" invalid info  \" }";
    }
    public String createAnnounce(AnnonceBlob blob){
        if(!jwTgv.verifyToken(blob.getToken()))return "{ \"mes\":\" invalid Token  \" }";
        if (blob.verifyInfos()) {
            Annonce annonce = new Annonce();
            try {
                blob.FillAnnonceFromBlob(annonce);
                if (DAOAnnonce.getDAOAnnonce().Create(annonce)) {
                    return "{ \"id\": " + annonce.getId() + "  }";//return id if everything is okay
                } else return  "{ \"mes\":\" invalid info \" }";
            } catch (Exception e) {
                System.err.println(e);
                return "{ \"mes\":\" invalid info \" }";

            }
        } else  return  "{ \"mes\":\" invalid info \" }";
    }
    public String updateAnnounce(AnnonceBlob blob){
        if(!jwTgv.verifyToken(blob.getToken()))return "{ \"mes\":\" invalid Token  \" }";
        if(!DAOAnnonce.getDAOAnnonce().checkExiste(blob.getId())){
            return "{ \"mes\":\" invalid : id doesn't exist \" }";
        }
        if (blob.verifyInfos()) {
            Annonce annonce = new Annonce();
            try {
                annonce.setId(blob.getId());
                blob.FillAnnonceFromBlob(annonce);
                DAOAnnonce.getDAOAnnonce().update(annonce);
                 return  "{ \"id\":\" " + annonce.getId() + " \" }";//return id if everything is okay
            } catch (Exception e) {
                System.err.println(e);
                 return  "{ \"mes\":\" invalid info \" }";
            }
        } else return  "{ \"mes\":\" invalid info \" }";
    }
    public String deleteAnnounce(IdsBlob blob)
    {
        if(!jwTgv.verifyToken(blob.getToken()))return "{ \"mes\":\" invalid Token  \" }";
        if(blob.getId_annonce()==0&&blob.getAnonnce_ids()==null)
        {
            return  "{ \"mes\":\" invalid info \" }";
        }
        else if(blob.getAnonnce_ids()!=null)
        {
            Collection<Annonce> annonces=new LinkedList<Annonce>();
            int num=blob.getAnonnce_ids().length;
            for (int id: blob.getAnonnce_ids()) {
                Annonce annonce=new Annonce();
                annonce.setId(id);
                annonces.add(annonce);
            }
            if(DAOAnnonce.getDAOAnnonce().deleteMultiple(annonces))
                return  "{ \"succeeded\":\" "+num+" announces were Deleted \" }";
            else  return  "{ \"mes\":\" invalid : Exception Happened it must be that some id is not in the db \" }";
        }
        else
        {
            Annonce annonce=new Annonce();
            annonce.setId(blob.getId_annonce());
            if(DAOAnnonce.getDAOAnnonce().delete(annonce))
                return  "{ \"succeeded\":\"Deleted\" }";
            else  return  "{ \"mes\":\" invalid : id doesn't exist \" }";
        }
    }
}
