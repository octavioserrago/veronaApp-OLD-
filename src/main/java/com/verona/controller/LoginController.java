package com.verona.controller;

import java.sql.SQLException;

import com.verona.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField loginField;

    @FXML
    private Label errorLoginLabel;

    @FXML
    private PasswordField passwordField;

    private SceneController sceneController;

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    void initialize() {

    }

    public void reinicializarLoginController() {
        loginField.setText("");
        passwordField.setText("");
        errorLoginLabel.setVisible(false);
    }

    @FXML
    void btnLoginClick(ActionEvent event) throws SQLException {
        System.out.println("Before Login Attempt");
        String userName = loginField.getText();
        String password = passwordField.getText();
        int sucursalID = 0;

        User user = new User(userName, password, "", "", sucursalID, 0);

        if (user.login(userName, password)) {
            int roleID = user.getRoleID();
            int userID = user.getUserID();
            sucursalID = user.getSucursalID();

            user.setUserID(userID);

            User.setCurrentUser(user);

            switch (roleID) {
                case 1:
                    if (sceneController != null) {
                        sceneController.switchToManagerDashboard();
                    }
                    break;

                case 2:
                    if (sceneController != null) {
                        sceneController.switchToDashboardSeller();
                    }
                    break;
                case 3:
                    if (sceneController != null) {
                        sceneController.switchToAdminDashboard();
                    }
                    break;

                default:
                    System.out.println("Error Relacionado al ROL");
                    break;
            }

        } else {
            errorLoginLabel.setVisible(true);
            System.out.println("Error en el inicio de sesión. Usuario o contraseña incorrectos.");
        }
    }

}