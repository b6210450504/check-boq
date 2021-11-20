package com.check_boq;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConAuthen {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    PasswordField passwordField ;
    @FXML
    Label errMes ;


    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                errMes.setMaxWidth(Double.MAX_VALUE);
                errMes.setAlignment(Pos.CENTER);
                // for test only
                passwordField.setText("");
            }
        });
    }


    public void eventSignIn(ActionEvent event) throws IOException {
        if(passwordField.getText().isEmpty()){
            errMes.setText("Please insert pin/password.");
        }
        else if(passwordField.getText().equals("123456")){
            root = FXMLLoader.load(getClass().getResource("checkBoq.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }else{
            passwordField.clear();
            errMes.setText("Wrong Password/PIN.");
        }
    }

    public void eventBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
