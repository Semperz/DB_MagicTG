package edu.badpals.db_magictg.controller;

import edu.badpals.db_magictg.conexion.Connect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class DeleteWindow {

    @FXML
    private TextField Txt_EliminarID; // Campo para ingresar el ID de la carta a eliminar
    @FXML
    private TextField Txt_EliminarConfirmarNombre; // Campo para confirmar el nombre de la carta
    @FXML
    private Button Btn_EliminarCarta; // Botón para eliminar la carta
    @FXML
    private Button Btn_VolverInicio; // Botón para volver a la ventana principal

    // Acción para eliminar la carta
    @FXML
    public void eliminarCarta(ActionEvent event) {
        String idText = Txt_EliminarID.getText(); // Obtener el ID ingresado
        String nombre = Txt_EliminarConfirmarNombre.getText(); // Obtener el nombre ingresado

        // Verificamos que el ID y el nombre no estén vacíos
        if (idText.isEmpty() || nombre.isEmpty()) {
            // Mostrar alerta si los campos están vacíos
            Alerts.newAlert(Alert.AlertType.ERROR, "Campos Vacíos", "El ID y el nombre son campos obligatorios.");
            return;
        }

        try {
            int cardId = Integer.parseInt(idText); // Convertir el ID a entero

            // Verificar si existe una carta con ese ID y nombre
            String cardNameFromDB = Connect.obtenerNombrePorId(cardId);

            if (cardNameFromDB != null && cardNameFromDB.equalsIgnoreCase(nombre)) {
                // Si el nombre coincide con el de la carta (ignorando mayúsculas/minúsculas), eliminarla
                boolean exito = Connect.eliminarCartaPorId(cardId);

                if (exito) {
                    // Éxito al eliminar la carta
                    Alerts.newAlert(Alert.AlertType.INFORMATION, "Carta Eliminada", "La carta con ID " + cardId + " y nombre '" + nombre + "' ha sido eliminada.");
                } else {
                    // Si no se pudo eliminar la carta por algún motivo
                    Alerts.newAlert(Alert.AlertType.ERROR, "Error al Eliminar", "No se pudo eliminar la carta con el ID " + cardId + ".");
                }
            } else {
                // Si el nombre no coincide con la carta obtenida de la base de datos
                Alerts.newAlert(Alert.AlertType.ERROR, "Nombre Incorrecto", "El nombre ingresado no coincide con el de la carta con ID " + cardId + ".");
            }
        } catch (NumberFormatException e) {
            // Capturar el error si el ID no es un número válido
            Alerts.newAlert(Alert.AlertType.ERROR, "ID Inválido", "El ID debe ser un número válido.");
        } catch (Exception e) {
            // Capturar cualquier otro error general
            e.printStackTrace();
            Alerts.newAlert(Alert.AlertType.ERROR, "Error al Eliminar", "Ocurrió un error al intentar eliminar la carta. Detalles: " + e.getMessage());
        }
    }



    // Acción para volver a la ventana principal
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
