package com.check_boq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SerBoqDataList {
    private ArrayList<MoBOQ> boqArrayList;
    SerDatabaseConnection dbCon ;
    Connection connectionDb ;

    public SerBoqDataList() {
        boqArrayList = new ArrayList<>() ;
        dbCon = new SerDatabaseConnection() ;
        connectionDb = dbCon.getConnection();
        ArrayList<MoBOQ> temp = new ArrayList<>() ;
        String query ="SELECT * FROM check_boq.boq" ;
        try{
            Statement statement = connectionDb.createStatement();
            ResultSet queryOutPut = statement.executeQuery(query);
//            MoBOQ(int BO_GroupID, int BO_ID, String BO_ProjName, String BO_Member, String BO_Materials, long BO_Price, int BO_Period)
            while (queryOutPut.next()) {
                temp.add(new MoBOQ(queryOutPut.getInt("BO_GroupID"),
                        queryOutPut.getInt("BO_ID"),
                        queryOutPut.getString("BO_Name"),
                        queryOutPut.getString("BO_Member"),
                        queryOutPut.getString("BO_Materials"),
                        queryOutPut.getLong("BO_Price"),
                        queryOutPut.getInt("BO_Period")
                        )) ;
            }
            boqArrayList = temp ;
//            System.out.println(boqArrayList.toString());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromDataBase(){
        ArrayList<MoBOQ> temp = new ArrayList<>() ;
        String query ="SELECT * FROM check_boq.boq" ;
        try{
            Statement statement = connectionDb.createStatement();
            ResultSet queryOutPut = statement.executeQuery(query);
//            MoBOQ(int BO_GroupID, int BO_ID, String BO_ProjName, String BO_Member, String BO_Materials, long BO_Price, int BO_Period)
            while (queryOutPut.next()) {
                temp.add(new MoBOQ(queryOutPut.getInt("BO_GroupID"),
                        queryOutPut.getInt("BO_ID"),
                        queryOutPut.getString("BO_Name"),
                        queryOutPut.getString("BO_Member"),
                        queryOutPut.getString("BO_Materials"),
                        queryOutPut.getLong("BO_Price"),
                        queryOutPut.getInt("BO_Period")
                )) ;
            }
            boqArrayList = temp ;
//            System.out.println(boqArrayList.toString());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MoBOQ> getBoqArrayList(){
        getDataFromDataBase();
        return boqArrayList ;
    }

    public void addBOQToDatabase(MoBOQ toAdd){
        String query = "INSERT INTO check_boq.boq (BO_GroupID, BO_Name, BO_Member, BO_Materials, BO_Price, BO_Period) VALUES(" ;
        query += toAdd.getBO_GroupID() + "," ;
        query += "\"" + toAdd.getBO_ProjName() + "\"" + "," ;
        query += "\"" + toAdd.getBO_Member() + "\"" + "," ;
        query += "\"" + toAdd.getBO_Materials() + "\"" + "," ;
        query += toAdd.getBO_Amount() + "," ;
        query += toAdd.getBO_Period() + ")" ;
        try{
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delBoqOnDatabase(int id){
//        DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
        String query = "DELETE FROM check_boq.boq WHERE BO_ID = " + id ;
        try{
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
