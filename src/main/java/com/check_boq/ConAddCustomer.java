package com.check_boq;

import javafx.application.Platform;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConAddCustomer {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    TableView cusTable;
    @FXML
    Label cusIDLabel, labelErr ;
    @FXML
    TextField nameTextField, phoneTextField, emailTextField ;
    @FXML
    Button addButton, editButton, delButton ;

    MoCustomer cusSelect ;
    ArrayList<MoCustomer> moCustomerArrayList ;
    ObservableList<MoCustomer> customerObservableList ;
    SerCusDataList serCusDataList ;

    public void initialize(){
        editButton.setDisable(true);
        labelErr.setMaxWidth(Double.MAX_VALUE);
        labelErr.setAlignment(Pos.CENTER);
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                serCusDataList = new SerCusDataList() ;
//                moCustomerArrayList = new ArrayList<>() ;
                moCustomerArrayList = serCusDataList.getCustomerArrayList() ;
                showTable();
                cusTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        showSelectedCus((MoCustomer) newValue) ;
                    }
                });
            }
        });
    }

    public void showSelectedCus(MoCustomer selectedCus){
        cusSelect = selectedCus ;
        addButton.setDisable(true);
        editButton.setDisable(false);
        nameTextField.setText(selectedCus.getCS_Name());
        phoneTextField.setText(selectedCus.getCS_Phone());
        emailTextField.setText(selectedCus.getCS_Email());
        cusIDLabel.setText(String.valueOf(selectedCus.getCS_ID()));
    }
    public void onClicked(){
        if(cusTable.getSelectionModel().isEmpty()){
//            System.out.println("None");
        }
        else{
            clear();
            showTable();
        }
    }

    public void eventEditButton(){
        if(!cusTable.getSelectionModel().isEmpty()){
            if(nameTextField.getText().isEmpty()||phoneTextField.getText().isEmpty()||emailTextField.getText().isEmpty()){
                labelErr.setText("Please insert correct information.");
            }
            else if(nameTextField.getText().length() > 80){
                labelErr.setText("Please insert correct information.");
            }
            else if(!isPhoneNumber(phoneTextField.getText())){
                labelErr.setText("Please insert correct information.");
            }
            else if(!isValidEmailAddress(emailTextField.getText()) && emailTextField.getText().length() > 50){
                labelErr.setText("Please insert correct information.");
            }
            else{
                MoCustomer temp = new MoCustomer(nameTextField.getText(),phoneTextField.getText(),emailTextField.getText()) ;
                serCusDataList.updateCusToDatabase(temp,cusSelect);
            }
        }
        //            serCusDataList.addCustomer(temp);
        showTable();
//            moCustomerArrayList.add(temp) ;
        clear();
    }

    public void clear(){
        cusTable.getSelectionModel().clearSelection();
        nameTextField.setText(null);
        phoneTextField.setText(null);
        emailTextField.setText(null);
        addButton.setDisable(false);
        editButton.setDisable(true);
        cusIDLabel.setText(null);
        showTable();
    }

    public void showTable(){
        cusTable.getColumns().clear();
        serCusDataList.getCustomerDatabase();
        moCustomerArrayList = serCusDataList.getCustomerArrayList() ;
        customerObservableList = (FXCollections.observableList(moCustomerArrayList));
        TableColumn idCol = new TableColumn("ID");
        TableColumn nameCol = new TableColumn("Name");
        TableColumn phoneCol = new TableColumn("Phone");
        TableColumn emailCol = new TableColumn("Email");
        idCol.setCellValueFactory(new PropertyValueFactory<MoCustomer,String>("CS_ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<MoCustomer,String>("CS_Name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<MoCustomer,String>("CS_Phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<MoCustomer,String>("CS_Email"));
        cusTable.getColumns().addAll(idCol,nameCol,phoneCol,emailCol) ;
        cusTable.setItems(customerObservableList);
    }
    public void eventBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void eventAddButton(){
        if(nameTextField.getText().isEmpty()||phoneTextField.getText().isEmpty()||emailTextField.getText().isEmpty()){
            labelErr.setText("Please insert correct information.");
        }
        else if(nameTextField.getText().length() > 80){
            labelErr.setText("Please insert correct information.");
        }
        else if(!isPhoneNumber(phoneTextField.getText())){
            labelErr.setText("Please insert correct information.");
        }
        else if(!isValidEmailAddress(emailTextField.getText()) && emailTextField.getText().length() > 50){
            labelErr.setText("Please insert correct information.");
        }
        else{
            MoCustomer temp = new MoCustomer(nameTextField.getText(),phoneTextField.getText(),emailTextField.getText()) ;
            try{
                serCusDataList.addCustomer(temp);
                labelErr.setText("Add Customer Complete.");
            } catch (Exception e) {
                labelErr.setText("Can't Add Customer.");
                e.printStackTrace();
            }
//            serCusDataList.addCustomer(temp);
            showTable();
//            moCustomerArrayList.add(temp) ;
            clear();
        }
    }

    public void eventDel(){
        if(!cusTable.getSelectionModel().isEmpty()){
            try{
                serCusDataList.deleteCustomer(cusSelect.getCS_ID());
                labelErr.setText("Delete Customer Complete.");
                showTable();
                clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            labelErr.setText("Please Select Customer to Delete.");
        }
    }

    private static boolean isValidEmailAddress(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if(mat.matches()){
            return true ;
        }
        else{
            return false ;
        }
    }
    private static boolean isPhoneNumber(String number){
        Pattern pattern = Pattern.compile("\\d{10}") ;
        Matcher mat = pattern.matcher(number) ;
        if(mat.matches()){
            return true ;
        }
        else{
            return false ;
        }
    }
}
