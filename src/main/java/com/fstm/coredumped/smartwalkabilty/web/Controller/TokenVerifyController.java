package com.fstm.coredumped.smartwalkabilty.web.Controller;

import com.fstm.coredumped.smartwalkabilty.web.Model.Service.JWTgv;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenVerifyController extends HttpServlet {
    public class err{
        String error="error";
        err(){}
    }
    public class TokenToRecieve{
        String token;
        TokenToRecieve(){

        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        TokenToRecieve token = gson.fromJson(req.getReader(),TokenToRecieve.class);
        if (new JWTgv().verifyToken(token.token)){
            resp.getWriter().println("SUCESS");
        }
        else {
            resp.getWriter().println("FAIL");
        }
    }
}
