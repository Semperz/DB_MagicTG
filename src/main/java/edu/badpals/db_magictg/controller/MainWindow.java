package edu.badpals.db_magictg.controller;

import edu.badpals.db_magictg.conexion.Connect;
import edu.badpals.db_magictg.model.Card;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;

public class MainWindow  {

    @FXML
    private ComboBox<String> CmbSet;

    @FXML
    private ComboBox<String> CmbFiltrarPor;

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



        //Cargar combobox sets
        List<String> list = Connect.getSets();
        list.add(0, "Por defecto");
        CmbSet.getItems().addAll(list);
        CmbSet.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizarTablaConFiltros();
        });
        CmbFiltrarPor.getItems().addAll("Por defecto", "Precio Ascendente", "Precio Descendente");
        CmbFiltrarPor.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualizarTablaConFiltros();
        });

        //Inicializar tabla
        establecerCarta();
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


        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    public void toLogIn(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/badpals/db_magictg/Log_in_view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void establecerCarta() {
        String nombreCarta = TxtCardName.getText().trim();
        String tipoPrecio = CmbFiltrarPor.getValue();
        String tipoSet = CmbSet.getValue();

        List<String> nombresCartas = Connect.getNombreCartas();

        if (!nombreCarta.isEmpty() && !nombresCartas.contains(nombreCarta)) {
            Alerts.newAlert(Alert.AlertType.ERROR, "Error de búsqueda", "La carta '" + nombreCarta + "' no existe en la base de datos.");
            return;
        }

        // cartas filtradas
        ObservableList<Card> cards = obtenerCartasFiltradas(nombreCarta, tipoPrecio, tipoSet);

        // No resultado a los filtros
        if (cards.isEmpty() && !nombreCarta.isEmpty()) {
            Alerts.newAlert(Alert.AlertType.ERROR, "Sin resultados", "No se encontraron cartas que coincidan con los filtros aplicados.");
        }


        tableViewCards.setItems(cards);
    }


    private void actualizarTablaConFiltros() {
        establecerCarta();
    }

    private ObservableList<Card> obtenerCartasFiltradas(String nombreCarta, String tipoPrecio, String tipoSet) {
        List<Card> cardList;

        // Manejar caso de "Por defecto"
        boolean precioPorDefecto = tipoPrecio == null || "Por defecto".equals(tipoPrecio);
        boolean setPorDefecto = tipoSet == null || "Por defecto".equals(tipoSet);

        if (nombreCarta == null || nombreCarta.isEmpty()) {
            // Sin nombre de carta
            if (!precioPorDefecto && !setPorDefecto) {
                // Filtrar por precio y set
                cardList = "Precio Ascendente".equals(tipoPrecio)
                        ? Connect.searchByPrizeAscAndSet(tipoSet)
                        : Connect.searchByPrizeDescAndSet(tipoSet);
            } else if (!precioPorDefecto) {
                // Filtrar solo por precio
                cardList = "Precio Ascendente".equals(tipoPrecio)
                        ? Connect.searchByPrizeAsc()
                        : Connect.searchByPrizeDesc();
            } else if (!setPorDefecto) {
                // Filtrar solo por set
                cardList = Connect.searchCardsBySet(tipoSet);
            } else {
                // Sin filtros: mostrar todas las cartas
                cardList = Connect.readAllCards();
            }
        } else {
            // Con nombre de carta
            if (!precioPorDefecto && !setPorDefecto) {
                // Filtrar por nombre, precio y set
                cardList = "Precio Ascendente".equals(tipoPrecio)
                        ? Connect.searchCardByPrizeAscAndSet(tipoSet, nombreCarta)
                        : Connect.searchCardByPrizeDescAndSet(tipoSet, nombreCarta);
            } else if (!precioPorDefecto) {
                // Filtrar por nombre y precio
                cardList = "Precio Ascendente".equals(tipoPrecio)
                        ? Connect.searchCardByPrizeAsc(nombreCarta)
                        : Connect.searchCardByPrizeDesc(nombreCarta);
            } else if (!setPorDefecto) {
                // Filtrar por nombre y set
                cardList = Connect.searchCardsBySetAndName(tipoSet, nombreCarta);
            } else {
                // Filtrar solo por nombre
                cardList = Connect.searchCardByName(nombreCarta);
            }
        }

        return FXCollections.observableArrayList(cardList);
    }

    @FXML
    public void resetTable(){
        List<Card> allCards = Connect.readAllCards();
        ObservableList<Card> cards = FXCollections.observableArrayList(allCards);
        TxtCardName.clear();
        tableViewCards.setItems(cards);
        List<String> list = Connect.getSets();
        list.add(0, "Por defecto");
        CmbSet.setValue(list.get(0));
        CmbFiltrarPor.setValue("Por defecto");
    }

}