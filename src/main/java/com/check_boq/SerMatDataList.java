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

    public ArrayList<MoMaterial> getMoMaterialArrayList(){
        getDataFromDatabase() ;
        return moMaterialArrayList ;
    }
}
