package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.web.Model.Service.SiteService;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.*;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.IdsSiteBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.SiteBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOSite;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;

import static java.util.stream.Collectors.joining;

public class SiteController extends HttpServlet {

    SiteService service=new SiteService();
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        Gson s = new Gson();
        try {
            IdsSiteBlob Blob=s.fromJson(request.getReader(), IdsSiteBlob.class);
            String s1=service.getServ(Blob);
            if(s1.contains("invalid"))response.setStatus(400);
            response.getWriter().println(s1);
        }
        catch (Exception e)
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
            SiteBlob Blob = s.fromJson(req.getReader(), SiteBlob.class);
            String s1=service.createSite(Blob);
            if(s1.contains("invalid"))resp.setStatus(400);
            resp.getWriter().println(s1);
        } catch (Exception e) {
            System.err.println(e);
            resp.setStatus(400);
            resp.getWriter().println("{ \"mes\":\"  Exception Happened \" }");
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("application/json");
        Gson s = new Gson();
        try {
            SiteBlob Blob = s.fromJson(req.getReader(), SiteBlob.class);
            String s1=service.updateSite(Blob);
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
            IdsSiteBlob Blob = s.fromJson(req.getReader(), IdsSiteBlob.class);
            String s1=service.deleteSite(Blob);
            if(s1.contains("invalid"))resp.setStatus(400);
            resp.getWriter().println(s1);
        }
        catch (Exception e){
            System.err.println(e);
            resp.setStatus(400);
            resp.getWriter().println("{ \"mes\":\"  Exception Happened \" }");
        }

    }
}
