package com.check_boq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SerMatDataList {
    private ArrayList<MoMaterial> moMaterialArrayList;
    SerDatabaseConnection dbCon ;
    Connection connectionDb ;

    public SerMatDataList() {
        dbCon = new SerDatabaseConnection() ;
        connectionDb = dbCon.getConnection();
        getDataFromDatabase() ;
    }

    public void getDataFromDatabase(){
        ArrayList<MoMaterial> tempMoList = new ArrayList<>() ;
        String query ="SELECT * FROM check_boq.materials" ;
        try{
            Statement statement = connectionDb.createStatement();
            ResultSet queryOutPut = statement.executeQuery(query);
            while (queryOutPut.next()) {
                tempMoList.add(new MoMaterial(queryOutPut.getInt("Mat_ID"),
                        queryOutPut.getString("Mat_Name"),
                        queryOutPut.getInt("Mat_Price")));
            }
            moMaterialArrayList = tempMoList ;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertToDatabase(MoMaterial insert){
        String query ="INSERT INTO check_boq.materials (Mat_Name, Mat_Price) VALUES(" ;
        query+= "\"" + insert.getMat_Name() + "\"" + "," + insert.getMat_Price() + ")" ;
        try{
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query) ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delMatDataBase(int id){
        String query = "DELETE FROM check_boq.materials WHERE Mat_ID = " + id ;
        try{
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query) ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateMatDataBase(MoMaterial moMaterial){
        String query = "UPDATE check_boq.materials SET " ;
        query+= "Mat_Price = " + "\"" + moMaterial.getMat_Price() + "\"";
        query+= " WHERE " + "Mat_ID = " + showMatID(moMaterial.getMat_Name()) ;
        try{
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MoMaterial getMatInfoByName(String name){
        for (MoMaterial n: moMaterialArrayList) {
            if(n.getMat_Name().equals(name)){
                return n ;
            }
        }
        return null ;
    }

    public boolean checkMat(String mat){
        for (MoMaterial moMaterial : moMaterialArrayList){
            if (moMaterial.getMat_Name().equalsIgnoreCase(mat))
                return true;
        }
        return false;
    }

    public void editMat(String mat, int unit){
        for (MoMaterial moMaterial : moMaterialArrayList){
            if (moMaterial.getMat_Name().equalsIgnoreCase(mat)){
                moMaterial.setMat_Price(unit);
            }
        }
    }

    public int showMatID(String mat){
        for (MoMaterial moMaterial : moMaterialArrayList){
            if (moMaterial.getMat_Name().equalsIgnoreCase(mat)){
                return moMaterial.getMat_ID();
            }
        }
        return 0;
    }

    public ArrayList<MoMaterial> getMoMaterialArrayList(){
        getDataFromDatabase() ;
        return moMaterialArrayList ;
    }
}
