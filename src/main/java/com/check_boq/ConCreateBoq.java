package com.check_boq;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConCreateBoq {
    @FXML
    TableView matTable, matBoqTable ;
    @FXML
    TextArea torDesTextArea ;
    @FXML
    TextField qtyTextField, find ;
    @FXML
    Label totalLabel, idLabel, nameLabel, priceLabel ;

    private Stage stage;
    private Scene scene;
    private Parent root;

    SerMatDataList serMatDataList ;
    ObservableList<MoMaterial> moMaterialObservableList ;
    ObservableList<MoMaterial> masterData ;

    ArrayList<MoMatForBoq> moMatForBoqArrayList ;
    ObservableList<MoMatForBoq> moMatForBoqObservableList ;

    MoTOR tor ;
    public void setTOR(MoTOR seTOR){
        this.tor = seTOR ;
    }
    MoMaterial selectedMaterial ;

    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                serMatDataList = new SerMatDataList() ;
                masterData = FXCollections.observableList(serMatDataList.getMoMaterialArrayList()) ;
                showTableMat() ;
                showTORDes();
                moMatForBoqArrayList = new ArrayList<>() ;
                FilteredList<MoMaterial> filteredData = new FilteredList<>(masterData, p -> true);
                find.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(mat -> {
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        if (mat.getMat_Name().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches first name.
                        }
                        return false;
                    });
                });
                SortedList<MoMaterial> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(matTable.comparatorProperty());
                matTable.setItems(sortedData);
                matTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectedMat((MoMaterial) newValue);
                    }
                });
            }
        });
    }

    public void showTableMat(){
        moMaterialObservableList = FXCollections.observableList(serMatDataList.getMoMaterialArrayList()) ;
        matTable.getColumns().clear();
        TableColumn nameCol = new TableColumn("Material Name");
        TableColumn priceCol = new TableColumn("Price");
        TableColumn idCol = new TableColumn("ID");
        nameCol.setCellValueFactory(new PropertyValueFactory<MoMaterial,String>("Mat_Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<MoMaterial,Integer>("Mat_Price"));
        idCol.setCellValueFactory(new PropertyValueFactory<MoMaterial,Integer>("Mat_ID"));
        matTable.getColumns().addAll(idCol,nameCol,priceCol) ;
        matTable.setItems(moMaterialObservableList);
    }

    public void showMatBoqTable(){
        matBoqTable.getColumns().clear();
        moMatForBoqObservableList = FXCollections.observableList(moMatForBoqArrayList) ;
        TableColumn nameCol = new TableColumn("Material Name");
        TableColumn priceCol = new TableColumn("Price");
        TableColumn idCol = new TableColumn("ID");
        TableColumn qtyCol = new TableColumn("Qty.");
        TableColumn totalCol = new TableColumn("Total");
        nameCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq,String>("Mat_Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq,Integer>("Mat_Price"));
        idCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq,Integer>("Mat_ID"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq,Integer>("Mat_Qty"));
        totalCol.setCellValueFactory(new PropertyValueFactory<MoMatForBoq, Long>("Mat_Total"));

        matBoqTable.getColumns().addAll(idCol,nameCol,priceCol,qtyCol,totalCol) ;
        matBoqTable.setItems(moMatForBoqObservableList);
    }


    public void showSelectedMat(MoMaterial select){
        selectedMaterial = select ;
        idLabel.setText(String.valueOf(select.getMat_ID()));
        nameLabel.setText(select.getMat_Name());
        priceLabel.setText(String.valueOf(select.Mat_Price));
    }

    public void onClicked(){
        if(!matTable.getSelectionModel().isEmpty()){
            idLabel.setText("...");
            nameLabel.setText("...");
            priceLabel.setText("...");
            qtyTextField.clear();
            matTable.getSelectionModel().clearSelection();
        }
    }
    public void showTORDes(){
        torDesTextArea.appendText("Project Name: " + tor.getTO_Name() + "\n\n");
        torDesTextArea.appendText("GroupID: " + tor.getTO_GroupID() +"\n\n");
        torDesTextArea.appendText("Participant List\n");
        List<String> list = new ArrayList<String>(Arrays.asList(tor.getTO_Member().split(",")));
        int i = 1 ;
        for (String s:list) {
            torDesTextArea.appendText( i +") "+ s + "\n");
            i++ ;
        }
        torDesTextArea.appendText("\nMaterial List\n");
        list = new ArrayList<String>(Arrays.asList(tor.getTO_Materials().split(",")));
        i = 1 ;
        for (String s:list) {
            torDesTextArea.appendText( i +") "+ s + "Qty." + "\n");
            i++ ;
        }
        torDesTextArea.appendText("\n\nPeriod: "+ tor.getTO_Period() + " day.");
    }

    public void eventAdd(){
        if(matTable.getSelectionModel().isEmpty() ){
            // err label
        }
        else if(qtyTextField.getText().isEmpty() && !matTable.getSelectionModel().isEmpty()){
            qtyTextField.setText("1");
        }
        else{
            moMatForBoqArrayList.add( new MoMatForBoq(selectedMaterial.getMat_ID(),selectedMaterial.getMat_Name(),
                    selectedMaterial.getMat_Price(),Integer.valueOf(qtyTextField.getText()))) ;
            showMatBoqTable();
            onClicked();
        }
    }
    public void eventBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("selectTor.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
