package controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import database.DBconnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            return;
        } else if (txtDate.getValue() == null) {
            Notifications.create().title("Warning").text("Select the Task Due Date...").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else if (txtDate.getValue().isBefore(LocalDate.now())) {
            Notifications.create().title("Warning").text("Select Valid date...").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else {
            if (saveTaskDatabase()) {
                Notifications.create().title("Sucess").text("Task added Sucessfully").position(Pos.BOTTOM_RIGHT).showInformation();
            } else {
                Notifications.create().title("Warning").text("Error").position(Pos.BOTTOM_RIGHT).showInformation();
                return;
            }
        }
    }

    private Boolean saveTaskDatabase() {
        try {
            Connection connection = DBconnection.getInstance().getConnection();
            String sql = "INSERT INTO `task` (`task_id`,`name`,`description`,`date`,`time`,`status_id`) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (genarateTaskId() != null) {
                ps.setString(1, genarateTaskId());
                ps.setString(2, txtTaskNameField.getText());
                ps.setString(3, txtTaskDescriptionField.getText());
                ps.setString(4, String.valueOf(txtDate.getValue()));
                ps.setString(5, String.valueOf(txtDate.getValue()));
                ps.setInt(6, 1);

                if (ps.executeUpdate() > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                Notifications.create().title("Warning").text("Task not added ,something went wrong").position(Pos.BOTTOM_RIGHT).showInformation();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String genarateTaskId() {
        try {
            ResultSet rst = DBconnection.getInstance().getConnection().createStatement().executeQuery("SELECT `task_id` FROM `task` ORDER BY `task_id` DESC LIMIT 1");
            if (rst.next()) {
                String existId = rst.getString("task_id");
                return String.format("T%03d", Integer.parseInt(existId.substring(1)) + 1);
            } else {
                return "T001"; // First ID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    See all task button---------------------------------------------------------------------------------
    public void seeAllTaskOnActionButton(ActionEvent actionEvent) {
    }
}
