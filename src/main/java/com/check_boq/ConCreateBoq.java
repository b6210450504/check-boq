package com.check_boq;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class ConCreateBoq {
    MoTOR tor ;
    public void setTOR(MoTOR seTOR){
        this.tor = seTOR ;
    }

    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                System.out.println(tor.toString());
            }
        });
    }
}
