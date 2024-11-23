package edu.badpals.db_magictg.conexion;

import edu.badpals.db_magictg.model.Card;

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
                "    t.TYPE_NAME,\n" +
                "    r.NOMBRE_RAREZA,\n" +
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
                int tipo = rs.getInt("TIPO");
                int rareza = rs.getInt("RAREZA");
                int cardSet = rs.getInt("CARD_SET");
                float precio = rs.getFloat("PRECIO");
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public static void eliminarCartaPorId(int cardId) {
        String query = "DELETE FROM t_cards WHERE ID_CARD = ?";
        try (Connection connection = crearConexion()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cardId);
            ps.executeUpdate();
            System.out.println("Carta con ID " + cardId + " eliminada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo para obtener el nombre de la carta por su ID
    public static String obtenerNombrePorId(int cardId) {
        String query = "SELECT NOMBRE FROM t_cards WHERE ID_CARD = ?";
        try (Connection connection = crearConexion()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("NOMBRE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra la carta con ese ID
    }
}
