package com.check_boq;

import javafx.application.Platform;
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


public class ConAddMat {
    @FXML
    TableView<MoMaterial> matTable ;
    @FXML
    TextField nameTextField, priceTextField ;
    @FXML
    Label iDLabel,labelErr ;
    @FXML
    Button addButton, delButton ;


    private Stage stage;
    private Scene scene;
    private Parent root;

    private SerMatDataList serMatDataList ;
    private MoMaterial selectedMat ;
    private ObservableList<MoMaterial> moMaterialObservableList ;

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                serMatDataList = new SerMatDataList() ;
                showTable();
                matTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        selectMat(newValue) ;
                    }
                });
                priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
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
        labelErr.setText("");
    }

    public void eventAddButton(){
        if(nameTextField.getText().isEmpty() || priceTextField.getText().isEmpty()){
            labelErr.setTextFill(Color.RED);
            labelErr.setText("Please insert information.");
        }
        else if(nameTextField.getText().length() > 100 || Integer.parseInt(priceTextField.getText()) <= 0){
            labelErr.setTextFill(Color.RED);
            labelErr.setText("Wrong information insert.");
        }
        else{
            if (serMatDataList.checkMat(nameTextField.getText()) && matTable.getSelectionModel().isEmpty()){
                labelErr.setTextFill(Color.RED);
                labelErr.setText("Duplicate material.");
            }
            else if (serMatDataList.checkMat(nameTextField.getText()) && !matTable.getSelectionModel().isEmpty()){
                MoMaterial tempMat = new MoMaterial(nameTextField.getText(), Integer.parseInt(priceTextField.getText()));
                serMatDataList.updateMatDataBase(tempMat);
                showTable();
                labelErr.setTextFill(Color.GREEN);
                labelErr.setText("Edit Material Complete.");
                priceTextField.clear();
                nameTextField.clear();
                iDLabel.setText("...");
                matTable.getSelectionModel().clearSelection();
            }
            else {
                MoMaterial tempMat = new MoMaterial(nameTextField.getText(), Integer.parseInt(priceTextField.getText()));
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
    }

    public void eventDel(){
        if(!matTable.getSelectionModel().isEmpty()){
            serMatDataList.delMatDataBase(selectedMat.getMat_ID()) ;
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
