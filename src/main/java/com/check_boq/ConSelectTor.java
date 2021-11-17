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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ConSelectTor {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TableView torTable ;
    @FXML
    TextField nameTextField,idTextField ;

    private ObservableList<MoTOR> torObservableList;
    private ObservableList<MoTOR> masterData ;

    private MoTOR tor ;

    SerTorDataList serTorDataList ;

    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serTorDataList = new SerTorDataList();
                masterData = FXCollections.observableList(serTorDataList.getTorArrayList()) ;
                torObservableList = FXCollections.observableList(serTorDataList.getTorArrayList()) ;
                showTable();
                FilteredList<MoTOR> filteredData = new FilteredList<>(masterData, p -> true);
                nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(tor -> {
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        if (tor.getTO_Name().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches first name.
                        }
                        return false;
                    });
                });
                SortedList<MoTOR> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(torTable.comparatorProperty());
                torTable.setItems(sortedData);
                torTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectedTor((MoTOR) newValue);
                    }
                });
            }
        });
    }

    public void showTable(){
        torTable.getColumns().clear();
        ObservableList<MoTOR> moTORS = FXCollections.observableList(serTorDataList.getTorArrayList()) ;
        TableColumn groupIDCol = new TableColumn("GroupID");
        TableColumn projNameCol = new TableColumn("Project Name");
        TableColumn matCol = new TableColumn("Material");
        TableColumn memberCol = new TableColumn("Member") ;
        TableColumn periodCol = new TableColumn("Period") ;

        groupIDCol.setCellValueFactory(new PropertyValueFactory<MoTOR,Integer>("TO_GroupID"));
        projNameCol.setCellValueFactory(new PropertyValueFactory<MoTOR,String>("TO_Name"));
        matCol.setCellValueFactory(new PropertyValueFactory<MoTOR,String>("TO_Materials"));
        memberCol.setCellValueFactory(new PropertyValueFactory<MoTOR, String>("TO_Member"));
        periodCol.setCellValueFactory(new PropertyValueFactory<MoTOR,Integer>("TO_Period"));
        torTable.getColumns().addAll(groupIDCol,projNameCol,matCol,memberCol,periodCol) ;
        torTable.setItems(moTORS);
    }

    public void showTable(int id){
        torTable.getColumns().clear();
        ArrayList<MoTOR> searchList = new ArrayList<>() ;
        for (MoTOR tor: serTorDataList.getTorArrayList()) {
            if(Integer.valueOf(tor.getTO_GroupID()) == id){
                searchList.add(tor) ;
            }
        }
        ObservableList<MoTOR> moTORS = FXCollections.observableList(searchList) ;
        TableColumn groupIDCol = new TableColumn("GroupID");
        TableColumn projNameCol = new TableColumn("Project Name");
        TableColumn matCol = new TableColumn("Material");
        TableColumn memberCol = new TableColumn("Member") ;
        TableColumn periodCol = new TableColumn("Period") ;
        groupIDCol.setCellValueFactory(new PropertyValueFactory<MoTOR,Integer>("TO_GroupID"));
        projNameCol.setCellValueFactory(new PropertyValueFactory<MoTOR,String>("TO_Name"));
        matCol.setCellValueFactory(new PropertyValueFactory<MoTOR,String>("TO_Materials"));
        memberCol.setCellValueFactory(new PropertyValueFactory<MoTOR, String>("TO_Member"));
        periodCol.setCellValueFactory(new PropertyValueFactory<MoTOR,Integer>("TO_Period"));
        torTable.getColumns().addAll(groupIDCol,projNameCol,matCol,memberCol,periodCol) ;
        torTable.setItems(moTORS);
    }

    public void eventIDSearch(){
        showTable(Integer.valueOf(idTextField.getText()));
    }
    public void eventBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void onClick(){
        initialize();
        idTextField.clear();
        nameTextField.clear();
    }
    public void eventCreateBoq(ActionEvent event) throws IOException {
        if(!torTable.getSelectionModel().isEmpty()){
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createBoq.fxml"));
            stage.setScene(new Scene(loader.load(), 1024, 768));
            ConCreateBoq createBOQController = loader.getController() ;
            createBOQController.setTOR(tor);
            stage.show();
        }
    }

    public void showSelectedTor(MoTOR selectedTOR){
        this.tor = selectedTOR ;
    }
}
