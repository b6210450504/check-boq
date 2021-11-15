package com.check_boq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Check BOQ");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        System.out.println("Hello World.");
    }

    public static void main(String[] args) {
        launch();
    }
}