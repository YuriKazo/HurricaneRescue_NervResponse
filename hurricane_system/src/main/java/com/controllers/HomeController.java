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
        // Button that does nothing
    }

    @FXML
    private void goToMap() throws IOException {
        App.setRoot("map");
    }

    @FXML
    private void goToCreateReliefRequest() throws IOException {
        App.setRoot("create_requests");
    }

    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void goToSignUp() throws IOException {
        System.out.println("Going to sign up");
        App.setRoot("signup");
    }

    @FXML
    private void logOut() throws IOException {
    }
}
