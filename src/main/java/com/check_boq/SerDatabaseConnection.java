package com.check_boq;

import java.sql.Connection;
import java.sql.DriverManager;

public class SerDatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName = "check_boq";
        String databaseUser = "root";
        String databasePass = "_JL=z+jV7x%Fu+aHYp$";
        String url = "jdbc:mysql://localhost/" +databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePass);

        }catch (Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
