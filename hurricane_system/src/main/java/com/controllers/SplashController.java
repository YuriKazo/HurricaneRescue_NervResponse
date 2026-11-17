package com.controllers;

import java.io.IOException;

import com.relief.App;

import javafx.fxml.FXML;

public class SplashController {
    
    @FXML
    private void goToHome() throws IOException {
        App.setRoot("home");
    }
}
