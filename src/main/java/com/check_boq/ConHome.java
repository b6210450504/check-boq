package com.check_boq;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ConHome {
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

   public void eventAddTor(ActionEvent event) throws IOException {
      root = FXMLLoader.load(getClass().getResource("addTor.fxml"));
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
   }

   public void eventCreateBoq(){

   }

   public void eventAddCus(ActionEvent event) throws IOException {
      root = FXMLLoader.load(getClass().getResource("addCus.fxml"));
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
   }

   public void eventAddMat(ActionEvent event) throws IOException {
      root = FXMLLoader.load(getClass().getResource("addMat.fxml"));
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
   }
}