package edu.badpals.db_magictg.conexion;

import java.sql.*;
import java.util.Properties;

public class Connect {
    String urldb = "jdbc:mysql://localhost:3306/paises_db";
    public Connection crearConexion() throws SQLException {
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("user", "root");
        propiedadesConexion.setProperty("password", "root");
        return DriverManager.getConnection(urldb, propiedadesConexion);
    }

    public void cerrarConexion(java.sql.Connection conexion) throws SQLException {
        conexion.close();
    }

    public static String listarCartasExport(Connect connection){
        String query = "SELECT * FROM t_cards";
        StringBuilder result = new StringBuilder();
        try {
            Statement statement = connection.crearConexion().createStatement();
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

    public static
}
