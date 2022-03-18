package com.fstm.coredumped.smartwalkabilty.web.Model.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Organisation;
import com.fstm.coredumped.smartwalkabilty.web.Model.dao.DAOOrganisation;

import java.util.Date;

public class JWTgv {
    private String keystr = "X4IN8q1Mhv8FRa71KnbEaBJ4XEeCe3DTuxVUn8w5PadA3VxazC9rxOTHj1xfGde";

    private static JWTgv jwtinstance=null;

    public JWTgv() {

    }

    public static JWTgv getJWTgv() {
        if(jwtinstance == null)
            jwtinstance= new JWTgv();
        return jwtinstance;
    }

    public String generateToken(Organisation organisation) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(keystr);
            String token = JWT.create()
                    .withIssuer("coredumped")
                    .withIssuedAt(new Date())
                    .withClaim("id",Integer.toString(organisation.getId()))
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception){
            return null;
        }
    }

    public boolean verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(keystr);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception){
            return false;
        }
    }
}
