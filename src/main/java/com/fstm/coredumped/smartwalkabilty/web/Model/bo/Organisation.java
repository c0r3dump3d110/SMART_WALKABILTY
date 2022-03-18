package com.fstm.coredumped.smartwalkabilty.web.Model.bo;

import java.util.Date;
import java.util.Vector;

public class Organisation {

    public static String types[] = {"Entreprise","Organisation Non Commerciale"};
    // Attributs

    private int id;
    private String nom;
    private Date dateCreation;
    private String login;
    private String password;
    private String email;
    private int type;
    private Vector<Site> liste_sites = new Vector<Site>();

    // Constructors

    public Organisation(int id) {
        this.id = id;
    }
    public Organisation() {}

    // Getters

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Vector<Site> getListe_sites() {
        return liste_sites;
    }

    public int getType() {
        return type;
    }

    // Setters


    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setListe_sites(Vector<Site> liste_sites) {
        this.liste_sites = liste_sites;
    }

    public void setType(int type) {
        this.type = type;
    }
}
