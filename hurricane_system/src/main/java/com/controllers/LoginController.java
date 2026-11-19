package com.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.model.*;

import com.relief.App;

public class LoginController {

    @FXML
    private Button secondaryButton;

    @FXML
    private Label lbl_error;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    void login(ActionEvent event) {
        String username = txt_username.getText();
        String password = txt_password.getText();
        System.out.println(username + " " + password);

        ReliefApplication app = ReliefApplication.getInstance();

        if (!app.login(username, password)) {
            lbl_error.setText("Invalid username or password");
            return;
        }

        try {
            App.setRoot("home");
        } catch (java.io.IOException e) {
            lbl_error.setText("Unable to load home screen");
        }

    }

    @FXML
    private void goToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void goToMap() throws IOException {
    }

    @FXML
    private void goToCreateReliefRequest() throws IOException {
        
    }

    @FXML
    private void goToLogin() throws IOException {
        // Button that does nothing
    }

    @FXML
    private void goToSignUp() throws IOException {
        App.setRoot("signup");
    }

}
