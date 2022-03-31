package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.*;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOSite;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class SiteController extends HttpServlet {
    public class IdsSiteBlob {
        String Token;
        int id_site=0;
        int[] site_ids;
        int id_organisation=0;
        public IdsSiteBlob(){}
    }
    public class SiteBlob{
        int id;
        String name,datecreated,Token;
        int id_organisation;
        GeoPoint geoLocation;
        boolean verifyInfos()
        {
            if(name==null ||datecreated==null ||id_organisation==0||geoLocation==null||Token==null)return false;
            return true;
        }
    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        Gson s = new Gson();
        IdsSiteBlob Blob=s.fromJson(request.getReader(), IdsSiteBlob.class);

        try {

            if(AnnonceController.verifyToken(Blob.Token)) {
                if (Blob.id_site != 0) {
                   Site site = DAOSite.getDaoSite().findById(Blob.id_site);
                   response.getWriter().println(s.toJson(site));
                }
                else if(Blob.id_organisation != 0)
                {
                    Collection<Site> site = DAOSite.getDaoSite().RetreveListSite(Blob.id_organisation);
                    response.getWriter().println(s.toJson(site));
                }
                else{
                    response.getWriter().println("{ \"mes\":\" invalid info  \" }");
                }
            }
            else{
                response.getWriter().println("{ \"mes\":\" invalid Token  \" }");
            }
        }catch (Exception e)
        {
            System.err.println(e);
            response.getWriter().println("{ \"mes\":\" invalid info \" }");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        Gson s = new Gson();
        SiteBlob Blob = s.fromJson(req.getReader(), SiteBlob.class);

        if( AnnonceController.verifyToken(Blob.Token)&& Blob.verifyInfos())
        {
            Site site=new Site();
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                site.setName(Blob.name);
                site.setDateCreated(dateFormat.parse(Blob.datecreated));
               // site.setOrganisation(Blob.id_organisation);
                Organisation organisation = new Organisation();
                organisation.setId(Blob.id_organisation);
                site.setOrganisation(organisation);
                site.setLocalisation(Blob.geoLocation);
                 if(DAOSite.getDaoSite().Create(site)){
                     resp.getWriter().println("{ \"id\":\" " + site.getId() + " \" }");
                 }else{
                     resp.getWriter().println("{ \"mes\":\" invalid info \" }");
                 }
            } catch (Exception e) {
                System.err.println(e);
                resp.getWriter().println("{ \"mes\":\" invalid info \" }");

            }
        }else resp.getWriter().println("{ \"mes\":\" invalid info \" }");

    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json");
        Gson s = new Gson();
        try {
            SiteBlob Blob = s.fromJson(req.getReader(), SiteBlob.class);
                if(!DAOSite.getDaoSite().existance(Blob.id)){
                    resp.getWriter().println("{ \"\":\" id doesn't exist \" }");
                    return;
                }
                if(Blob.verifyInfos()&& AnnonceController.verifyToken(Blob.Token))
            {
                Site site=new Site();
                try {
                    site.setId(Blob.id);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    site.setName(Blob.name);
                    site.setDateCreated(dateFormat.parse(Blob.datecreated));
                    // site.setOrganisation(Blob.id_organisation);
                    Organisation organisation = new Organisation();
                    organisation.setId(Blob.id_organisation);
                    site.setOrganisation(organisation);
                    site.setLocalisation(Blob.geoLocation);
                    DAOSite.getDaoSite().update(site);
                    resp.getWriter().println("{ \"id\":\" " + site.getId() + " \" }");



                }catch (Exception e) {
                    System.err.println(e);
                    resp.getWriter().println("{ \"mes\":\" invalid info \" }");
                }


        } else resp.getWriter().println("{ \"mes\":\" invalid info \" }");
        }catch (Exception e){
            System.err.println(e);
            resp.getWriter().println("{ \"mes\":\" invalid info \" }");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try {


        resp.setContentType("application/json");
        Gson s = new Gson();
        IdsSiteBlob Blob = s.fromJson(req.getReader(), IdsSiteBlob.class);
        if(!AnnonceController.verifyToken(Blob.Token) ||(Blob.id_site==0&&Blob.site_ids==null))
        {
            resp.getWriter().println("{ \"mes\":\" invalid info \" }");
        }
        else if(Blob.site_ids!=null)
        {
            Collection<Site> sites = new LinkedList<Site>();
            int num=Blob.site_ids.length;
            for (int id: Blob.site_ids) {
                Site site=new Site();
                site.setId(id);
                sites.add(site);
            }
            if(DAOSite.getDaoSite().deleteMultiple(sites))
                resp.getWriter().println("{ \"succeeded\":\" "+num+" announces were Deleted \" }");
            else resp.getWriter().println("{ \"mes\":\" Exception Happened it must be that some id is not in the db \" }");
        }

        else{


        Site site=new Site();
        site.setId(Blob.id_site);
        if(DAOSite.getDaoSite().delete(site))
            resp.getWriter().println("{ \"succeeded\":\"Deleted\" }");
        else resp.getWriter().println("{ \"mes\":\" doesn't exist \" }");


        }
        }
        catch (Exception e){
            System.err.println(e);
            resp.getWriter().println("{ \"mes\":\" Exception Happened \" }");
        }

    }
}
