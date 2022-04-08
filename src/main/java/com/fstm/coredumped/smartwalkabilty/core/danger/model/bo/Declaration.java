package com.fstm.coredumped.smartwalkabilty.core.danger.model.bo;

import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Vertex;

import java.io.Serializable;
import java.util.Date;

public class Declaration implements Serializable
{
    private int id;
    private Danger danger;
    private Vertex vertex;
    private Date dateDeclared;

    public Declaration(Danger danger, Vertex vertex, Date dateDeclared) {
        this.danger = danger;
        this.vertex = vertex;
        this.dateDeclared = dateDeclared;
    }

    public Declaration(int id, Danger danger, Vertex vertex, Date dateDeclared) {
        this.id = id;
        this.danger = danger;
        this.vertex = vertex;
        this.dateDeclared = dateDeclared;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Danger getDanger() {
        return danger;
    }

    public void setDanger(Danger danger) {
        this.danger = danger;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public Date getDateDeclared() {
        return dateDeclared;
    }

    public void setDateDeclared(Date dateDeclared) {
        this.dateDeclared = dateDeclared;
    }
}
