package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.web.Model.Service.CategoriesService;
import com.fstm.coredumped.smartwalkabilty.web.Model.Service.JWTgv;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Categorie;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.blobs.CatIdsBlob;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOCategorie;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CategorieController extends HttpServlet
{
    CategoriesService service=new CategoriesService();
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            response.setContentType("application/json");
            Gson s = new Gson();
            CatIdsBlob Blob=s.fromJson(request.getReader(),CatIdsBlob.class);
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



}
