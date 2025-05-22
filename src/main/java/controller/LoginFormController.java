package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.DBconnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.ResultSet;

public class LoginFormController {


    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXTextField usernameField;

    @FXML
    void LoginOnActionButton(ActionEvent event) throws IOException {
        if (usernameField.getText().isEmpty()) {
            Notifications.create().title("Warning").text("Missing username").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else if (passwordField.getText().isEmpty()) {
            Notifications.create().title("Warning").text("Missing password").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else {

            if (LoginController.getInstance().userAuthentication(usernameField.getText(), passwordField.getText())) {
//                User Available (allow access to login)
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/todo-dashboard.fxml"))));
                stage.setTitle("Dashboard");
                stage.getIcons().add(new Image("/images/icon.png"));
                stage.show();
            } else {
//                user not found(decline access to login)
                Notifications.create().title("Warning").text("User Not Found , Please Create an account !").position(Pos.BOTTOM_RIGHT).showInformation();
            }
        }
    }


    @FXML
    void signupOnActionButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/user-signup.fxml"))));
        stage.setTitle("Register");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.show();
    }


}
