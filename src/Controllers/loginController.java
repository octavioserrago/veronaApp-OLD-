package Controllers;


import Data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class loginController {

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
    void btnLoginClick(ActionEvent event) {
        String userName = loginField.getText();
        String password = passwordField.getText();

        User user = new User(userName, password);

        if (user.login(userName, password)) {
            System.out.println("Exito al iniciar Sesion!");

            int roleID = user.getRoleID();

            
            switch (roleID) {
                case 1:
                    System.out.println("Pagina de administrador");
                    break;

                case 2:
                    
                if (sceneController != null) {
                  sceneController.switchToDashboardSeller();
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
