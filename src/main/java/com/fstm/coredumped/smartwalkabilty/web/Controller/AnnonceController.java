package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.web.Model.Service.JWTgv;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Image;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOAnnonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOCategorie;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import static java.util.stream.Collectors.joining;

public class AnnonceController extends HttpServlet
{
    public class IdsBlob {
         String Token;
         int id_annonce=0;
         int id_organisation=0;
         int[] anonnce_ids;
         public IdsBlob(){}
    }

    public class AnnonceBlob{
        int id;
        String dateDebut,dateFin,Titre,Description,url,Token;
        int[] sites;
        String[] url_optionnel;
        int id_cat;
        boolean verifyInfos()
        {
            if(dateDebut==null ||dateFin==null ||Titre==null||Description==null||url==null||Token==null||sites==null)return false;
            if(sites.length==0)return false;
            return true;
        }
    }
    public static boolean verifyToken(String Token)
    {
        return new JWTgv().verifyToken(Token);
    }


    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
        response.setContentType("application/json");
        Gson s = new Gson();
        IdsBlob Blob=s.fromJson(request.getReader(),IdsBlob.class);
        if(verifyToken(Blob.Token)) {
            if (Blob.id_annonce!=0) {
                int id = Blob.id_annonce;
                Annonce annonce = DAOAnnonce.getDAOAnnonce().findById(id);
                response.getWriter().println(s.toJson(annonce));
            }
            else if(Blob.id_organisation!=0)
            {
                int id = Blob.id_organisation;
                Collection<Annonce> annonces=DAOAnnonce.getDAOAnnonce().RetrieveOrganisationAnnonces(id);
                response.getWriter().println(s.toJson(annonces));
            }
        }else {
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
        try {
            AnnonceBlob Blob = s.fromJson(req.getReader(), AnnonceBlob.class);
            if (Blob.verifyInfos() && verifyToken(Blob.Token)) {
                Annonce annonce = new Annonce();
                try {
                    FillAnnonceFromBlob(Blob, annonce);
                    if (DAOAnnonce.getDAOAnnonce().Create(annonce)) {
                        resp.getWriter().println("{ \"id\": " + annonce.getId() + "  }");//return id if everything is okay
                    } else resp.getWriter().println("{ \"mes\":\" invalid info \" }");
                } catch (Exception e) {
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json");
        Gson s = new Gson();
        try {
            AnnonceBlob Blob = s.fromJson(req.getReader(), AnnonceBlob.class);
            if(!DAOAnnonce.getDAOAnnonce().checkExiste(Blob.id)){
                resp.getWriter().println("{ \"mes\":\" id doesn't exist \" }");
                return;
            }
            if (Blob.verifyInfos() && verifyToken(Blob.Token)) {
                Annonce annonce = new Annonce();
                try {
                    annonce.setId(Blob.id);
                    FillAnnonceFromBlob(Blob, annonce);
                    DAOAnnonce.getDAOAnnonce().update(annonce);
                    resp.getWriter().println("{ \"id\":\" " + annonce.getId() + " \" }");//return id if everything is okay
                } catch (Exception e) {
                    System.err.println(e);
                    resp.getWriter().println("{ \"mes\":\" invalid info \" }");
                }
            } else resp.getWriter().println("{ \"mes\":\" invalid info \" }");
        }catch (Exception e){
            System.err.println(e);
            resp.getWriter().println("{ \"mes\":\" invalid info \" }");
        }
    }

    private void FillAnnonceFromBlob(AnnonceBlob blob, Annonce annonce) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        annonce.setDateDebut(dateFormat.parse(blob.dateDebut));
        annonce.setDateFin(dateFormat.parse(blob.dateFin));
        annonce.setTitre(blob.Titre);
        annonce.setDescription(blob.Description);
        annonce.setUrlPrincipalImage(blob.url);
        annonce.setCategorie(DAOCategorie.getDaoCategorie().getById(blob.id_cat));
        for (int st : blob.sites) {
            Site site = new Site();
            site.setId(st);
            annonce.AddSite(site);
        }
        String[] urlop = blob.url_optionnel;
        if (urlop != null)
            for (String url : urlop) {
                annonce.AddImage(new Image(url, annonce));
            }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try {
            resp.setContentType("application/json");
            Gson s = new Gson();
            IdsBlob Blob = s.fromJson(req.getReader(), IdsBlob.class);
            if(!verifyToken(Blob.Token) ||(Blob.id_annonce==0&&Blob.anonnce_ids==null))
            {
                resp.getWriter().println("{ \"mes\":\" invalid info \" }");
            }
            else if(Blob.anonnce_ids!=null)
            {
                Collection<Annonce> annonces=new LinkedList<Annonce>();
                int num=Blob.anonnce_ids.length;
                for (int id: Blob.anonnce_ids) {
                    Annonce annonce=new Annonce();
                    annonce.setId(id);
                    annonces.add(annonce);
                }
                if(DAOAnnonce.getDAOAnnonce().deleteMultiple(annonces))
                resp.getWriter().println("{ \"succeeded\":\" "+num+" announces were Deleted \" }");
                else resp.getWriter().println("{ \"mes\":\" Exception Happened it must be that some id is not in the db \" }");
            }
            else
            {
                Annonce annonce=new Annonce();
                annonce.setId(Blob.id_annonce);
                if(DAOAnnonce.getDAOAnnonce().delete(annonce))
                resp.getWriter().println("{ \"succeeded\":\"Deleted\" }");
                else resp.getWriter().println("{ \"mes\":\" doesn't exist \" }");
            }
        }catch (Exception e){
            System.err.println(e);
            resp.getWriter().println("{ \"mes\":\" Exception Happened \" }");
        }
    }

}
