package controller.login;

import alert.Alert;
import alert.AlertType;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginFormController {


    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    void LoginOnActionButton(ActionEvent actionEvent) throws IOException {
        if (usernameField.getText().isEmpty()) {
            Alert.trigger(AlertType.WARNING, "Enter a username.");
            return;
        } else if (passwordField.getText().isEmpty()) {
            Alert.trigger( AlertType.WARNING, "Enter a strong password.");
            return;
        } else {

            Boolean isAvailable = LoginController.getInstance().userAuthentication(usernameField.getText(), passwordField.getText());
            if (isAvailable) {
//                User Available (allow access to login)
                Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                loginStage.close();

                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/todo-dashboard.fxml"))));
                stage.setTitle("Dashboard");
                stage.getIcons().add(new Image("/images/icon.png"));
                stage.show();
            } else {
//                user not found(decline access to login)
                Alert.trigger( AlertType.WARNING, "User Not Found , Please Create an account !");
            }
        }
    }

    @FXML
    void signupOnActionButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/user-signup.fxml"))));
        stage.setTitle("Register");
        stage.setResizable(false);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.show();
    }
}
