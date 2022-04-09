package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.web.Model.Service.AnnouncesService;
import com.fstm.coredumped.smartwalkabilty.web.Model.Service.JWTgv;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.AnnonceBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.IdsBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOAnnonce;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;


import static java.util.stream.Collectors.joining;

public class AnnonceController extends HttpServlet
{
    AnnouncesService servise=new AnnouncesService();
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        Gson s = new Gson();
        try {
            IdsBlob Blob=s.fromJson(request.getReader(),IdsBlob.class);
            String s1= servise.getServ(Blob);
            if(s1.contains("invalid"))response.setStatus(401);
            response.getWriter().println(s1);
        }catch (Exception e)
        {
            System.err.println(e);
            response.setStatus(400);
            response.getWriter().println("{ \"mes\":\"  Exception Happened \" }");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson s = new Gson();
        try {
            AnnonceBlob blob = s.fromJson(req.getReader(), AnnonceBlob.class);
            String s1=servise.createAnnounce(blob);
            if(s1.contains("invalid"))resp.setStatus(400);
            resp.getWriter().println(s1);
        }catch (Exception e){
            System.err.println(e);
            resp.setStatus(402);
            resp.getWriter().println("{ \"mes\":\"  Exception Happened \" }");
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json");
        Gson s = new Gson();
        try {
            AnnonceBlob Blob = s.fromJson(req.getReader(), AnnonceBlob.class);
            String s1= servise.updateAnnounce(Blob);
            if(s1.contains("invalid"))resp.setStatus(400);
            resp.getWriter().println(s1);
        }catch (Exception e){
            System.err.println(e);
            resp.setStatus(400);
            resp.getWriter().println("{ \"mes\":\"  Exception Happened \" }");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        try {
            resp.setContentType("application/json");
            Gson s = new Gson();
            IdsBlob Blob = s.fromJson(req.getReader(), IdsBlob.class);
            String s1= servise.deleteAnnounce(Blob);
            if(s1.contains("invalid"))resp.setStatus(400);
            resp.getWriter().println(s1);
        }catch (Exception e){
            System.err.println(e);
            resp.setStatus(400);
            resp.getWriter().println("{ \"mes\":\" Exception Happened \" }");
        }
    }

}

