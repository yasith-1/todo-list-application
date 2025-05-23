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
import javafx.scene.input.MouseEvent;
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
            Notifications.create().title("Warning").text("Enter an username..").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else if (passwordField.getText().isEmpty()) {
            Notifications.create().title("Warning").text("Enter a Strong password..").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else {
            try {
                Boolean isExist = SignupController.getInstance().notifyAlreadyUserExist(usernameField.getText());
                if (isExist) {
//                    user exist
                    Notifications.create().title("Warning").text("User Already Registerd..").position(Pos.BOTTOM_RIGHT).showInformation();
                } else {
//                     user not exist
                    User user = new User(idField.getText(),usernameField.getText(),passwordField.getText());
                    Boolean isAdded = SignupController.getInstance().addUser(user);
                    if (isAdded){
                        Notifications.create().title("Sucess").text("User Registerd Sucessfully !").position(Pos.BOTTOM_RIGHT).showInformation();
                    }else {
                        Notifications.create().title("Warning").text("User Registration fail , try again !").position(Pos.BOTTOM_RIGHT).showInformation();
                        return;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }


    public void backToLoginText(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/user-login.fxml"))));
        stage.setTitle("Login");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.show();
    }

    private void setUserId(){
        String userId = SignupController.getInstance().generateuserId();
        idField.setText(userId);
    }

}
