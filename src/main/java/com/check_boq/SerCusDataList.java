package com.check_boq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SerCusDataList implements SerDataList {

    private ArrayList<MoCustomer> customerArrayList;
    SerDatabaseConnection dbCon ;
    Connection connectionDb ;
    public SerCusDataList() {
        ArrayList<MoCustomer> tempCusArrayList = new ArrayList<>() ;
        dbCon = new SerDatabaseConnection() ;
        connectionDb = dbCon.getConnection();
        String query ="SELECT * FROM check_boq.customer" ;
        try{
            Statement statement = connectionDb.createStatement();
            ResultSet queryOutPut = statement.executeQuery(query);
            while (queryOutPut.next()) {
                tempCusArrayList.add(new MoCustomer(queryOutPut.getInt("CS_ID"),
                        queryOutPut.getString("CS_Name"),
                        queryOutPut.getString("CS_Phone"),
                        queryOutPut.getString("CS_Email"))) ;
            }
            customerArrayList = tempCusArrayList ;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getCustomerDatabase(){
        ArrayList<MoCustomer> tempCusArrayList = new ArrayList<>() ;
        dbCon = new SerDatabaseConnection() ;
        connectionDb = dbCon.getConnection();
        String query ="SELECT * FROM check_boq.customer" ;
        try{
            Statement statement = connectionDb.createStatement();
            ResultSet queryOutPut = statement.executeQuery(query);
            while (queryOutPut.next()) {
                tempCusArrayList.add(new MoCustomer(queryOutPut.getInt("CS_ID"),
                        queryOutPut.getString("CS_Name"),
                        queryOutPut.getString("CS_Phone"),
                        queryOutPut.getString("CS_Email"))) ;
            }
            customerArrayList = tempCusArrayList ;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addCustomer(MoCustomer customer){
        String query = "INSERT INTO check_boq.customer (CS_Name, CS_Phone, CS_Email) VALUES(" ;
        query+= "\"" ;
        query+=customer.getCS_Name()+ "\"" +"," ;
        query+= "\"" ;
        query+=customer.getCS_Phone()+ "\"" +"," ;
        query+= "\"" ;
        query+=customer.getCS_Email() + "\"" + ")" ;
//        System.out.println(query);
        try{
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query) ;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updateCusToDatabase(MoCustomer newCus, MoCustomer oldCus){
        String query = "UPDATE check_boq.customer SET " ;
        query+= "CS_Name = " + "\"" + newCus.getCS_Name() + "\"" + "," ;
        query+= "CS_Phone = " + "\"" + newCus.getCS_Phone() + "\"" + "," ;
        query+= "CS_Email = "+ "\"" + newCus.getCS_Email() + "\"" ;
        query+= " WHERE " + "CS_ID = " + oldCus.getCS_ID() ;
        try {
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> returnNameArraylist(){
        ArrayList<String> name = new ArrayList<>() ;
        getCustomerDatabase() ;
        for(MoCustomer s:customerArrayList){
            name.add(s.getCS_Name()) ;
        }
        return name ;
    }

    public int searchIDByName(String name){
        getCustomerDatabase() ;
        for(MoCustomer s:customerArrayList){
            if(s.getCS_Name().equals(name)){
                return s.getCS_ID() ;
            }
        }
        return 0 ;
    }

    public void deleteCustomer(int id){
        String query = "DELETE FROM check_boq.customer WHERE CS_ID =" + id ;
        try {
            Statement statement = connectionDb.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<MoCustomer> getCustomerArrayList(){
        return customerArrayList;
    }
}
