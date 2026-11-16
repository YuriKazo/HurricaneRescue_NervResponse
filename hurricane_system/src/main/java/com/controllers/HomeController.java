package com.controllers;

import java.io.IOException;

import com.relief.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeController {

    @FXML
    private Label lbl_hurricane_info;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void goToHome() throws IOException {

    }

    @FXML
    private void goToMap() throws IOException {
    }

    @FXML
    private void goToCreateReliefRequest() throws IOException {
        
    }

    @FXML
    private void goToLogin() throws IOException {

    }

    @FXML
    private void goToSignUp() throws IOException {
    }

    @FXML
    private void logOut() throws IOException {
        
    }
}
