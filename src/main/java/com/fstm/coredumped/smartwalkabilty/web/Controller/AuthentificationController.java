package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.web.Model.Service.JWTgv;
import com.fstm.coredumped.smartwalkabilty.web.Model.Service.MD5Hash;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Organisation;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOOrganisation;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthentificationController extends HttpServlet {
    public class LoginBlob {
        String login;
        String password;
        LoginBlob(){}
    }
    public class err{
        String error;
        err(String errMessage){
            this.error = errMessage;
        }
    }
    public class Response{
        String token;
        OrganisationBlob organisation;
        Response(String t, OrganisationBlob org){
            this.organisation = org;
            this.token = t;
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
        super.service(req, resp);
    }

    public class OrganisationBlob{
        int id;
        String name;
        String datecreated;
        String login;
        String email;

        public OrganisationBlob(int id, String name, String datecreated, String login, String email) {
            this.id = id;
            this.name = name;
            this.datecreated = datecreated;
            this.login = login;
            this.email = email;
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        Gson gson = new Gson();
        LoginBlob blob = gson.fromJson(req.getReader(), LoginBlob.class);
        String generatedPassword = MD5Hash.MD5Hash(blob.password);
        Organisation organisation = new DAOOrganisation().authentification(blob.login,generatedPassword);
        if (organisation != null) {
            String token = new JWTgv().generateToken(organisation);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            resp.getWriter().println(gson.toJson(new Response( token , new OrganisationBlob(
                    organisation.getId(),
                    organisation.getNom(),
                    formatter.format(organisation.getDateCreation()),
                    organisation.getLogin(),
                    organisation.getEmail()
            ))));
        }
        else {
            resp.getWriter().println(gson.toJson(new err("Authentication Error : no account found with the provided login/password")));
        }
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Max-Age", "10");
    }
}
