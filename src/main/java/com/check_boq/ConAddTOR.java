package com.check_boq;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ConAddTOR {
    @FXML
    TextField progNameTextField, matTextField, memTextField,periodTextField, matQtyTextField ;
    @FXML
    TableView matTable ;
    @FXML
    TableView<List<String>> memTable ;
    @FXML
    Label errLabel ;

    ArrayList<MoMatForTor> matForTorArrayList ;
    ObservableList<MoMatForTor> matForTorObservableList;

    ArrayList<MoMem> memArraylist ;
    ObservableList<MoMem> moMemObservableList ;

    MoMatForTor selectMat ;



    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                matForTorArrayList = new ArrayList<>() ;
                memArraylist = new ArrayList<>() ;
                matTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        selectMat = (MoMatForTor) newValue;
                    }
                });
            }
        });
    }

    public void showTableMat(){
        matTable.getColumns().clear();
        matForTorObservableList = FXCollections.observableArrayList(matForTorArrayList) ;
        TableColumn matNameCol = new TableColumn("Material Name");
        TableColumn matQtyCol = new TableColumn("Quantity");
        matNameCol.setCellValueFactory(new PropertyValueFactory<MoMatForTor,String>("Mat_Name"));
        matQtyCol.setCellValueFactory(new PropertyValueFactory<MoMatForTor,Integer>("Mat_Qty"));
        matTable.getColumns().addAll(matNameCol,matQtyCol) ;
        matTable.setItems(matForTorObservableList) ;
    }

    public void showTableMem(){
        memTable.getColumns().clear();
        moMemObservableList = FXCollections.observableList(moMemObservableList) ;
        TableColumn memNameCol = new TableColumn("Participant Role") ;
        memNameCol.setCellValueFactory(new PropertyValueFactory<MoMem,String>("mem_Name"));
        memTable.getColumns().add(memNameCol) ;

    }

    public void eventAddMem(){
        if(memTextField.getText().isEmpty()){
            errLabel.setText("Please insert participant information.");
        }
        else if(memTextField.getText().length()>50){
            errLabel.setText("Participant is too long!");
        }
        else{
            memArraylist.add(new MoMem(memTextField.getText())) ;
        }
    }

    public void eventDelMem(){

    }

    public void eventAddMat(){
        if(matTextField.getText().isEmpty()){
            errLabel.setText("Please insert material information.");
        }
        else if(matQtyTextField.getText().isEmpty()){
            matQtyTextField.setText("1");
        }
        try{
            int qty = Integer.valueOf(matQtyTextField.getText()) ;
            if(qty <= 0 || qty > 999){
                errLabel.setText("Incorrectly Quantity!");
            }
            else if(matTextField.getText().length() > 50){
                errLabel.setText("Material name is too long.");
            }
            else{
                MoMatForTor temp = new MoMatForTor (matTextField.getText(),Integer.valueOf(matQtyTextField.getText()));
                matForTorArrayList.add(temp) ;
                showTableMat();
                matTextField.clear();
                matQtyTextField.clear();
            }
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            errLabel.setText("Incorrectly Material Information");
        }
    }

    public void eventDelMat(){
        if(matTable.getSelectionModel().isEmpty()){
            errLabel.setText("Please select material to delete.");
        }
        else if(selectMat != null){
            matForTorArrayList.remove(selectMat) ;
            showTableMat();
        }
    }

    public void eventAddButton(){

    }

    public void eventBackButton(){

    }


}
