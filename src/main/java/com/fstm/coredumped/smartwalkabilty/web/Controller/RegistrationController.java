package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Organisation;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOOrganisation;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistrationController extends HttpServlet {
    class Fiche{
        String name;
        String email;
        String login;
        String password;
        String creationDate;
        String type;
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = new Gson();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Fiche fiche = gson.fromJson(req.getReader(),Fiche.class);
        boolean emailE = DAOOrganisation.getDaoOrganisation().isEmailExist(fiche.email);
        boolean loginE = DAOOrganisation.getDaoOrganisation().isLoginExist(fiche.login);
        if (!emailE && !loginE) {
            Organisation organisation = new Organisation();
            organisation.setNom(fiche.name);
            organisation.setEmail(fiche.email);
            organisation.setLogin(fiche.login);
            organisation.setPassword(fiche.password);
            try {
                organisation.setDateCreation(formatter.parse(fiche.creationDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            organisation.setType(Integer.parseInt(fiche.type));
            if (DAOOrganisation.getDaoOrganisation().Create(organisation))
                resp.getWriter().println("{\"success\":\"Organization added\"}");
            else{
                resp.setStatus(400);
                resp.getWriter().println("{\"error\":\"Organization not inserted\"}");
            }

        }
        else {
            resp.setStatus(400);
            if (emailE)
                resp.getWriter().println("{\"error\":\"Email address already used\"}");
            else
                resp.getWriter().println("{\"error\":\"Login already used\"}");
        }
    }
}
