package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.web.Model.Service.JWTgv;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Annonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Categorie;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Image;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Site;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOAnnonce;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOCategorie;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;

public class CategorieController extends HttpServlet
{
    public class IdsBlob {
         String Token;
         int id_cat=0;
         public IdsBlob(){}
    }
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
        response.setContentType("application/json");
        Gson s = new Gson();
        IdsBlob Blob=s.fromJson(request.getReader(),IdsBlob.class);
        if(new JWTgv().verifyToken(Blob.Token)) {
            if (Blob.id_cat!=0) {
                Categorie categorie= DAOCategorie.getDaoCategorie().getById(Blob.id_cat);
                response.getWriter().println(s.toJson(categorie));
            }
            else
            {
                response.getWriter().println(s.toJson(DAOCategorie.getDaoCategorie().Retrieve()));
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



}
