package com.check_boq;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ConAddMat {
    @FXML
    TableView matTable ;
    @FXML
    TextField nameTextField, priceTextField ;
    @FXML
    Label iDLabel,labelErr ;
    @FXML
    Button addButton, editButton, delButton ;


    private Stage stage;
    private Scene scene;
    private Parent root;

    SerMatDataList serMatDataList ;
    MoMaterial selectedMat ;
    ObservableList<MoMaterial> moMaterialObservableList ;

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                serMatDataList = new SerMatDataList() ;
                showTable();
//                editButton.setDisable(true);
                matTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        selectMat((MoMaterial) newValue) ;
                    }
                });
                priceTextField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
            }
        });
    }

    public void showTable(){
        labelErr.setTextFill(Color.RED);
        moMaterialObservableList = FXCollections.observableList(serMatDataList.getMoMaterialArrayList()) ;
        matTable.getColumns().clear();
        TableColumn idCol = new TableColumn("ID");
        TableColumn nameCol = new TableColumn("Name");
        TableColumn priceCol = new TableColumn("Price/Unit");
        idCol.setCellValueFactory(new PropertyValueFactory<MoMaterial,Integer>("Mat_ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<MoMaterial,String>("Mat_Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<MoMaterial,Integer>("Mat_Price"));
        matTable.getColumns().addAll(idCol,nameCol,priceCol) ;
        matTable.setItems(moMaterialObservableList);
    }

    public void selectMat(MoMaterial select){
        selectedMat = select ;
//        addButton.setDisable(true);
//        editButton.setDisable(false);
        iDLabel.setText(String.valueOf(select.getMat_ID()));
        nameTextField.setText(select.getMat_Name());
        priceTextField.setText(String.valueOf(select.getMat_Price()));
    }

    public void onClicked(){
//        addButton.setDisable(false);
//        editButton.setDisable(true);
        matTable.getSelectionModel().clearSelection();
        iDLabel.setText("...");
        nameTextField.clear();
        priceTextField.clear();
    }

    public void eventAddButton(){
        if(nameTextField.getText().isEmpty() || priceTextField.getText().isEmpty()){
            labelErr.setText("Please insert information.");
        }
        else if(nameTextField.getText().length() > 100 || Integer.valueOf(priceTextField.getText()) <= 0){
            labelErr.setText("Wrong information insert.");
        }
        else{
            MoMaterial tempMat = new MoMaterial (nameTextField.getText(),Integer.valueOf(priceTextField.getText())) ;
            serMatDataList.insertToDatabase(tempMat);
            showTable();
            labelErr.setTextFill(Color.GREEN);
            labelErr.setText("Add Material Complete.");
            priceTextField.clear();
            nameTextField.clear();
            iDLabel.setText("...");
            matTable.getSelectionModel().clearSelection();
        }
    }



    public void eventDel(){
        if(!matTable.getSelectionModel().isEmpty()){
            serMatDataList.delMatDataBase(selectedMat.getMat_ID()) ;
            matTable.getSelectionModel().clearSelection();
            matTable.getSelectionModel().clearSelection();
            iDLabel.setText("...");
            nameTextField.clear();
            priceTextField.clear();
            showTable();
            labelErr.setText("Delete Complete.");
        }
        else{
            labelErr.setText("Please Select Material.");
        }
    }


    public void eventBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
