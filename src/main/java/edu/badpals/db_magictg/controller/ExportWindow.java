package edu.badpals.db_magictg.controller;

import edu.badpals.db_magictg.conexion.Connect;
import edu.badpals.db_magictg.services.DataExporter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ExportWindow {

    @FXML
    private Button BtnExportar;

    @FXML
    private Button BtnVolver;

    @FXML
    private TextField TxtNombreArchivo;

    @FXML
    public void exportData(ActionEvent event) {
        String fileName = TxtNombreArchivo.getText().trim();
        if (fileName.isEmpty()) {
            Alerts.newAlert(Alert.AlertType.ERROR, "Error", "Por favor ingrese un nombre de archivo.");
            return;
        }

        Connect connection = new Connect();
        String data = Connect.listarCartasExport(connection); // Obtener los datos para exportar
        DataExporter.exportData(fileName, data); // Exportar los datos
        Alerts.newAlert(Alert.AlertType.INFORMATION, "Exportación Completa", "Los datos han sido exportados correctamente.");
    }


    @FXML
    public void toMainWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/db_magictg/Main_view_MTG.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
