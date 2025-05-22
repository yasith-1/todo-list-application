package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    public Label txtLogoName;
    public DatePicker txtDate;
    public JFXListView todoListView;
    public JFXListView doneListView;
    public JFXTextField txtNameField;
    public JFXTextField txtDescriptionField;
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
    public void addTaskOnActionButton(ActionEvent actionEvent) {
        System.out.println("Hello yaluweeeeeee");
    }
}
