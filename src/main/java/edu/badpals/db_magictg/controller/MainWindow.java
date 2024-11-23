package edu.badpals.db_magictg.controller;

import edu.badpals.db_magictg.conexion.Connect;
import edu.badpals.db_magictg.model.Card;
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

public class MainWindow  {

    @FXML
    private TextField TxtCardName;

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
    private TableColumn<Card, Integer> columnaID;
    @FXML
    private TableColumn<Card, String> columnaNombre;
    @FXML
    private TableColumn<Card, Integer> columnaManaCost;
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

//    @FXML
//    public void initialize() {
//        // Configurar las columnas con los métodos getter correspondientes
//        columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
//        columnaManaCost.setCellValueFactory(new PropertyValueFactory<>("manaCost"));
//        columnaColor.setCellValueFactory(new PropertyValueFactory<>("color"));
//        columnaIC.setCellValueFactory(new PropertyValueFactory<>("ic"));
//        columnaPoder.setCellValueFactory(new PropertyValueFactory<>("power"));
//        columnaResistencia.setCellValueFactory(new PropertyValueFactory<>("resistance"));
//        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("type"));
//        columnaRareza.setCellValueFactory(new PropertyValueFactory<>("rarity"));
//        columnaSet.setCellValueFactory(new PropertyValueFactory<>("set"));
//        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        // Cargar los datos en la tabla
//        ObservableList<Card> cards = (ObservableList<Card>) Connect.readAllCards();
//        tableViewCards.setItems(cards);
//    }



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







}