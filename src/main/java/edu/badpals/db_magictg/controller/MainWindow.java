package edu.badpals.db_magictg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainWindow {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}