package com.check_boq;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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

public class ConCheckBoq {
    private Stage stage;
    private Scene scene;
    private Parent root;

    // boq data
    @FXML
    TableView boqTable, matTable ;
    @FXML
    TextField searchBOQ ;
    @FXML
    Label idLabel, groupIDLabel, nameLabel, periodLabel ;
    @FXML
    TextArea boqMemTextArea ;
    SerBoqDataList serBoqDataList ;
    ObservableList<MoBOQ> moBOQObservableList ;
    ObservableList<MoBOQ> masterData ;
    ObservableList<MoMatForBoq> masterDataMat ;

    // tor data
    @FXML
    TextArea torTextArea ;
    SerTorDataList serTorDataList ;

    // customer data
    SerCusDataList serCusDataList ;

    // materials data
    @FXML
    TextField searchMat ;
    SerMatDataList serMatDataList ;
    @FXML
    Label totalLabel ;
    int total = 0 ;



    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                serBoqDataList = new SerBoqDataList() ;
                serTorDataList = new SerTorDataList() ;
                serCusDataList = new SerCusDataList() ;
                serMatDataList = new SerMatDataList() ;
                showBoqTable();
                masterData = FXCollections.observableList(serBoqDataList.getBoqArrayList()) ;

                // BOQ Search Filter
                FilteredList<MoBOQ> filteredData = new FilteredList<>(masterData, p -> true);
                searchBOQ.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(boq -> {
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        if (boq.getBO_ProjName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        if(String.valueOf(boq.getBO_ID()).contains(lowerCaseFilter)){
                            return true;
                        }
                        if(String.valueOf(boq.getBO_GroupID()).contains(lowerCaseFilter)){
                            return true ;
                        }
                        return false;
                    });
                });
                SortedList<MoBOQ> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(boqTable.comparatorProperty());
                boqTable.setItems(sortedData);
                // listener Boq Table
                boqTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectBoq((MoBOQ) newValue);
                    }
                });
            }
        });
    }

    public void showBoqTable(){
        boqTable.getColumns().clear();
        moBOQObservableList = FXCollections.observableList(serBoqDataList.getBoqArrayList()) ;
        TableColumn nameCol = new TableColumn("Project Name");
        TableColumn idCol = new TableColumn("BOQ_ID");
        TableColumn groupID = new TableColumn("Group_ID") ;
        nameCol.setCellValueFactory(new PropertyValueFactory<MoBOQ,String>("BO_ProjName"));
        idCol.setCellValueFactory(new PropertyValueFactory<MoBOQ,Integer>("BO_ID"));
        groupID.setCellValueFactory(new PropertyValueFactory<MoBOQ,Integer>("BO_GroupID"));
        boqTable.getColumns().addAll(idCol,nameCol,groupID) ;
        boqTable.setItems(moBOQObservableList);
    }

    public void showSelectBoq(MoBOQ select){
        showTorDes(select) ;
        idLabel.setText(String.valueOf(select.getBO_ID()));
        groupIDLabel.setText(serCusDataList.searchCusByID(select.getBO_GroupID()).getCS_Email())  ;
        nameLabel.setText(select.getBO_ProjName());
        periodLabel.setText(String.valueOf(select.getBO_Period()));
        showMemberBoq(select.getBO_Member()) ;
        showMatBoq(select.getBO_Materials()) ;
    }

    // Participants TextArea print
    public void showMemberBoq(String member){
        boqMemTextArea.appendText("Participant BOQ List\n");
        List<String> list = new ArrayList<String>(Arrays.asList(member.split(",")));
        int i = 1 ;
        for (String s:list) {
            boqMemTextArea.appendText( i +") "+ s + "\n");
            i++ ;
        }
    }

    // Tor TextArea print
    public void showTorDes(MoBOQ boq){
        MoTOR tor = serTorDataList.getTorByProjName(boq.getBO_ProjName()); ;
        torTextArea.appendText("Project Name: " + tor.getTO_Name() + "\n\n");
        torTextArea.appendText("GroupID: " + tor.getTO_GroupID() +"\n\n");
        torTextArea.appendText("Participant List\n");
        List<String> list = new ArrayList<String>(Arrays.asList(tor.getTO_Member().split(",")));
        int i = 1 ;
        for (String s:list) {
            torTextArea.appendText( i +") "+ s + "\n");
            i++ ;
        }
        torTextArea.appendText("\nMaterial List\n");
        list = new ArrayList<String>(Arrays.asList(tor.getTO_Materials().split(",")));
        i = 1 ;
        for (String s:list) {
            torTextArea.appendText( i +") "+ s + "Qty." + "\n");
            i++ ;
        }
        torTextArea.appendText("\n\nPeriod: "+ tor.getTO_Period() + " day.");
    }

    public void showMatBoq(String mat){
        ArrayList<MoMatForBoq> matForShow = new ArrayList<>() ;
        List<String> list = new ArrayList<String>(Arrays.asList(mat.split(",")));
        // "{name=qty, name=qty}"
        List<String> list2 ;
//        ArrayList<String> matName = new ArrayList<>() ;
//        ArrayList<String> matQty = new ArrayList<>() ;
        for(int i = 0 ; i < list.size(); i++){
            list2 = new ArrayList<String>(Arrays.asList(list.get(i).split("="))) ;
//            matName.add(list2.get(0)) ;
//            matQty.add(list2.get(1)) ;
            MoMaterial temp = serMatDataList.getMatInfoByName(list2.get(0)) ;
            // (int mat_ID, String mat_Name, int mat_Price, int mat_Qty)
            matForShow.add(new MoMatForBoq(temp.getMat_ID(),temp.getMat_Name(),temp.getMat_Price(),Integer.valueOf(list2.get(1)))) ;
        }
        for (MoMatForBoq t: matForShow) {
            total += t.getMat_Total() ;
        }
        ObservableList<MoMatForBoq> moMatForBoqObservableList ;
        matTable.getColumns().clear();
        moMatForBoqObservableList = FXCollections.observableList(matForShow) ;
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
        matTable.getColumns().addAll(idCol,nameCol,priceCol,qtyCol,totalCol) ;
        matTable.setItems(moMatForBoqObservableList);
        totalLabel.setText(String.valueOf(total));
    }

    public void eventAccept(){
        
    }


    public void eventBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("selectTor.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
