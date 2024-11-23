package edu.badpals.db_magictg.controller;

import edu.badpals.db_magictg.conexion.Connect;
import edu.badpals.db_magictg.model.Card;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainWindow  {

    @FXML
    private TextField TxtCardName;

    @FXML
    private Button BtnBuscarCarta;

    @FXML
    private ComboBox ChkFiltrarPor;

    @FXML
    private ComboBox ChkSet;

    @FXML
    private TableView TabCard;

    @FXML
    private Button BtnInsertar;

    @FXML
    private Button BtnModificar;

    @FXML
    private Button BtnEliminar;

    @FXML
    private Button BtnExportar;


    //Tabla
    @FXML
    private TableView<Card> tableViewCards;

    // Definir las columnas
    @FXML
    private TableColumn<Card, String> columnaID;
    @FXML
    private TableColumn<Card, String> columnaNombre;
    @FXML
    private TableColumn<Card, String> columnaManaCost;
    @FXML
    private TableColumn<Card, String> columnaColor;
    @FXML
    private TableColumn<Card, String> columnaIC;
    @FXML
    private TableColumn<Card, String> columnaPoder;
    @FXML
    private TableColumn<Card, String> columnaResistencia;
    @FXML
    private TableColumn<Card, String> columnaTipo;
    @FXML
    private TableColumn<Card, String> columnaRareza;
    @FXML
    private TableColumn<Card, String> columnaSet;
    @FXML
    private TableColumn<Card, String> columnaPrecio;



    @FXML
    public void initialize() {
        // Configurar las columnas con los métodos getter correspondientes
        columnaID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId_card()))
        );
        columnaNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );
        columnaManaCost.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMana_cost())
        );
        columnaColor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getColor())
        );
        columnaIC.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getColor_identity())
        );
        columnaPoder.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPoder()))
        );
        columnaResistencia.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getResistencia()))
        );
        // Configuración de columnas con objetos personalizados
        columnaTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().getName_type()));
        columnaRareza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRareza().getName_rarity()));
        columnaSet.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCard_set().getName_set()));

        columnaPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));


        // Cargar los datos en la tabla
        List<Card> cardList = Connect.readAllCards();
        ObservableList<Card> cards = FXCollections.observableArrayList(cardList);
        tableViewCards.setItems(cards);
    }



    @FXML
    public void toExportView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/db_magictg/export_view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void toInsertView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/db_magictg/Add_Card_view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void toDeleteView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/db_magictg/Delete_card_view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void toModifyView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/db_magictg/Modify_Price_View.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void toLogIn(ActionEvent event) throws IOException {
        // Cargar la nueva ventana
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/db_magictg/Log_in_view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void buscarCarta() {
        String nombreCarta = TxtCardName.getText().trim();
        if (nombreCarta.isEmpty()) {
            Alerts.newAlert(Alert.AlertType.ERROR, "El campo está vacío", "Por favor, introduce un nombre de carta.");
            return;
        }

        List<String> nombresCartas = Connect.getNombreCartas();

        // Verificar si el nombre existe en la lista
        if (nombresCartas.contains(nombreCarta)) {
            List<Card> cardList = Connect.searchCardByName(nombreCarta);
            ObservableList<Card> cards = FXCollections.observableArrayList(cardList);
            tableViewCards.setItems(cards);
        } else {
            Alerts.newAlert(Alert.AlertType.ERROR, "La carta no existe", "El nombre ingresado no coincide con ninguna carta.");
        }
    }





}