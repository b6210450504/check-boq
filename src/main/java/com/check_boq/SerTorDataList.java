package com.check_boq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SerTorDataList {
    private ArrayList<MoTOR> torArrayList;
    SerDatabaseConnection dbCon ;
    Connection connectionDb ;

    public SerTorDataList() {
        torArrayList = new ArrayList<>() ;
        dbCon = new SerDatabaseConnection() ;
        connectionDb = dbCon.getConnection();
        ArrayList<MoTOR> tempTorArrayList = new ArrayList<>() ;
        String query ="SELECT * FROM check_boq.tor" ;
        try{
            Statement statement = connectionDb.createStatement();
            ResultSet queryOutPut = statement.executeQuery(query);
            while (queryOutPut.next()) {
                tempTorArrayList.add(new MoTOR(queryOutPut.getString("TO_Name"),
                        queryOutPut.getString("TO_Materials"),
                        queryOutPut.getString("TO_Member"),
                        queryOutPut.getInt("TO_Period"),
                        String.valueOf(queryOutPut.getInt("TO_GroupID")))) ;
            }
            torArrayList = tempTorArrayList ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insetTOR(MoTOR add){
        String query = "INSERT INTO check_boq.tor VALUES(" + add.DB() +")" ;
        try{
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delTorDataBase(int id){
        String query = "DELETE FROM check_boq.tor WHERE TO_GroupID = " + id ;
        try{
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkTor(String tor){
        for (MoTOR moTOR : torArrayList){
            if (moTOR.getTO_GroupID().equalsIgnoreCase(tor))
                return true;
        }
        return false;
    }

    public void getDataTorFromDataBase(){
        ArrayList<MoTOR> tempTorArrayList = new ArrayList<>() ;
        String query ="SELECT * FROM check_boq.tor" ;
        try{
            Statement statement = connectionDb.createStatement();
            ResultSet queryOutPut = statement.executeQuery(query);
            while (queryOutPut.next()) {
                tempTorArrayList.add(new MoTOR(queryOutPut.getString("TO_Name"),
                        queryOutPut.getString("TO_Materials"),
                        queryOutPut.getString("TO_Member"),
                        queryOutPut.getInt("TO_Period"),
                        String.valueOf(queryOutPut.getInt("TO_GroupID")))) ;
            }
            torArrayList = tempTorArrayList ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MoTOR getTorByProjName(String name){
        for (MoTOR t:torArrayList) {
            if(t.getTO_Name().equals(name)){
                return  t ;
            }
        }
        return null ;
    }

    public ArrayList<MoTOR> getTorArrayList(){
        getDataTorFromDataBase() ;
        return torArrayList ;
    }
}
