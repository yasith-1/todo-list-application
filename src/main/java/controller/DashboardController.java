package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    public Label txtLogoName;
    public DatePicker txtDate;
    public JFXListView todoListView;
    public JFXListView doneListView;
    public JFXTextField txtTaskDescriptionField;
    public JFXTextField txtTaskNameField;
    @FXML
    private Label dateTimeLabel;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm:ss");

    //    Date and time----------------------------------------------------------------------------------
    public void initialize() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> updateDateTime())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        String formatted = now.format(formatter);
        dateTimeLabel.setText(formatted);
    }

    // --------------------------------------------------------------------------------------------------

    //    Add task button------------------------------------------------------------------------------------
    public void addTaskOnActionButton(ActionEvent actionEvent) {
        if (txtTaskNameField.getText().isEmpty()) {
            Notifications.create().title("Warning").text("Fill the Task Name field...").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else if (txtTaskDescriptionField.getText().isEmpty()) {
            Notifications.create().title("Warning").text("Fill the Task Description field...").position(Pos.BOTTOM_RIGHT).showInformation();
            txtTaskNameField.requestFocus();
            return;
        } else if (txtDate.getValue() == null) {
            Notifications.create().title("Warning").text("Select the Task Due Date...").position(Pos.BOTTOM_RIGHT).showInformation();
            txtTaskNameField.requestFocus();
            return;
        } else if (txtDate.getValue().isBefore(LocalDate.now())) {
            Notifications.create().title("Warning").text("Select Valid date...").position(Pos.BOTTOM_RIGHT).showInformation();
            txtTaskNameField.requestFocus();
            return;
        } else {
            Notifications.create().title("Success").text("Task Added Successfully!").position(Pos.BOTTOM_RIGHT).showInformation();
        }

    }

    //    See all task button---------------------------------------------------------------------------------
    public void seeAllTaskOnActionButton(ActionEvent actionEvent) {
    }
}
