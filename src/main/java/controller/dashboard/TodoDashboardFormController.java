package controller.dashboard;

import com.jfoenix.controls.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Task;
import org.controlsfx.control.Notifications;
import util.TaskStatus;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TodoDashboardFormController implements Initializable {

    public Label txtLogoName;
    public DatePicker txtDate;
    public JFXListView todoListView;
    public JFXListView doneListView;
    public JFXTextField txtTaskDescriptionField;
    public JFXTextField txtTaskNameField;
    @FXML
    private Label dateTimeLabel;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm:ss");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserName();
        loadDashBoard();
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
        } else if (txtDate.getValue() == null) {
            Notifications.create().title("Warning").text("Select the Task Due Date...").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else if (txtDate.getValue().isBefore(LocalDate.now())) {
            Notifications.create().title("Warning").text("Select Valid date...").position(Pos.BOTTOM_RIGHT).showInformation();
            return;
        } else {
            Task task = new Task(
                    null,
                    txtTaskNameField.getText(),
                    txtTaskDescriptionField.getText().isEmpty() ? "null" : txtTaskDescriptionField.getText(),
                    Date.valueOf(txtDate.getValue()),
                    Time.valueOf(LocalTime.now()),
                    TaskStatus.PENDING,
                    null);
            Boolean isAdded = TodoDashboardController.getInstance().saveTaskDatabase(task);
            if (isAdded) {
                Notifications.create().title("Success").text("Task added Successfully !").position(Pos.BOTTOM_RIGHT).showInformation();
                clearTextField();
                loadDashBoard();
            } else {
                Notifications.create().title("Warning").text("Task not added").position(Pos.BOTTOM_RIGHT).showInformation();
            }
        }
    }

    private void setUserName() {
        String loggedUserName = TodoDashboardController.getInstance().getLoggedUserName();
        txtLogoName.setText(loggedUserName);
    }

    //    See all task button---------------------------------------------------------------------------------
    public void seeAllTaskOnActionButton(ActionEvent actionEvent) {
    }


    public void logOutOnActionButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/user-login.fxml"))));
        stage.setTitle("Login");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.show();
    }

    //    Load task method---------------------------------------------------------------------------------------
    private void loadtask() {
        try {
            ArrayList<Task> taskArrayList = TodoDashboardController.getInstance().loadTask();

            if (taskArrayList == null || taskArrayList.isEmpty()) {
                Notifications.create().title("Information").
                        text("No tasks to display !").
                        position(Pos.BOTTOM_RIGHT).
                        hideAfter(Duration.seconds(5)).
                        showWarning();
                return;
            } else {
                for (Task task : taskArrayList) {

                    // Main container - VBox for vertical layout
                    VBox vBox = new VBox();
                    vBox.setSpacing(8);
                    vBox.setStyle("-fx-background-color: linear-gradient(to right, #A1C4FD, #C2E9FB); " +
                            "-fx-border-color: #4A90E2; " +
                            "-fx-border-width: 2px; " +
                            "-fx-border-radius: 10; " +
                            "-fx-background-radius: 10; " +
                            "-fx-padding: 15; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 5, 0, 0, 2);");

                    // Task name
                    Label taskNameLabel = new Label("Task : " + task.getName());
                    taskNameLabel.setStyle("-fx-font-size: 16px; " +
                            "-fx-font-family: 'Poppins'; " +
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: #333333; " +
                            "-fx-wrap-text: true;"); // Enable text wrapping for long task names

                    // HBOX  - Date and Checkbox in HBox
                    HBox hBox = new HBox();
                    hBox.setSpacing(15);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    String taskDate = task.getDate().toString();
                    if (taskDate != null && !taskDate.isEmpty()) {
                        Label dateLabel = new Label("Date : " + taskDate);
                        dateLabel.setStyle("-fx-font-size: 14px; " + "-fx-text-fill: #333333;");
                        hBox.getChildren().add(dateLabel);
                    }

                    // Task time
                    Label taskTimeLabel = new Label("Task time : " + task.getTime());
                    taskTimeLabel.setStyle("-fx-font-size: 14px; " +
                            "-fx-font-weight: regular; " +
                            "-fx-text-fill: #333333; " +
                            "-fx-wrap-text: true;"); // Enable text wrapping for long task names

                    CheckBox checkBox = new CheckBox("Completed");
                    checkBox.setStyle("-fx-font-size: 14px; " + "-fx-text-fill: #444444; " + "-fx-cursor: hand;");
                    hBox.getChildren().add(checkBox);

                    // Add task description if available (optional third row)
                    if (task.getDescription() != null && !task.getDescription().equals("null") && !task.getDescription().isEmpty()) {
                        Label descriptionLabel = new Label("Description : " + task.getDescription());
                        descriptionLabel.setStyle("-fx-font-size: 12px; " + "-fx-text-fill: #666666; " + "-fx-wrap-text: true; " + "-fx-font-style: italic;");
                        vBox.getChildren().addAll(taskNameLabel, taskTimeLabel, hBox, descriptionLabel);
                    } else {
                        vBox.getChildren().addAll(taskNameLabel, taskTimeLabel, hBox);
                    }

                    // Delete Button
                    Button deleteButton = new Button("Delete");
                    deleteButton.setStyle(
                            "-fx-background-color: #e74c3c; " +
                                    "-fx-text-fill: white; " +
                                    "-fx-font-size: 13px; " +
                                    "-fx-font-weight: bold; " +
                                    "-fx-padding: 8 18 8 18; " +
                                    "-fx-background-radius: 20; " +
                                    "-fx-cursor: hand;"
                    );
                    deleteButton.setOnMouseEntered(e -> {
                        deleteButton.setStyle(
                                "-fx-background-color: #c0392b; " +
                                        "-fx-text-fill: white; " +
                                        "-fx-font-size: 13px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-padding: 8 18 8 18; " +
                                        "-fx-background-radius: 20; " +
                                        "-fx-cursor: hand;" +
                                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
                        );
                    });
                    deleteButton.setOnMouseExited(e -> {
                        deleteButton.setStyle(
                                "-fx-background-color: #e74c3c; " +
                                        "-fx-text-fill: white; " +
                                        "-fx-font-size: 13px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-padding: 8 18 8 18; " +
                                        "-fx-background-radius: 20; " +
                                        "-fx-cursor: hand;"
                        );
                    });

                    // Add Delete button
                    vBox.getChildren().add(deleteButton);
                    todoListView.getItems().add(vBox);


                    // Checkbox action logic
                    checkBox.setOnAction(actionEvent -> {
                        if (checkBox.isSelected()) {
                            try {
                                Boolean isCompTaskAdded = TodoDashboardController.getInstance().addCompletedTask(
                                        task.getId(),
                                        task.getUserId(),
                                        Date.valueOf(LocalDate.now()),
                                        Time.valueOf(LocalTime.now()));

                                if (isCompTaskAdded) {
//                                    Boolean isTaskRemoved = DashboardController.getInstance().removeTask(task.getId(), task.getUserId());

                                    loadDashBoard();
                                    todoListView.getItems().remove(vBox);
                                    Notifications.create().title("Marked").
                                            text("Task completed !").
                                            position(Pos.BOTTOM_RIGHT).
                                            hideAfter(Duration.seconds(5)).
                                            showInformation();

                                } else {
                                    Notifications.create().title("Marked").
                                            text("Task not completed !").
                                            position(Pos.BOTTOM_RIGHT).
                                            hideAfter(Duration.seconds(5)).
                                            showInformation();
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

//                    Delete logic
                    deleteButton.setOnAction(evt -> {
                        try {
                            Boolean isDeleted = TodoDashboardController.getInstance().removeTask(task.getId(), task.getUserId());
                            if (isDeleted) {
                                todoListView.getItems().remove(vBox);
                                loadDashBoard();
                                Notifications.create().title("Deleted").text("Task deleted successfully !").position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showInformation();
                            } else {
                                Notifications.create().title("Failed Delete").text("Failed to delete task try again !").position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showError();
                                return;
                            }
                        } catch (Exception e) {
                            Notifications.create().title("Failed").text("Operation Failed try again !" + e.getMessage()).position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showError();
                        }
                    });
                }

                Notifications.create().title("Loaded Tasks").text("Task Loaded successfully !").position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showInformation();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading tasks: " + e.getMessage()).hide();
        }
    }

    //    Load completed task method---------------------------------------------------------------------------------------
    private void loadCompletedTask() {
        try {
            doneListView.getItems().clear(); // Clear old list

            ArrayList<Task> compTaskArrayList = TodoDashboardController.getInstance().loadCompletedTask();

            if (compTaskArrayList == null || compTaskArrayList.isEmpty()) {
                Notifications.create()
                        .title("Information")
                        .text("No completed task to display!")
                        .position(Pos.BOTTOM_RIGHT)
                        .hideAfter(Duration.seconds(5))
                        .showWarning();
                return;
            }

            for (Task compTask : compTaskArrayList) {
                VBox vBox = new VBox();
                vBox.setSpacing(8);
                vBox.setStyle("-fx-background-color: linear-gradient(to right, #FAD0C4, #FFD1DC); " +
                        "-fx-border-color: #FF6F91; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 5, 0, 0, 2);");

                Label taskNameLabel = new Label("Task : " + compTask.getName());
                taskNameLabel.setStyle("-fx-font-size: 16px; " +
                        "-fx-font-family: 'Poppins'; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: #333333; " +
                        "-fx-wrap-text: true;");

                HBox hBox = new HBox();
                hBox.setSpacing(15);
                hBox.setAlignment(Pos.CENTER_LEFT);

                if (compTask.getDate() != null) {
                    Label dateLabel = new Label("Date : " + compTask.getDate().toString());
                    dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
                    hBox.getChildren().add(dateLabel);
                }

                Label statusLabel = new Label("Task Status : COMPLETED");
                statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
                hBox.getChildren().add(statusLabel);

                Label taskTimeLabel = new Label("Task time : " + compTask.getTime());
                taskTimeLabel.setStyle("-fx-font-size: 14px; " +
                        "-fx-font-weight: normal; " +
                        "-fx-text-fill: #333333; " +
                        "-fx-wrap-text: true;");

                if (compTask.getDescription() != null && !compTask.getDescription().equalsIgnoreCase("null") && !compTask.getDescription().isEmpty()) {
                    Label descriptionLabel = new Label("Description: " + compTask.getDescription());
                    descriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666; -fx-wrap-text: true; -fx-font-style: italic;");
                    vBox.getChildren().addAll(taskNameLabel, hBox, taskTimeLabel, descriptionLabel);
                } else {
                    vBox.getChildren().addAll(taskNameLabel, hBox, taskTimeLabel);
                }

                // HBox for buttons
                HBox buttonBox = new HBox();
                buttonBox.setSpacing(10);
                buttonBox.setPadding(new Insets(10, 0, 0, 0));

                // Delete Button
                Button deleteButton = new Button("Delete");
                deleteButton.setStyle("-fx-background-color: #e74c3c; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 18 8 18; " +
                        "-fx-background-radius: 20; " +
                        "-fx-cursor: hand;");
                deleteButton.setOnMouseEntered(e -> deleteButton.setStyle(
                        "-fx-background-color: #c0392b; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 18 8 18; " +
                                "-fx-background-radius: 20; " +
                                "-fx-cursor: hand;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"));
                deleteButton.setOnMouseExited(e -> deleteButton.setStyle(
                        "-fx-background-color: #e74c3c; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 18 8 18; " +
                                "-fx-background-radius: 20; " +
                                "-fx-cursor: hand;"));

                // Mark as Pending Button
                Button markPendingButton = new Button("Mark as Pending");
                markPendingButton.setStyle("-fx-background-color: #2ecc71; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 18 8 18; " +
                        "-fx-background-radius: 20; " +
                        "-fx-cursor: hand;");
                markPendingButton.setOnMouseEntered(e -> markPendingButton.setStyle(
                        "-fx-background-color: #27ae60; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 18 8 18; " +
                                "-fx-background-radius: 20; " +
                                "-fx-cursor: hand;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"));
                markPendingButton.setOnMouseExited(e -> markPendingButton.setStyle(
                        "-fx-background-color: #2ecc71; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 13px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 8 18 8 18; " +
                                "-fx-background-radius: 20; " +
                                "-fx-cursor: hand;"));

                // Delete Logic
                deleteButton.setOnAction(evt -> {
                    try {
                        deleteButton.setDisable(true);
                        Boolean isDeleted = TodoDashboardController.getInstance().removeTask(compTask.getId(), compTask.getUserId());
                        if (isDeleted) {
                            doneListView.getItems().remove(vBox);
                            loadDashBoard();
                            Notifications.create().title("Success").text("Completed Task deleted successfully!").position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showInformation();
                        } else {
                            deleteButton.setDisable(false);
                            Notifications.create().title("Error").text("Failed to delete completed task!").position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showError();
                        }
                    } catch (Exception e) {
                        deleteButton.setDisable(false);
                        Notifications.create().title("Error").text("An error occurred: " + e.getMessage()).position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showError();
                    }
                });

                // Mark as Pending Logic (you must implement markTaskAsPending in your controller)
                markPendingButton.setOnAction(evt -> {
                    try {
                        boolean updated = TodoDashboardController.getInstance().markTaskAsPending(compTask.getId(),compTask.getUserId());
                        if (updated) {
                            doneListView.getItems().remove(vBox);
                            loadDashBoard();
                            Notifications.create().title("Task Updated").text("Task marked as pending!").position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showInformation();
                        } else {
                            Notifications.create().title("Error").text("Failed to update task!").position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showError();
                        }
                    } catch (Exception e) {
                        Notifications.create().title("Error").text("An error occurred: " + e.getMessage()).position(Pos.BOTTOM_RIGHT).hideAfter(Duration.seconds(5)).showError();
                    }
                });

                buttonBox.getChildren().addAll(deleteButton, markPendingButton);
                vBox.getChildren().add(buttonBox);
                doneListView.getItems().add(vBox);
            }

            Notifications.create()
                    .title("Loaded Tasks")
                    .text("Completed tasks loaded successfully!")
                    .position(Pos.BOTTOM_RIGHT)
                    .hideAfter(Duration.seconds(5))
                    .showInformation();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading Completed tasks: " + e.getMessage()).hide();
        }
    }


    public void loadTaskOnActionButton(ActionEvent actionEvent) {
        loadDashBoard();
    }

    private void clearTextField() {
        txtTaskNameField.setText("");
        txtTaskDescriptionField.setText("");
        txtDate.setValue(null);
    }

    private void loadDashBoard() {
        todoListView.getItems().clear();
        doneListView.getItems().clear();
        loadtask();
        loadCompletedTask();
    }
}
