package com.check_boq;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConCheckBoq {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){

            }
        });
    }

    public void eventBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("selectTor.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
