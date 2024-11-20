package edu.badpals.db_magictg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class MainWindow  {

    @FXML
    public void toExportView (ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/export_view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) Btn_ExportData.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();

        }catch (IOException e){
            e.printStackTrace();
        }
    }





}