package com.controllers;

import java.io.IOException;

import com.relief.App;

import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("home");
    }
}