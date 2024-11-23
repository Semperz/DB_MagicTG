package edu.badpals.db_magictg.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class InsertWindow {

    @FXML
    private Button BtnVolver;
    @FXML
    private Button BtnAñadirCarta;
    @FXML
    private TextField Txt_Nombre;
    @FXML
    private ComboBox<String> Cmb_Set;
    @FXML
    private ComboBox<String> Cmb_Tipo;
    @FXML
    private ComboBox<String> Cmb_Rareza;
    @FXML
    private TextField Txt_Precio;
    @FXML
    private Spinner<Integer> Sp_Poder;
    @FXML
    private Spinner<Integer> Sp_Resistencia;
    @FXML
    private Spinner<Integer> Sp_McAzul;
    @FXML
    private Spinner<Integer> Sp_McRojo;
    @FXML
    private Spinner<Integer> Sp_McVerde;
    @FXML
    private Spinner<Integer> Sp_McNegro;
    @FXML
    private Spinner<Integer> Sp_McBlanco;
    @FXML
    private Spinner<Integer> Sp_McIncoloro;
    @FXML
    private CheckBox Chk_McPrirexiano;
    @FXML
    private CheckBox Chk_McDoble;
    @FXML
    private ComboBox<String> Cmb_McDoble;
    @FXML
    private CheckBox Chk_IcAzul;
    @FXML
    private CheckBox Chk_IcRojo;
    @FXML
    private CheckBox Chk_IcVerde;
    @FXML
    private CheckBox Chk_IcNegro;
    @FXML
    private CheckBox Chk_IcBlanco;

    // Acción para volver a la ventana anterior
    @FXML
    public void volver(ActionEvent event) {
        // Obtener el stage actual (ventana)
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close(); // Cerrar la ventana actual
    }

    // Acción para añadir carta a la base de datos (lógica personalizada)
    @FXML
    public void addCard(ActionEvent event) {
        // Obtener valores de los campos
        String nombre = Txt_Nombre.getText();
        String set = Cmb_Set.getValue();
        String tipo = Cmb_Tipo.getValue();
        String rareza = Cmb_Rareza.getValue();
        String precio = Txt_Precio.getText();
        int poder = Sp_Poder.getValue();
        int resistencia = Sp_Resistencia.getValue();
        int mcAzul = Sp_McAzul.getValue();
        int mcRojo = Sp_McRojo.getValue();
        int mcVerde = Sp_McVerde.getValue();
        int mcNegro = Sp_McNegro.getValue();
        int mcBlanco = Sp_McBlanco.getValue();
        int mcIncoloro = Sp_McIncoloro.getValue();

        // Obtener valores de las CheckBoxes
        boolean mcPrirexiano = Chk_McPrirexiano.isSelected();
        boolean mcDoble = Chk_McDoble.isSelected();
        String mcDobleValor = Cmb_McDoble.getValue();
        boolean icAzul = Chk_IcAzul.isSelected();
        boolean icRojo = Chk_IcRojo.isSelected();
        boolean icVerde = Chk_IcVerde.isSelected();
        boolean icNegro = Chk_IcNegro.isSelected();
        boolean icBlanco = Chk_IcBlanco.isSelected();

        // Mostrar los valores obtenidos por consola
        System.out.println("Carta añadida: ");
        System.out.println("Nombre: " + nombre);
        System.out.println("Set: " + set);
        System.out.println("Tipo: " + tipo);
        System.out.println("Rareza: " + rareza);
        System.out.println("Precio: " + precio);
        System.out.println("Poder: " + poder);
        System.out.println("Resistencia: " + resistencia);
        System.out.println("Maná Azul: " + mcAzul);
        System.out.println("Maná Rojo: " + mcRojo);
        System.out.println("Maná Verde: " + mcVerde);
        System.out.println("Maná Negro: " + mcNegro);
        System.out.println("Maná Blanco: " + mcBlanco);
        System.out.println("Maná Incoloro: " + mcIncoloro);
        System.out.println("¿Prirexiano?: " + mcPrirexiano);
        System.out.println("¿Doble?: " + mcDoble);
        System.out.println("Valor Doble: " + mcDobleValor);
        System.out.println("¿Ic Azul?: " + icAzul);
        System.out.println("¿Ic Rojo?: " + icRojo);
        System.out.println("¿Ic Verde?: " + icVerde);
        System.out.println("¿Ic Negro?: " + icNegro);
        System.out.println("¿Ic Blanco?: " + icBlanco);

        // Aquí se puede agregar la lógica para almacenar la carta en la base de datos

        // Vaciar los campos después de añadir la carta
        clearFields();
    }

    // Método para vaciar los campos del formulario
    private void clearFields() {
        Txt_Nombre.clear();
        Cmb_Set.setValue(null);
        Cmb_Tipo.setValue(null);
        Cmb_Rareza.setValue(null);
        Txt_Precio.clear();
        Sp_Poder.getValueFactory().setValue(0);
        Sp_Resistencia.getValueFactory().setValue(0);
        Sp_McAzul.getValueFactory().setValue(0);
        Sp_McRojo.getValueFactory().setValue(0);
        Sp_McVerde.getValueFactory().setValue(0);
        Sp_McNegro.getValueFactory().setValue(0);
        Sp_McBlanco.getValueFactory().setValue(0);
        Sp_McIncoloro.getValueFactory().setValue(0);
        Chk_McPrirexiano.setSelected(false);
        Chk_McDoble.setSelected(false);
        Cmb_McDoble.setValue(null);
        Chk_IcAzul.setSelected(false);
        Chk_IcRojo.setSelected(false);
        Chk_IcVerde.setSelected(false);
        Chk_IcNegro.setSelected(false);
        Chk_IcBlanco.setSelected(false);
    }

    // Método para inicializar la ventana (llenar ComboBoxes, etc.)
    @FXML
    public void initialize() {
        // Aquí se pueden inicializar valores predeterminados para ComboBoxes o cualquier otro control.
        Cmb_Set.getItems().addAll("Set 1", "Set 2", "Set 3"); // Ejemplo
        Cmb_Tipo.getItems().addAll("Tipo 1", "Tipo 2", "Tipo 3");
        Cmb_Rareza.getItems().addAll("Común", "Rara", "Épica");
        Cmb_McDoble.getItems().addAll("Doble 1", "Doble 2", "Doble 3");

        // Agregar valores predeterminados a los spinners
        Sp_Poder.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        Sp_Resistencia.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        Sp_McAzul.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        Sp_McRojo.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        Sp_McVerde.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        Sp_McNegro.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        Sp_McBlanco.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        Sp_McIncoloro.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
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
