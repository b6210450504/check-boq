package com.check_boq;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TorTable {
    @FXML
    TableView torTable ;

    SerTorDataList serTorDataList ;



    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                serTorDataList = new SerTorDataList() ;
                showTable();
            }
        });
    }

    public void showTable(){
        ObservableList<MoTOR> moTORS = FXCollections.observableList(serTorDataList.getTorArrayList()) ;
        TableColumn groupIDCol = new TableColumn("GroupID");
        TableColumn projNameCol = new TableColumn("Project Name");
        TableColumn matCol = new TableColumn("Material");
        TableColumn memberCol = new TableColumn("Member") ;
        TableColumn periodCol = new TableColumn("Period") ;
        groupIDCol.setCellValueFactory(new PropertyValueFactory<MoTOR,String>("TO_GroupID"));
        projNameCol.setCellValueFactory(new PropertyValueFactory<MoTOR,String>("TO_Name"));
        matCol.setCellValueFactory(new PropertyValueFactory<MoTOR,String>("TO_Materials"));
        memberCol.setCellValueFactory(new PropertyValueFactory<MoTOR, String>("TO_Member"));
        periodCol.setCellValueFactory(new PropertyValueFactory<MoTOR,Integer>("TO_Period"));
        torTable.getColumns().addAll(groupIDCol,projNameCol,matCol,memberCol,periodCol) ;
        torTable.setItems(moTORS);
    }
}
