package edu.badpals.db_magictg.controller;

import edu.badpals.db_magictg.conexion.Connect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import static edu.badpals.db_magictg.conexion.Connect.getRarezaId;

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
    private CheckBox Chk_IcAzul;
    @FXML
    private CheckBox Chk_IcRojo;
    @FXML
    private CheckBox Chk_IcVerde;
    @FXML
    private CheckBox Chk_IcNegro;
    @FXML
    private CheckBox Chk_IcBlanco;


    // Acción para añadir carta a la base de datos (lógica personalizada)
    @FXML
    public void addCard(ActionEvent event) {
        // Obtener los valores de los campos
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

        // Obtener los IDs de Rareza, Set y Tipo desde las tablas respectivas
        int rarezaId = Connect.getRarezaId(rareza);
        int setId = Connect.getSetId(set);
        int tipoId = Connect.getTipoId(tipo);

        boolean icAzul = Chk_IcAzul.isSelected();
        boolean icRojo = Chk_IcRojo.isSelected();
        boolean icVerde = Chk_IcVerde.isSelected();
        boolean icNegro = Chk_IcNegro.isSelected();
        boolean icBlanco = Chk_IcBlanco.isSelected();

        // Convertir el precio a un valor flotante
        float precioFloat = 0;
        try {
            precioFloat = Float.parseFloat(precio);
        } catch (NumberFormatException e) {
            Alerts.newAlert(Alert.AlertType.ERROR, "Precio Inválido", "El precio debe ser un número válido.");
            return;
        }

        // Verificar si todos los campos obligatorios están llenos
        if (nombre.isEmpty() || set == null || tipo == null || rareza == null) {
            Alerts.newAlert(Alert.AlertType.ERROR, "Campos Vacíos", "Todos los campos son obligatorios.");
            return;
        }

        // Construir la cadena de coste de maná y color
        String manaCost = buildManaCost(mcAzul, mcRojo, mcVerde, mcNegro, mcBlanco, mcIncoloro);
        String color = buildColor(mcAzul, mcRojo, mcVerde, mcNegro, mcBlanco);
        String colorIdentity = buildColorIdentity(icAzul, icRojo, icVerde, icNegro, icBlanco, mcAzul, mcRojo, mcVerde, mcNegro, mcBlanco);

        try {
            // Insertar la carta en la base de datos
            Connect.insertCard(nombre, manaCost, mcAzul + mcRojo + mcVerde + mcNegro + mcBlanco + mcIncoloro, color, colorIdentity,
                    poder, resistencia, tipoId, rarezaId, setId, precioFloat);
            Alerts.newAlert(Alert.AlertType.INFORMATION, "Carta Añadida", "La carta '" + nombre + "' ha sido añadida correctamente.");

            // Vaciar los campos después de añadir la carta
            clearFields();
        } catch (Exception e) {
            // En caso de cualquier error al insertar la carta
            e.printStackTrace();
            Alerts.newAlert(Alert.AlertType.ERROR, "Error al Añadir Carta", "Ocurrió un error al intentar añadir la carta. Detalles: " + e.getMessage());
        }
    }


    public static String buildManaCost(int mcAzul, int mcRojo, int mcVerde, int mcNegro, int mcBlanco, int mcIncoloro) {
        StringBuilder manaCost = new StringBuilder();

        // Añadir el valor de maná incoloro al principio como un número
        if (mcIncoloro > 0) manaCost.append(mcIncoloro);

        // Añadir los valores de maná por color
        if (mcAzul > 0) manaCost.append("{U}".repeat(mcAzul));
        if (mcRojo > 0) manaCost.append("{R}".repeat(mcRojo));
        if (mcVerde > 0) manaCost.append("{G}".repeat(mcVerde));
        if (mcNegro > 0) manaCost.append("{B}".repeat(mcNegro));
        if (mcBlanco > 0) manaCost.append("{W}".repeat(mcBlanco));

        return manaCost.toString();
    }



    public static String buildColor(int mcAzul, int mcRojo, int mcVerde, int mcNegro, int mcBlanco) {
        StringBuilder color = new StringBuilder();

        if (mcAzul > 0) color.append("Azul, ");
        if (mcRojo > 0) color.append("Rojo, ");
        if (mcVerde > 0) color.append("Verde, ");
        if (mcNegro > 0) color.append("Negro, ");
        if (mcBlanco > 0) color.append("Blanco, ");

        // Eliminar la última coma y espacio, si existe
        if (color.length() > 0) {
            color.setLength(color.length() - 2);
        }

        else {
            color.append("Incoloro");
        }

        return color.toString();
    }

    public static String buildColorIdentity(boolean icAzul, boolean icRojo, boolean icVerde, boolean icNegro, boolean icBlanco, int mcAzul, int mcRojo, int mcVerde, int mcNegro, int mcBlanco) {
        StringBuilder colorIdentity = new StringBuilder();

        if (icAzul || mcAzul > 0) colorIdentity.append("U");
        if (icRojo || mcRojo > 0) colorIdentity.append("R");
        if (icVerde || mcVerde > 0) colorIdentity.append("G");
        if (icNegro || mcNegro > 0) colorIdentity.append("B");
        if (icBlanco || mcBlanco > 0) colorIdentity.append("W");

        return colorIdentity.toString();
    }




    // Metodo para vaciar los campos del formulario
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
        Chk_IcAzul.setSelected(false);
        Chk_IcRojo.setSelected(false);
        Chk_IcVerde.setSelected(false);
        Chk_IcNegro.setSelected(false);
        Chk_IcBlanco.setSelected(false);
    }

    // Metodo para inicializar la ventana (llenar ComboBoxes, etc.)
    @FXML
    public void initialize() {
        // Cargar opciones desde la base de datos para cada ComboBox
        Cmb_Set.getItems().addAll(Connect.getSets());
        Cmb_Tipo.getItems().addAll(Connect.getTipos());
        Cmb_Rareza.getItems().addAll(Connect.getRarezas());

        // Configurar los valores predeterminados de los Spinners
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
