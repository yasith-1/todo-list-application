package controller.signup;

import alert.Alert;
import alert.AlertType;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupFormController implements Initializable {

    @FXML
    public JFXTextField idField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXTextField usernameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserId();
    }

    @FXML
    void RegisterOnActionButton(ActionEvent event) {
        if (usernameField.getText().isEmpty()) {
            Alert.trigger( AlertType.WARNING, "Enter a username.");
            return;
        } else if (passwordField.getText().isEmpty()) {
            Alert.trigger( AlertType.WARNING, "Enter a strong password.");
            return;
        } else {
            try {
                boolean isExist = SignupController.getInstance().notifyAlreadyUserExist(usernameField.getText());
                if (isExist) {
                    Alert.trigger( AlertType.WARNING, "User already registered. Please login");
                } else {
                    User user = new User(idField.getText(), usernameField.getText(), passwordField.getText());
                    boolean isAdded = SignupController.getInstance().addUser(user);
                    if (isAdded) {
                        Alert.trigger( AlertType.INFORMATION, "User registered successfully!");

                        // Go to login window-------------------------------------------------------------------------
                        gotoLoginWindowOnActionBtn( event);
                    } else {
                        Alert.trigger( AlertType.ERROR, "User registration failed. Try again.");
                    }
                }
            } catch (Exception e) {
                Alert.trigger( AlertType.ERROR, "Something went wrong: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void setUserId() {
        String userId = SignupController.getInstance().generateuserId();
        idField.setText(userId);
    }

    @FXML
    public void gotoLoginWindowOnActionBtn(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/user-login.fxml")));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.show();
    }
}
