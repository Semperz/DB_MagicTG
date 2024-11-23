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

public class ModifyWindow {

    @FXML
    private TextField Txt_NombreCarta;  // Campo para ingresar el nombre de la carta
    @FXML
    private TextField Txt_NuevoPrecio;  // Campo para ingresar el nuevo precio
    @FXML
    private Button Btn_ModificarPrecio;  // Botón para modificar el precio
    @FXML
    private Button Btn_VolverInicio;  // Botón para volver al inicio

    // Acción para modificar el precio de la carta
    @FXML
    public void modificarPrecio(ActionEvent event) {
        String nombreCarta = Txt_NombreCarta.getText();  // Obtener el nombre de la carta
        String nuevoPrecioStr = Txt_NuevoPrecio.getText();  // Obtener el nuevo precio

        try {
            // Verificar que el nombre de la carta no esté vacío
            if (nombreCarta == null || nombreCarta.trim().isEmpty()) {
                Alerts.newAlert(Alert.AlertType.ERROR, "Nombre de Carta Vacío", "Por favor, ingresa el nombre de la carta.");
                return;
            }

            // Convertir el nuevo precio a un número decimal
            float nuevoPrecio = Float.parseFloat(nuevoPrecioStr);

            // Llamamos a Connect para actualizar el precio de la carta en la base de datos
            boolean exito = Connect.modificarPrecioPorNombre(nombreCarta, nuevoPrecio);

            if (exito) {
                // Éxito en la modificación del precio
                System.out.println("Precio de la carta '" + nombreCarta + "' modificado a: " + nuevoPrecio);
                Alerts.newAlert(Alert.AlertType.INFORMATION, "Modificación Exitosa", "El precio de la carta '" + nombreCarta + "' ha sido modificado a: " + nuevoPrecio);
            } else {
                // No se pudo modificar el precio porque no se encontró la carta
                System.out.println("No se pudo modificar el precio de la carta '" + nombreCarta + "' porque no se encontró.");
                Alerts.newAlert(Alert.AlertType.ERROR, "Modificación Fallida", "No se encontró una carta con el nombre '" + nombreCarta + "'.");
            }
        } catch (NumberFormatException e) {
            // Capturar el error si el nuevo precio no es un número válido
            System.out.println("El nuevo precio debe ser un número válido.");
            Alerts.newAlert(Alert.AlertType.ERROR, "Precio Inválido", "Por favor, ingresa un precio válido (número decimal).");
        } catch (Exception e) {
            // Capturar cualquier otro error general
            e.printStackTrace();
            Alerts.newAlert(Alert.AlertType.ERROR, "Error de Modificación", "Ocurrió un error al modificar el precio de la carta. Detalles: " + e.getMessage());
        }
    }


    // Acción para volver a la ventana principal o la ventana de inicio
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