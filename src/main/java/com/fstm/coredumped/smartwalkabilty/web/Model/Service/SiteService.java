package com.fstm.coredumped.smartwalkabilty.web.Model.Service;

import com.fstm.coredumped.smartwalkabilty.web.Controller.AnnonceController;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Organisation;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.IdsSiteBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.SiteBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOSite;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;

public class SiteService
{
    JWTgv jwTgv=new JWTgv();
    public String getServ(IdsSiteBlob blob){
        Gson s=new Gson();
        if(jwTgv.verifyToken(blob.getToken())) {
            if (blob.getId_site() != 0) {
                Site site = DAOSite.getDaoSite().findById(blob.getId_site());
                 return s.toJson(site);
            }
            else if(blob.getId_organisation() != 0)
            {
                Collection<Site> site = DAOSite.getDaoSite().RetreveListSite(blob.getId_organisation());
                return s.toJson(site);
            }
            else{
                return  "{ \"mes\":\" invalid info  \" }";
            }
        }
        else{
            return  "{ \"mes\":\" invalid Token  \" }";
        }
    }
    public String createSite(SiteBlob blob) throws ParseException {
        if(!jwTgv.verifyToken(blob.getToken()))return "{ \"mes\":\" invalid Token  \" }";
        if(blob.verifyInfos())
        {
            Site site=new Site();
            blob.FillSiteFromBlob(site);
            if(DAOSite.getDaoSite().Create(site)){
                return  "{ \"id\":\" " + site.getId() + " \" }";
            }else{
               return  "{ \"mes\":\" invalid info \" }";
            }
        }else return  "{ \"mes\":\" invalid info \" }";
    }
    public  String  updateSite(SiteBlob blob){
        if(!jwTgv.verifyToken(blob.getToken()))
            return "{ \"mes\":\" invalid Token  \" }";
        if(!DAOSite.getDaoSite().existance(blob.getId())){
            return  "{ \"\":\" id doesn't exist \" }";
        }
        if(blob.verifyInfos())
        {
            Site site=new Site();
            try {
                site.setId(blob.getId());
                blob.FillSiteFromBlob(site);
                DAOSite.getDaoSite().update(site);
                 return  "{ \"id\":\" " + site.getId() + " \" }";
            }catch (Exception e) {
                System.err.println(e.toString());
               return "{ \"mes\":\" invalid info \" }";
            }
        } else return  "{ \"mes\":\" invalid info \" }";
    }
    public String deleteSite(IdsSiteBlob blob){
        if(!jwTgv.verifyToken(blob.getToken()))
            return "{ \"mes\":\" invalid Token  \" }";
        if(blob.getId_site()==0&&blob.getSite_ids()==null)
        {
            return  "{ \"mes\":\" invalid info \" }";
        }
        if(blob.getSite_ids()!=null)
        {
            Collection<Site> sites = new LinkedList<Site>();
            int num=blob.getSite_ids().length;
            for (int id: blob.getSite_ids()) {
                Site site=new Site();
                site.setId(id);
                sites.add(site);
            }
            if(DAOSite.getDaoSite().deleteMultiple(sites))
                return "{ \"succeeded\":\" "+num+" sites were Deleted \" }";
            else return "{ \"mes\":\" Exception Happened it must be that some id is not in the db \" }";
        }
        else{
            Site site=new Site();
            site.setId(blob.getId_site());
            if(DAOSite.getDaoSite().delete(site))
                return "{ \"succeeded\":\"Deleted\" }";
            else return "{ \"mes\":\" invalid : doesn't exist \" }";
        }
    }
}
