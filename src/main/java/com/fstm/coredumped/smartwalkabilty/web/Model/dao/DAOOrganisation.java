package com.fstm.coredumped.smartwalkabilty.web.Model.dao;

import com.fstm.coredumped.smartwalkabilty.web.Model.Service.MD5Hash;
import com.fstm.coredumped.smartwalkabilty.web.Model.bo.Organisation;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DAOOrganisation implements IDAO<Organisation> {

    private static DAOOrganisation daoo=null;

    public DAOOrganisation() {

    }

    public static DAOOrganisation getDaoOrganisation() {
        if(daoo == null)
            daoo= new DAOOrganisation();
        return daoo;
    }

    @Override
    public boolean Create(Organisation organisation) {
        try {
            PreparedStatement pr=Connexion.getCon().prepareStatement(
                    "insert into organizations(datecreated,name,login,passHash,email,type) values(?,?,?,?,?,?) "
            );
            pr.setDate(1,new java.sql.Date(organisation.getDateCreation().getTime()));
            pr.setString(2,organisation.getNom());
            pr.setString(3,organisation.getLogin());
            pr.setString(4, MD5Hash.MD5Hash(organisation.getPassword()));
            pr.setString(5,organisation.getEmail());
            pr.setInt(6,organisation.getType());

            pr.executeUpdate();
            return true;
        }
        catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }

    @Override
    public Collection<Organisation> Retrieve() {
        List<Organisation> organisations=new LinkedList<Organisation>();
        try {
            PreparedStatement pr=Connexion.getCon().prepareStatement("select * from organizations");
            ResultSet resultSet=pr.executeQuery();
            while (resultSet.next())
            {
                Organisation organisation=extractOrganisation(resultSet);
                organisations.add(organisation);
            }
            return organisations;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void update(Organisation organisation) {
        try {
            PreparedStatement pr=Connexion.getCon().prepareStatement(
                    "Update organizations SET login=?,passHash=?,email=?,name=?,datecreated=? where id=? "
            );
            pr.setString(1,organisation.getLogin());
            pr.setString(2,organisation.getPassword());
            pr.setString(3,organisation.getEmail());
            pr.setString(4,organisation.getEmail());
            pr.setDate(5, (Date) organisation.getDateCreation());
            pr.setInt(6,organisation.getId());
            pr.executeUpdate();
        }catch (SQLException e){
            System.err.println(e);
        }
    }

    @Override
    public boolean delete(Organisation organisation) {
        try {
            PreparedStatement pr=Connexion.getCon().prepareStatement("Delete FROM organizations where id=?");
            pr.setInt(1,organisation.getId());
            pr.executeUpdate();
            return true;
        }catch (SQLException e){
            System.err.println(e);
            return false;
        }
    }


    private Organisation extractOrganisation(ResultSet resultSet) throws SQLException {
        Organisation organisation=new Organisation();
        organisation.setId(resultSet.getInt("id"));
        organisation.setDateCreation(resultSet.getDate("datecreated"));
        organisation.setLogin(resultSet.getString("login"));
        organisation.setNom(resultSet.getString("name"));
        organisation.setPassword(resultSet.getString("passHash"));
        organisation.setEmail(resultSet.getString("email"));
        organisation.setType(resultSet.getInt("type"));
        organisation.setListe_sites(null);
        return organisation;
    }


    public Organisation findOrganisationById(int id){
        try {
            Connexion.getCon().setAutoCommit(false);
            PreparedStatement preparedStatement= Connexion.getCon().prepareStatement(
                    "SELECT * FROM organizations WHERE id=?");
            preparedStatement.setInt(1,id);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()){
                Organisation organisation = extractOrganisation(set);
                organisation.setLogin(null);
                organisation.setPassword(null);
                return organisation;
            }else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean isLoginExist(String login){
        try {
            PreparedStatement preparedStatement= Connexion.getCon().prepareStatement(
                    "SELECT * FROM organizations WHERE login=? ");
            preparedStatement.setString(1,login);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next())
                return true;
            else
                return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean isEmailExist(String email){
        try {
            PreparedStatement preparedStatement= Connexion.getCon().prepareStatement(
                    "SELECT * FROM organizations WHERE email=? ");
            preparedStatement.setString(1,email);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next())
                return true;
            else
                return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public Organisation authentification(String login, String HashedPassword){
        try {
            PreparedStatement preparedStatement= Connexion.getCon().prepareStatement(
                    "SELECT * FROM organizations WHERE login=? AND passhash=? ");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2, HashedPassword);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()){
                Organisation organisation = extractOrganisation(set);
                organisation.setLogin(login);
                organisation.setPassword(HashedPassword);
                return organisation;
            }
            else
                return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
