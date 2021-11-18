package com.check_boq;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class TorTable {
    @FXML
    TableView<MoTOR> torTable ;
    @FXML
    Button delButton;
    @FXML
    Label labelErr ;

    private SerTorDataList serTorDataList ;
    private MoTOR selectedTor ;

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                serTorDataList = new SerTorDataList() ;
                showTable();
                torTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        selectTor(newValue) ;
                    }
                });
            }
        });
    }

    public void selectTor(MoTOR select){
        selectedTor = select ;
        labelErr.setText("");
    }

    public void showTable(){
        ObservableList<MoTOR> moTORS = FXCollections.observableList(serTorDataList.getTorArrayList()) ;
        torTable.getColumns().clear();
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
        torTable.getColumns().addAll(groupIDCol, projNameCol, matCol, memberCol, periodCol) ;
        torTable.setItems(moTORS);
    }

    public void onClicked(){
        torTable.getSelectionModel().clearSelection();
        labelErr.setText("");
    }

    public void eventDel(){
        if(!torTable.getSelectionModel().isEmpty()){
            serTorDataList.delTorDataBase(Integer.parseInt(selectedTor.getTO_GroupID())) ;
            torTable.getSelectionModel().clearSelection();
            showTable();
            labelErr.setTextFill(Color.GREEN);
            labelErr.setText("Delete Complete.");
        }
        else{
            labelErr.setTextFill(Color.RED);
            labelErr.setText("Please Select Material.");
        }
    }
}
