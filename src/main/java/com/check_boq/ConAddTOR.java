package com.check_boq;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

public class ConAddTOR {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    TextField progNameTextField, matTextField, memTextField,periodTextField, matQtyTextField ;
    @FXML
    TableView matTable ;
    @FXML
    TableView<MoMem> memTable ;
    @FXML
    Label errLabel ;
    @FXML
    ChoiceBox<String> cusChoiceBox ;

    ArrayList<MoMatForTor> matForTorArrayList ;
    ObservableList<MoMatForTor> matForTorObservableList;

    ArrayList<MoMem> memArraylist ;
    ObservableList<MoMem> moMemObservableList ;

    MoMatForTor selectMat ;
    MoMem selectMem ;
    SerCusDataList serCusDataList ;
    SerTorDataList serTorDataList ;



    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                matForTorArrayList = new ArrayList<>() ;
                memArraylist = new ArrayList<>() ;
                errLabel.setMaxWidth(Double.MAX_VALUE);
                errLabel.setAlignment(Pos.CENTER);
                serCusDataList = new SerCusDataList() ;
                serTorDataList = new SerTorDataList() ;
                errLabel.setTextFill(Color.RED);
                cusChoiceBox.getItems().addAll(serCusDataList.returnNameArraylist()) ;
                matTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        selectMat = (MoMatForTor) newValue;
                    }
                });
                memTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        selectMem = newValue ;
                    }
                });
                periodTextField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            periodTextField.setText(newValue.replaceAll("[^\\d]", ""));
                        }
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
        moMemObservableList = FXCollections.observableList(memArraylist) ;
        TableColumn memNameCol = new TableColumn("Participant Role") ;
        TableColumn numberCol = new TableColumn( "Number" );
        numberCol.setCellFactory( new Callback<TableColumn, TableCell>()
        {
            @Override
            public TableCell call(TableColumn p )
            {
                return new TableCell()
                {
                    @Override
                    public void updateItem( Object item, boolean empty )
                    {
                        super.updateItem( item, empty );
                        setGraphic( null );
                        setText( empty ? null : getIndex() + 1 + "" );
                    }
                };
            }
        });
        memNameCol.setCellValueFactory(new PropertyValueFactory<MoMem,String>("mem_Name"));
        memTable.getColumns().addAll(numberCol,memNameCol) ;
        memTable.setItems(moMemObservableList);
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
            showTableMem();
            memTextField.clear();
        }
    }

    public void eventDelMem(){
        memArraylist.remove(selectMem) ;
        showTableMem();
        memTextField.clear();
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
        if(progNameTextField.getText().isEmpty()){
            errLabel.setText("Please insert information.");
        }
        else if(progNameTextField.getText().length() > 100 || Integer.valueOf(periodTextField.getText()) == 0 ||
                Integer.valueOf(periodTextField.getText()) > 90 || cusChoiceBox.getItems().isEmpty()){
            errLabel.setText("Can't add TOR.");
        }
        else if(matForTorArrayList.isEmpty() || memArraylist.isEmpty()){
            errLabel.setText("Add some Material or Participant.");
        }
        else{
            String matStr = "" ;
            String memStr = "" ;

            for(MoMatForTor mat : matForTorArrayList){
                matStr += mat.getMat_Name() ;
                matStr += "=" + mat.getMat_Qty() ;
                matStr += ", " ;
            }

            for(MoMem mem : memArraylist){
                memStr += mem.getMem_Name() ;
                memStr += ", "  ;
            }

            matStr = matStr.substring(0,matStr.length()-2) ;
            memStr = memStr.substring(0,memStr.length()-2) ;

            MoTOR temp = new MoTOR(progNameTextField.getText(),matStr,memStr,Integer.valueOf(periodTextField.getText()),
                    String.valueOf(serCusDataList.searchIDByName(cusChoiceBox.getValue())) ) ;
            serTorDataList.insetTOR(temp);
            errLabel.setTextFill(Color.GREEN);
            errLabel.setText("Add TOR Complete.");
            clear() ;
        }
    }

    public void clear(){
        progNameTextField.clear();
        matTextField.clear();
        memTextField.clear();
        periodTextField.clear();
        matQtyTextField.clear();
        memArraylist.clear();
        matForTorArrayList.clear() ;
        matForTorObservableList.clear();
        moMemObservableList.clear();
        memTable.getColumns().clear();
        matTable.getColumns().clear();
    }


    public void eventBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void torList() throws IOException {
        Stage stage = new Stage() ;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("torTable.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("TOR Table");
        stage.setScene(scene);
        stage.show();
    }
}
