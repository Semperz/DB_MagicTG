package edu.badpals.db_magictg.controller;

import javafx.scene.control.Alert;

public class Alerts {

    public static void newAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
