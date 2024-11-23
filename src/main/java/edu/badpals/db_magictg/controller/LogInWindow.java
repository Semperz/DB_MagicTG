package edu.badpals.db_magictg.controller;

import edu.badpals.db_magictg.conexion.Connect;
//import edu.badpals.db_magictg.db.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LogInWindow {

    @FXML
    private TextField Txt_User; // Campo para el nombre de usuario
    @FXML
    private TextField Txt_Password; // Campo para la contraseña
    @FXML
    private Button Btn_LogIn; // Botón para iniciar sesión

    // Acción para iniciar sesión
    @FXML
    public void iniciarSesion(ActionEvent event) throws SQLException, IOException {
        String user = Txt_User.getText();
        String password = Txt_Password.getText();

        // Validación de credenciales desde la base de datos
        if (validarCredenciales(user, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Exitoso", "Bienvenido, " + user + "!");
            toMainWindow(event);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Fallido", "Usuario o contraseña incorrectos.");
        }
    }

    // Metodo de validación con base de datos
    private boolean validarCredenciales(String user, String password) throws SQLException {
        // Conectar a la base de datos
        Connection connection = Connect.crearConexionUsers();

        // Consulta SQL para verificar si el usuario y la contraseña existen
        String sql = "SELECT PASSWORD_USUARIO FROM T_LOGIN WHERE NOMBRE_USUARIO = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Aquí puedes usar un hash para verificar la contraseña, por ejemplo, usando BCrypt
                String storedPassword = rs.getString("PASSWORD_USUARIO");

                // Comparar la contraseña proporcionada con la almacenada (sin hashing en este ejemplo)
                return password.equals(storedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
