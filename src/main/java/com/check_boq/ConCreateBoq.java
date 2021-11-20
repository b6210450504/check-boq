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

public class ConCreateBoq {
    @FXML
    TableView<MoMaterial> matTable ;
    @FXML
    TableView<MoMatForBoq> matBoqTable ;
    @FXML
    TextArea torDesTextArea ;
    @FXML
    TextField qtyTextField, find ;
    @FXML
    Label totalLabel, idLabel, nameLabel, priceLabel, errLabel ;
    @FXML
    Button createBoqButton, addButton, delButton ;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private SerMatDataList serMatDataList ;
    private ObservableList<MoMaterial> moMaterialObservableList ;
    private ObservableList<MoMaterial> masterData ;

    private ArrayList<MoMatForBoq> moMatForBoqArrayList ;
    private ObservableList<MoMatForBoq> moMatForBoqObservableList ;

    private SerBoqDataList serBoqDataList ;

    private MoTOR tor ;
    private long total = 0 ;
    public void setTOR(MoTOR seTOR){
        this.tor = seTOR ;
    }
    private MoMaterial selectedMaterial ;
    private MoMatForBoq selectedMaterialBoq;

    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                serMatDataList = new SerMatDataList() ;
                serBoqDataList = new SerBoqDataList() ;
                qtyTextField.setDisable(true);
                errLabel.setMaxWidth(Double.MAX_VALUE);
                errLabel.setAlignment(Pos.CENTER);
                masterData = FXCollections.observableList(serMatDataList.getMoMaterialArrayList()) ;
                showTableMat() ;
                showTORDes();
                moMatForBoqArrayList = new ArrayList<>() ;
                FilteredList<MoMaterial> filteredData = new FilteredList<>(masterData, p -> true);

                find.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredData.setPredicate(mat -> {
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (newValue.isEmpty()) {
                            return true;
                        }
                        return mat.getMat_Name().toLowerCase().contains(lowerCaseFilter); // Filter matches first name.
                    });
                });

                SortedList<MoMaterial> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(matTable.comparatorProperty());
                matTable.setItems(sortedData);

                matTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectedMat( newValue);
                    }
                });

                qtyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        qtyTextField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });

                matBoqTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showSelectedMatBoq(newValue);
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

    public void showSelectedMatBoq(MoMatForBoq select){
        qtyTextField.setDisable(false);
        selectedMaterialBoq = select ;
    }

    public void showSelectedMat(MoMaterial select){
        qtyTextField.setDisable(false);
        selectedMaterial = select ;
        idLabel.setText(String.valueOf(select.getMat_ID()));
        nameLabel.setText(select.getMat_Name());
        priceLabel.setText(String.valueOf(select.Mat_Price));
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

    public void onClicked(){
        if(!matTable.getSelectionModel().isEmpty() || !matBoqTable.getSelectionModel().isEmpty()){
            idLabel.setText("...");
            nameLabel.setText("...");
            priceLabel.setText("...");
            qtyTextField.clear();
            matTable.getSelectionModel().clearSelection();
            matBoqTable.getSelectionModel().clearSelection();
            errLabel.setText("");
            qtyTextField.setDisable(false);
        }

    }

    public void eventAdd(){
        if(matTable.getSelectionModel().isEmpty() ){
            errLabel.setText("Please select some Material");
        }
        else if(qtyTextField.getText().isEmpty() && !matTable.getSelectionModel().isEmpty()){
            qtyTextField.setText("1");
        }
        else if(Integer.parseInt(qtyTextField.getText()) < 1){
            errLabel.setText("Please insert more than 0");
        }
        else{
            MoMatForBoq temp = new MoMatForBoq(selectedMaterial.getMat_ID(),selectedMaterial.getMat_Name(),
                    selectedMaterial.getMat_Price(),Integer.parseInt(qtyTextField.getText())) ;
            boolean duplicated  = false ;
            for (MoMatForBoq s:moMatForBoqArrayList) {
                if(s.getMat_ID() == temp.getMat_ID()){
                    s.setMat_Qty(temp.getMat_Qty()+s.getMat_Qty());
                    s.setMat_Total((long) s.getMat_Price() * s.getMat_Qty());
                    duplicated = true ;
                }
            }
            if(!duplicated){
                moMatForBoqArrayList.add(temp) ;
            }
            showMatBoqTable();
            setTotal();
            onClicked();
        }
    }

    public void eventCreateBOQ(){
        if(moMatForBoqArrayList.isEmpty()){
            errLabel.setText("Please add some material.");
        }
        else{
            StringBuilder matString = new StringBuilder();
            for (MoMatForBoq m:moMatForBoqArrayList) {
                matString.append(m.getMat_Name()).append("=").append(m.getMat_Qty()).append(",");
            }
            matString = new StringBuilder(matString.substring(0, matString.length() - 1));
            MoBOQ temp = new MoBOQ(Integer.parseInt(tor.getTO_GroupID()), tor.getTO_Name(),tor.getTO_Member(), matString.toString(),total,tor.getTO_Period()) ;
            serBoqDataList.addBOQToDatabase(temp);
            errLabel.setText("Create BOQ complete.");
            createBoqButton.setDisable(true);
            addButton.setDisable(true);
        }
    }

    public void eventDel(){
        if (!matBoqTable.getSelectionModel().isEmpty()) {
            delMatBoq(selectedMaterialBoq);
            onClicked();
        }
        setTotal();
    }

    public void eventBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("selectTor.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void delMatBoq(MoMatForBoq moMatForBoq){
        moMatForBoqArrayList.removeIf(mat -> mat.getMat_Name().equals(moMatForBoq.getMat_Name()));
    }

    private void setTotal(){
        if (!moMatForBoqArrayList.isEmpty()) {
            total = 0;
            for (MoMatForBoq v:moMatForBoqArrayList)
                total += v.getMat_Total() ;

            totalLabel.setText(String.valueOf(total));
        }
        else
            totalLabel.setText("...");

    }


}
