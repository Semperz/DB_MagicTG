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

public class Connect {
    static String urldb = "jdbc:mysql://localhost:3306/db_magictg";
    public static Connection crearConexion() throws SQLException {
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("user", "root");
        propiedadesConexion.setProperty("password", "root");
        return DriverManager.getConnection(urldb, propiedadesConexion);
    }

    public static Connection crearConexionUsers() throws SQLException {
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("user", "root");
        propiedadesConexion.setProperty("password", "root");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/db_login_magictg", propiedadesConexion);
    }

    public void cerrarConexion(java.sql.Connection conexion) throws SQLException {
        conexion.close();
    }

    public static String listarCartasExport(Connect connection){
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

    private static List<Card> searchCardByName(Connect connection, String cardName){
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
                "\tORDER BY c.ID_CARD;";
        PreparedStatement ps;
        try {
            ps = crearConexion().prepareStatement(query);
            ps.setString(1, cardName);
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
}
