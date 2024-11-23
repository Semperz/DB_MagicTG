package edu.badpals.db_magictg.conexion;

import edu.badpals.db_magictg.controller.Alerts;
import edu.badpals.db_magictg.model.Card;
import edu.badpals.db_magictg.model.Rarity;
import edu.badpals.db_magictg.model.Set;
import edu.badpals.db_magictg.model.Type;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class Connect {
    static String urldb = "jdbc:mysql://localhost:3306/db_magictg";
    public static Connection crearConexion() throws SQLException {
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("user", "root");
        propiedadesConexion.setProperty("password", "root");
        return getConnection(urldb, propiedadesConexion);
    }

    public static Connection crearConexionUsers() throws SQLException {
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("user", "root");
        propiedadesConexion.setProperty("password", "root");
        return getConnection("jdbc:mysql://localhost:3306/db_login_magictg", propiedadesConexion);
    }

    public void cerrarConexion(Connection conexion) throws SQLException {
        conexion.close();
    }

    public static String listarCartasExport(){
        String query = "SELECT * FROM t_cards";
        StringBuilder result = new StringBuilder();
        try {
            Statement statement = crearConexion().createStatement();
            ResultSet rs = statement.executeQuery(query);

            int columnCount = rs.getMetaData().getColumnCount();

            // Extraer datos en formato "clave:valor"
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    result.append(rs.getMetaData().getColumnName(i)).append(":").append(rs.getString(i));
                    if (i < columnCount) result.append(", ");
                }
                result.append("\n"); // cambio de linea
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result.toString();

    }

    public static List<Card> readAllCards(){
        List<Card> selectedCard = new ArrayList<>();
        String query = "SELECT \n" +
                "\tc.ID_CARD,\n" +
                "\tc.NOMBRE,\n" +
                "    c.MANA_COST,\n" +
                "    c.CMC,\n" +
                "    c.COLOR,\n" +
                "    c.COLOR_IDENTITY,\n" +
                "    c.PODER,\n" +
                "    c.RESISTENCIA,\n" +
                "    t.ID_TYPE,\n" +
                "    t.TYPE_NAME,\n" +
                "    r.ID_RAREZA,\n" +
                "    r.NOMBRE_RAREZA,\n" +
                "    s.ID_SET,\n" +
                "    s.SET_NAME,\n" +
                "    c.PRECIO\n" +
                "    FROM t_cards as c INNER JOIN t_type as t\n" +
                "\t\t\t\t\tON c.TIPO=t.ID_TYPE\n" +
                "                    INNER JOIN t_rareza as r\n" +
                "\t\t\t\t\t\tON c.RAREZA=r.ID_RAREZA\n" +
                "\t\t\t\t\t\t\tINNER JOIN t_set as s\n" +
                "\t\t\t\t\t\t\t\tON c.CARD_SET=s.ID_SET\n" +
                "\tORDER BY c.ID_CARD;";
        PreparedStatement ps;
        try {
            ps = crearConexion().prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("ID_CARD");
                String nombre = rs.getString("NOMBRE");
                String manaCost = rs.getString("MANA_COST");
                int cmc = rs.getInt("CMC");
                String color = rs.getString("COLOR");
                String colorIdentity = rs.getString("COLOR_IDENTITY");
                int poder = rs.getInt("PODER");
                int resistencia = rs.getInt("RESISTENCIA");
                Type tipo = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE_NAME"));
                Rarity rareza = new Rarity(rs.getInt("ID_RAREZA"), rs.getString("NOMBRE_RAREZA"));
                Set cardSet = new Set(rs.getInt("ID_SET"), rs.getString("SET_NAME"));
                float precio = rs.getFloat("PRECIO");
                Card card = new Card(id, nombre, manaCost, cmc, color, colorIdentity, poder, resistencia, tipo, rareza, cardSet, precio);
                selectedCard.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedCard;
    }

    public static List<Card> searchCardByName(String cardName){
        List<Card> selectedCard = new ArrayList<>();
        String query = "SELECT c.ID_CARD, c.NOMBRE, c.MANA_COST, c.CMC, c.COLOR, c.COLOR_IDENTITY, " +
                "c.PODER, c.RESISTENCIA, t.ID_TYPE, t.TYPE_NAME, r.ID_RAREZA, r.NOMBRE_RAREZA, " +
                "s.ID_SET, s.SET_NAME, c.PRECIO " +
                "FROM t_cards as c " +
                "INNER JOIN t_type as t ON c.TIPO = t.ID_TYPE " +
                "INNER JOIN t_rareza as r ON c.RAREZA = r.ID_RAREZA " +
                "INNER JOIN t_set as s ON c.CARD_SET = s.ID_SET " +
                "WHERE c.NOMBRE = ?\n" +
                "ORDER BY c.ID_CARD DESC;";
        try (Connection conn = crearConexion();
             PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, cardName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID_CARD");
                String nombre = rs.getString("NOMBRE");
                String manaCost = rs.getString("MANA_COST");
                int cmc = rs.getInt("CMC");
                String color = rs.getString("COLOR");
                String colorIdentity = rs.getString("COLOR_IDENTITY");
                int poder = rs.getInt("PODER");
                int resistencia = rs.getInt("RESISTENCIA");
                Type tipo = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE_NAME"));
                Rarity rareza = new Rarity(rs.getInt("ID_RAREZA"), rs.getString("NOMBRE_RAREZA"));
                Set cardSet = new Set(rs.getInt("ID_SET"), rs.getString("SET_NAME"));
                float precio = rs.getFloat("PRECIO");
                Card card = new Card(id, nombre, manaCost, cmc, color, colorIdentity, poder, resistencia, tipo, rareza, cardSet, precio);
                selectedCard.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedCard;
    }

    public static boolean eliminarCartaPorId(int cardId) {
        String query = "DELETE FROM t_cards WHERE ID_CARD = ?";

        try (Connection connection = crearConexion()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cardId);

            int filasAfectadas = ps.executeUpdate(); // Ejecutar la eliminación

            // Verificamos si se eliminaron filas
            return filasAfectadas > 0; // Retornamos true si se eliminó una carta, false si no
        } catch (SQLException e) {
            // Si ocurre un error en la consulta, imprimimos el stack trace y retornamos false
            e.printStackTrace();
            return false;
        }
    }



    // Metodo para obtener el nombre de la carta por su ID
    public static String obtenerNombrePorId(int cardId) {
        String query = "SELECT NOMBRE FROM t_cards WHERE ID_CARD = ?";

        try (Connection connection = crearConexion()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cardId);
            ResultSet rs = ps.executeQuery();

            // Verificamos si se obtuvo un resultado
            if (rs.next()) {
                return rs.getString("NOMBRE");
            } else {
                // Si no se encuentra ninguna carta con ese ID, mostramos un mensaje
                Alerts.newAlert(Alert.AlertType.ERROR, "Carta No Encontrada", "No se encontró una carta con el ID " + cardId + ".");
            }
        } catch (SQLException e) {
            // Capturamos cualquier excepción y mostramos el mensaje de error
            e.printStackTrace();
            Alerts.newAlert(Alert.AlertType.ERROR, "Error de Base de Datos", "Hubo un error al obtener el nombre de la carta: " + e.getMessage());
        }
        return null; // Si no se encuentra la carta o ocurre un error, retornamos null
    }


    public static boolean modificarPrecioPorNombre(String nombreCarta, double nuevoPrecio) {
        // Usamos LOWER() para hacer que la comparación sea insensible a mayúsculas/minúsculas
        String query = "UPDATE t_cards SET PRECIO = ? WHERE LOWER(NOMBRE) = LOWER(?)";

        try (Connection connection = crearConexion()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDouble(1, nuevoPrecio);  // Establecer el nuevo precio
            ps.setString(2, nombreCarta.toLowerCase());  // Convertir el nombre de la carta a minúsculas

            int filasAfectadas = ps.executeUpdate();  // Ejecutar la actualización

            if (filasAfectadas > 0) {
                Alerts.newAlert(Alert.AlertType.INFORMATION, "Confirmación", "Precio de la carta '" + nombreCarta + "' modificado a: " + nuevoPrecio);
                return true;
            } else {
                Alerts.newAlert(Alert.AlertType.ERROR, "Modificación Fallida", "Nombre o precio incorrecto.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void insertCard(String nombre, String manaCost, int cmc, String color, String colorIdentity,
                                  int poder, int resistencia, int tipo, int rareza, int cardSet, float precio) {
        String query = "INSERT INTO T_CARDS (NOMBRE, MANA_COST, CMC, COLOR, COLOR_IDENTITY, PODER, RESISTENCIA, TIPO, RAREZA, CARD_SET, PRECIO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecer los valores en la consulta
            stmt.setString(1, nombre);
            stmt.setString(2, manaCost);
            stmt.setInt(3, cmc);
            stmt.setString(4, color);
            stmt.setString(5, colorIdentity);
            stmt.setInt(6, poder);
            stmt.setInt(7, resistencia);
            stmt.setInt(8, tipo);
            stmt.setInt(9, rareza);
            stmt.setInt(10, cardSet);
            stmt.setFloat(11, precio);

            // Ejecutar la consulta de inserción
            stmt.executeUpdate();
            System.out.println("Carta añadida correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar la carta: " + e.getMessage());
        }
    }

    // Obtener lista de Sets desde la base de datos
    public static List<String> getSets() {
        List<String> sets = new ArrayList<>();
        String query = "SELECT SET_NAME FROM T_SET"; // Cambié "sets" por "T_SET" y "SET_NAME" como columna
        try (Connection conn = crearConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                sets.add(rs.getString("SET_NAME")); // Asegúrate de que el nombre de la columna sea correcto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sets;
    }

    // Obtener lista de Tipos desde la base de datos
    public static List<String> getTipos() {
        List<String> tipos = new ArrayList<>();
        String query = "SELECT TYPE_NAME FROM T_TYPE"; // Cambié "tipos" por "T_TYPE" y "TYPE_NAME" como columna
        try (Connection conn = crearConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tipos.add(rs.getString("TYPE_NAME")); // Asegúrate de que el nombre de la columna sea correcto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipos;
    }

    // Obtener lista de Rarezas desde la base de datos
    public static List<String> getRarezas() {
        List<String> rarezas = new ArrayList<>();
        String query = "SELECT NOMBRE_RAREZA FROM T_RAREZA"; // Cambié "rarezas" por "T_RAREZA" y "NOMBRE_RAREZA" como columna
        try (Connection conn = crearConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                rarezas.add(rs.getString("NOMBRE_RAREZA")); // Asegúrate de que el nombre de la columna sea correcto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rarezas;
    }

    // Obtener el ID de un Set desde la base de datos
    public static int getSetId(String setName) {
        String query = "SELECT ID_SET FROM T_SET WHERE SET_NAME = ?"; // Cambié "nombre" por "SET_NAME"
        try (Connection conn = crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, setName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_SET"); // Cambié "id" por "ID_SET"
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra el Set
    }

    // Obtener el ID de un Tipo desde la base de datos
    public static int getTipoId(String tipoName) {
        String query = "SELECT ID_TYPE FROM T_TYPE WHERE TYPE_NAME = ?"; // Cambié "nombre" por "TYPE_NAME"
        try (Connection conn = crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tipoName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_TYPE"); // Cambié "id" por "ID_TYPE"
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra el Tipo
    }

    // Obtener el ID de una Rareza desde la base de datos
    public static int getRarezaId(String rarezaName) {
        String query = "SELECT ID_RAREZA FROM T_RAREZA WHERE NOMBRE_RAREZA = ?"; // Cambié "nombre" por "NOMBRE_RAREZA"
        try (Connection conn = crearConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, rarezaName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_RAREZA"); // Cambié "id" por "ID_RAREZA"
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra la Rareza
    }


    public static List<Card> searchCardByPrizeAsc(String cardName){
        List<Card> selectedCard = new ArrayList<>();
        String query = "SELECT \n" +
                "\tc.ID_CARD,\n" +
                "\tc.NOMBRE,\n" +
                "    c.MANA_COST,\n" +
                "    c.CMC,\n" +
                "    c.COLOR,\n" +
                "    c.COLOR_IDENTITY,\n" +
                "    c.PODER,\n" +
                "    c.RESISTENCIA,\n" +
                "    t.ID_TYPE,\n" +
                "    t.TYPE_NAME,\n" +
                "    r.ID_RAREZA,\n" +
                "    r.NOMBRE_RAREZA,\n" +
                "    s.ID_SET,\n" +
                "    s.SET_NAME,\n" +
                "    c.PRECIO\n" +
                "    FROM t_cards as c INNER JOIN t_type as t\n" +
                "\t\t\t\t\tON c.TIPO=t.ID_TYPE\n" +
                "                    INNER JOIN t_rareza as r\n" +
                "\t\t\t\t\t\tON c.RAREZA=r.ID_RAREZA\n" +
                "\t\t\t\t\t\t\tINNER JOIN t_set as s\n" +
                "\t\t\t\t\t\t\t\tON c.CARD_SET=s.ID_SET\n" +
                "\tWHERE c.NOMBRE = ?\n" +
                "\tORDER BY c.PRECIO ASC;";
        PreparedStatement ps;
        try {
            ps = crearConexion().prepareStatement(query);
            ps.setString(1, cardName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID_CARD");
                String nombre = rs.getString("NOMBRE");
                String manaCost = rs.getString("MANA_COST");
                int cmc = rs.getInt("CMC");
                String color = rs.getString("COLOR");
                String colorIdentity = rs.getString("COLOR_IDENTITY");
                int poder = rs.getInt("PODER");
                int resistencia = rs.getInt("RESISTENCIA");
                Type tipo = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE_NAME"));
                Rarity rareza = new Rarity(rs.getInt("ID_RAREZA"), rs.getString("NOMBRE_RAREZA"));
                Set cardSet = new Set(rs.getInt("ID_SET"), rs.getString("SET_NAME"));
                float precio = rs.getFloat("PRECIO");
                Card card = new Card(id, nombre, manaCost, cmc, color, colorIdentity, poder, resistencia, tipo, rareza, cardSet, precio);
                selectedCard.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedCard;
    }


    public static List<Card> searchByPrizeAsc(){
        List<Card> cards = new ArrayList<>();
        String query = "SELECT \n" +
                "\tc.ID_CARD,\n" +
                "\tc.NOMBRE,\n" +
                "    c.MANA_COST,\n" +
                "    c.CMC,\n" +
                "    c.COLOR,\n" +
                "    c.COLOR_IDENTITY,\n" +
                "    c.PODER,\n" +
                "    c.RESISTENCIA,\n" +
                "    t.ID_TYPE,\n" +
                "    t.TYPE_NAME,\n" +
                "    r.ID_RAREZA,\n" +
                "    r.NOMBRE_RAREZA,\n" +
                "    s.ID_SET,\n" +
                "    s.SET_NAME,\n" +
                "    c.PRECIO\n" +
                "    FROM t_cards as c INNER JOIN t_type as t\n" +
                "\t\t\t\t\tON c.TIPO=t.ID_TYPE\n" +
                "                    INNER JOIN t_rareza as r\n" +
                "\t\t\t\t\t\tON c.RAREZA=r.ID_RAREZA\n" +
                "\t\t\t\t\t\t\tINNER JOIN t_set as s\n" +
                "\t\t\t\t\t\t\t\tON c.CARD_SET=s.ID_SET\n" +
                "\tORDER BY c.PRECIO ASC;";
        PreparedStatement ps;
        try {
            ps = crearConexion().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID_CARD");
                String nombre = rs.getString("NOMBRE");
                String manaCost = rs.getString("MANA_COST");
                int cmc = rs.getInt("CMC");
                String color = rs.getString("COLOR");
                String colorIdentity = rs.getString("COLOR_IDENTITY");
                int poder = rs.getInt("PODER");
                int resistencia = rs.getInt("RESISTENCIA");
                Type tipo = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE_NAME"));
                Rarity rareza = new Rarity(rs.getInt("ID_RAREZA"), rs.getString("NOMBRE_RAREZA"));
                Set cardSet = new Set(rs.getInt("ID_SET"), rs.getString("SET_NAME"));
                float precio = rs.getFloat("PRECIO");
                Card card = new Card(id, nombre, manaCost, cmc, color, colorIdentity, poder, resistencia, tipo, rareza, cardSet, precio);
                cards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public static List<Card> searchByPrizeDesc(){
        List<Card> cards = new ArrayList<>();
        String query = "SELECT \n" +
                "\tc.ID_CARD,\n" +
                "\tc.NOMBRE,\n" +
                "    c.MANA_COST,\n" +
                "    c.CMC,\n" +
                "    c.COLOR,\n" +
                "    c.COLOR_IDENTITY,\n" +
                "    c.PODER,\n" +
                "    c.RESISTENCIA,\n" +
                "    t.ID_TYPE,\n" +
                "    t.TYPE_NAME,\n" +
                "    r.ID_RAREZA,\n" +
                "    r.NOMBRE_RAREZA,\n" +
                "    s.ID_SET,\n" +
                "    s.SET_NAME,\n" +
                "    c.PRECIO\n" +
                "    FROM t_cards as c INNER JOIN t_type as t\n" +
                "\t\t\t\t\tON c.TIPO=t.ID_TYPE\n" +
                "                    INNER JOIN t_rareza as r\n" +
                "\t\t\t\t\t\tON c.RAREZA=r.ID_RAREZA\n" +
                "\t\t\t\t\t\t\tINNER JOIN t_set as s\n" +
                "\t\t\t\t\t\t\t\tON c.CARD_SET=s.ID_SET\n" +
                "\tORDER BY c.PRECIO DESC;";
        PreparedStatement ps;
        try {
            ps = crearConexion().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID_CARD");
                String nombre = rs.getString("NOMBRE");
                String manaCost = rs.getString("MANA_COST");
                int cmc = rs.getInt("CMC");
                String color = rs.getString("COLOR");
                String colorIdentity = rs.getString("COLOR_IDENTITY");
                int poder = rs.getInt("PODER");
                int resistencia = rs.getInt("RESISTENCIA");
                Type tipo = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE_NAME"));
                Rarity rareza = new Rarity(rs.getInt("ID_RAREZA"), rs.getString("NOMBRE_RAREZA"));
                Set cardSet = new Set(rs.getInt("ID_SET"), rs.getString("SET_NAME"));
                float precio = rs.getFloat("PRECIO");
                Card card = new Card(id, nombre, manaCost, cmc, color, colorIdentity, poder, resistencia, tipo, rareza, cardSet, precio);
                cards.add(card);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public static List<Card> searchCardByPrizeDesc(String cardName){
        List<Card> selectedCard = new ArrayList<>();
        String query = "SELECT \n" +
                "\tc.ID_CARD,\n" +
                "\tc.NOMBRE,\n" +
                "    c.MANA_COST,\n" +
                "    c.CMC,\n" +
                "    c.COLOR,\n" +
                "    c.COLOR_IDENTITY,\n" +
                "    c.PODER,\n" +
                "    c.RESISTENCIA,\n" +
                "    t.ID_TYPE,\n" +
                "    t.TYPE_NAME,\n" +
                "    r.ID_RAREZA,\n" +
                "    r.NOMBRE_RAREZA,\n" +
                "    s.ID_SET,\n" +
                "    s.SET_NAME,\n" +
                "    c.PRECIO\n" +
                "    FROM t_cards as c INNER JOIN t_type as t\n" +
                "\t\t\t\t\tON c.TIPO=t.ID_TYPE\n" +
                "                    INNER JOIN t_rareza as r\n" +
                "\t\t\t\t\t\tON c.RAREZA=r.ID_RAREZA\n" +
                "\t\t\t\t\t\t\tINNER JOIN t_set as s\n" +
                "\t\t\t\t\t\t\t\tON c.CARD_SET=s.ID_SET\n" +
                "\tWHERE c.NOMBRE = ?\n" +
                "\tORDER BY c.PRECIO DESC;";
        PreparedStatement ps;
        try {
            ps = crearConexion().prepareStatement(query);
            ps.setString(1, cardName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID_CARD");
                String nombre = rs.getString("NOMBRE");
                String manaCost = rs.getString("MANA_COST");
                int cmc = rs.getInt("CMC");
                String color = rs.getString("COLOR");
                String colorIdentity = rs.getString("COLOR_IDENTITY");
                int poder = rs.getInt("PODER");
                int resistencia = rs.getInt("RESISTENCIA");
                Type tipo = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE_NAME"));
                Rarity rareza = new Rarity(rs.getInt("ID_RAREZA"), rs.getString("NOMBRE_RAREZA"));
                Set cardSet = new Set(rs.getInt("ID_SET"), rs.getString("SET_NAME"));
                float precio = rs.getFloat("PRECIO");
                Card card = new Card(id, nombre, manaCost, cmc, color, colorIdentity, poder, resistencia, tipo, rareza, cardSet, precio);
                selectedCard.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedCard;
    }

    public static List<Card> searchCardsBySet(int setID){
        List<Card> selectedCards = new ArrayList<>();
        String query = "SELECT \n" +
                "\tc.ID_CARD,\n" +
                "\tc.NOMBRE,\n" +
                "    c.MANA_COST,\n" +
                "    c.CMC,\n" +
                "    c.COLOR,\n" +
                "    c.COLOR_IDENTITY,\n" +
                "    c.PODER,\n" +
                "    c.RESISTENCIA,\n" +
                "    t.ID_TYPE,\n" +
                "    t.TYPE_NAME,\n" +
                "    r.ID_RAREZA,\n" +
                "    r.NOMBRE_RAREZA,\n" +
                "    s.ID_SET,\n" +
                "    s.SET_NAME,\n" +
                "    c.PRECIO\n" +
                "    FROM t_cards as c INNER JOIN t_type as t\n" +
                "\t\t\t\t\tON c.TIPO=t.ID_TYPE\n" +
                "                    INNER JOIN t_rareza as r\n" +
                "\t\t\t\t\t\tON c.RAREZA=r.ID_RAREZA\n" +
                "\t\t\t\t\t\t\tINNER JOIN t_set as s\n" +
                "\t\t\t\t\t\t\t\tON c.CARD_SET=s.ID_SET\n" +
                "\tWHERE s.ID_SET = ?\n" +
                "\tORDER BY c.PRECIO DESC;";
        PreparedStatement ps;
        try {
            ps = crearConexion().prepareStatement(query);
            ps.setInt(1, setID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID_CARD");
                String nombre = rs.getString("NOMBRE");
                String manaCost = rs.getString("MANA_COST");
                int cmc = rs.getInt("CMC");
                String color = rs.getString("COLOR");
                String colorIdentity = rs.getString("COLOR_IDENTITY");
                int poder = rs.getInt("PODER");
                int resistencia = rs.getInt("RESISTENCIA");
                Type tipo = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE_NAME"));
                Rarity rareza = new Rarity(rs.getInt("ID_RAREZA"), rs.getString("NOMBRE_RAREZA"));
                Set cardSet = new Set(rs.getInt("ID_SET"), rs.getString("SET_NAME"));
                float precio = rs.getFloat("PRECIO");
                Card card = new Card(id, nombre, manaCost, cmc, color, colorIdentity, poder, resistencia, tipo, rareza, cardSet, precio);
                selectedCards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedCards;
    }

    public static List<Card> searchCardsBySetAndName(int setID, String cardName){
        List<Card> selectedCards = new ArrayList<>();
        String query = "SELECT \n" +
                "\tc.ID_CARD,\n" +
                "\tc.NOMBRE,\n" +
                "    c.MANA_COST,\n" +
                "    c.CMC,\n" +
                "    c.COLOR,\n" +
                "    c.COLOR_IDENTITY,\n" +
                "    c.PODER,\n" +
                "    c.RESISTENCIA,\n" +
                "    t.ID_TYPE,\n" +
                "    t.TYPE_NAME,\n" +
                "    r.ID_RAREZA,\n" +
                "    r.NOMBRE_RAREZA,\n" +
                "    s.ID_SET,\n" +
                "    s.SET_NAME,\n" +
                "    c.PRECIO\n" +
                "    FROM t_cards as c INNER JOIN t_type as t\n" +
                "\t\t\t\t\tON c.TIPO=t.ID_TYPE\n" +
                "                    INNER JOIN t_rareza as r\n" +
                "\t\t\t\t\t\tON c.RAREZA=r.ID_RAREZA\n" +
                "\t\t\t\t\t\t\tINNER JOIN t_set as s\n" +
                "\t\t\t\t\t\t\t\tON c.CARD_SET=s.ID_SET\n" +
                "\tWHERE s.ID_SET = ? AND c.NOMBRE = ?\n" +
                "\tORDER BY c.PRECIO DESC;";
        PreparedStatement ps;
        try {
            ps = crearConexion().prepareStatement(query);
            ps.setInt(1, setID);
            ps.setString(2, cardName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("ID_CARD");
                String nombre = rs.getString("NOMBRE");
                String manaCost = rs.getString("MANA_COST");
                int cmc = rs.getInt("CMC");
                String color = rs.getString("COLOR");
                String colorIdentity = rs.getString("COLOR_IDENTITY");
                int poder = rs.getInt("PODER");
                int resistencia = rs.getInt("RESISTENCIA");
                Type tipo = new Type(rs.getInt("ID_TYPE"), rs.getString("TYPE_NAME"));
                Rarity rareza = new Rarity(rs.getInt("ID_RAREZA"), rs.getString("NOMBRE_RAREZA"));
                Set cardSet = new Set(rs.getInt("ID_SET"), rs.getString("SET_NAME"));
                float precio = rs.getFloat("PRECIO");
                Card card = new Card(id, nombre, manaCost, cmc, color, colorIdentity, poder, resistencia, tipo, rareza, cardSet, precio);
                selectedCards.add(card);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedCards;
    }

    public static List<String> getNombreCartas() {
        List<String> cartas = new ArrayList<>();
        String query = "SELECT NOMBRE FROM T_CARDS";
        try (Connection con = crearConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                cartas.add(rs.getString("NOMBRE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartas;
    }
}
