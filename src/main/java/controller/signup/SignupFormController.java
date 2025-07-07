package controller.signup;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.User;
import org.controlsfx.control.Notifications;
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
            Notifications.create().title("Warning").text("Enter a username.").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else if (passwordField.getText().isEmpty()) {
            Notifications.create().title("Warning").text("Enter a strong password.").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else {
            try {
                boolean isExist = SignupController.getInstance().notifyAlreadyUserExist(usernameField.getText());
                if (isExist) {
                    Notifications.create().title("Warning").text("User already registered.").position(Pos.BOTTOM_RIGHT).showInformation();
                } else {
                    User user = new User(idField.getText(), usernameField.getText(), passwordField.getText());
                    boolean isAdded = SignupController.getInstance().addUser(user);
                    if (isAdded) {
                        Notifications.create().title("Success").text("User registered successfully!").position(Pos.BOTTOM_RIGHT).showInformation();

                        // Go to login window-------------------------------------------------------------------------
                        gotoLoginWindowOnActionBtn( event);
                    } else {
                        Notifications.create().title("Error").text("User registration failed. Try again.").position(Pos.BOTTOM_RIGHT).showError();
                    }
                }
            } catch (Exception e) {
                Notifications.create().title("Exception").text("Something went wrong: " + e.getMessage()).position(Pos.BOTTOM_RIGHT).showError();
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
