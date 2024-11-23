package edu.badpals.db_magictg.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
            // Convertir el nuevo precio a un número decimal
            double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);

            // Aquí puedes agregar la lógica para modificar el precio de la carta en la base de datos o en la lista interna
            // Por ejemplo:
            if (modificarPrecioCarta(nombreCarta, nuevoPrecio)) {
                System.out.println("Precio de la carta '" + nombreCarta + "' modificado a: " + nuevoPrecio);
            } else {
                System.out.println("No se pudo modificar el precio de la carta '" + nombreCarta + "'.");
            }
        } catch (NumberFormatException e) {
            System.out.println("El nuevo precio debe ser un número válido.");
        }
    }

    // Metodo ficticio para modificar el precio de la carta en la base de datos o lista (simulado)
    private boolean modificarPrecioCarta(String nombreCarta, double nuevoPrecio) {
        // Aquí deberías implementar la lógica para buscar la carta y actualizar el precio en la base de datos o en una lista interna.
        // Si la carta se encuentra y se actualiza el precio, retornar true. De lo contrario, retornar false.

        // Ejemplo de validación simulada:
        if (nombreCarta != null && !nombreCarta.isEmpty()) {
            // Aquí iría la lógica para actualizar el precio de la carta en la base de datos
            return true;
        } else {
            return false;
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
