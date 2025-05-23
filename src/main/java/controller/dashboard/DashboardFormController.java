package controller.dashboard;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;
import model.Task;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    public Label txtLogoName;
    public DatePicker txtDate;
    public JFXListView todoListView;
    public JFXListView doneListView;
    public JFXTextField txtTaskDescriptionField;
    public JFXTextField txtTaskNameField;
    @FXML
    private Label dateTimeLabel;

    String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm:ss");

    //    Date and time----------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserName();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDateTime()));
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
            Task task = new Task(null, txtTaskNameField.getText(), txtTaskDescriptionField.getText(), String.valueOf(txtDate.getValue()), currentTime, 1, null);
            Boolean isAdded = DashboardController.getInstance().saveTaskDatabase(task);
            if (isAdded) {
                Notifications.create().title("Sucess").text("Task added Sucessfully").position(Pos.BOTTOM_RIGHT).showInformation();
            } else {
                Notifications.create().title("Warning").text("Error").position(Pos.BOTTOM_RIGHT).showInformation();
                return;
            }
        }
    }


    private void loadTask() {
//        ArrayList<Task> todoListArrayList = new ArrayList<>();
//        try {
//            ResultSet rst = DBconnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM task WHERE user_id='" + userId + "'");
//            while (rst.next()) {
//                todoListArrayList.add(new ToDoList(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4)));
//            }
//            return todoListArrayList;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }


    private void setUserName() {
        String loggedUserName = DashboardController.getInstance().getLoggedUserName();
        txtLogoName.setText(loggedUserName);
    }

    //    See all task button---------------------------------------------------------------------------------
    public void seeAllTaskOnActionButton(ActionEvent actionEvent) {
    }


}
