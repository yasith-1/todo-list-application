package controller.dashboard;

import database.DBconnection;
import model.Task;
import util.CrudUtil;
import util.TaskStatus;
import java.sql.*;
import java.util.ArrayList;

public class DashboardController {

    private String username;
    private String userId;

    private static DashboardController instance;

    private DashboardController() {
    }

    public static DashboardController getInstance() {
        if (instance == null) {
            instance = new DashboardController();
        }
        return instance;
    }

    public void setLoggedUserName(String id, String name) {
        this.userId = id;
        this.username = name;
    }

    public String getLoggedUserName() {
        return username;
    }

    public Boolean saveTaskDatabase(Task task) {
        try {
            Boolean result = CrudUtil.execute("INSERT INTO `task` (`task_id`,`name`,`description`,`date`,`time`," +
                            "`status`,`user_id`) VALUES (?, ?, ?, ?, ?, ?,?)",
                    genarateTaskId(),
                    task.getName(),
                    task.getDescription(),
                    task.getDate(),
                    task.getTime(),
                    task.getStatus().name(),
                    userId);

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Task> loadTask() {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        try {
            ResultSet rst= CrudUtil.execute("SELECT * FROM `task` WHERE user_id=? AND  `status`=?",
                    userId, TaskStatus.PENDING.name());
            while (rst.next()) {
                taskArrayList.add(new Task(
                        rst.getString("task_id"),
                        rst.getString("name"),
                        rst.getString("description"),
                        rst.getDate("date"),
                        rst.getTime("time"),
                        TaskStatus.valueOf(rst.getString("status")),
                        rst.getString("user_id")
                ));
            }
            return taskArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Task> loadCompletedTask() {
        ArrayList<Task> compTaskArrayList = new ArrayList<>();
        try {
            ResultSet rst = CrudUtil.execute(
                    "SELECT * FROM `task` WHERE `user_id`=? AND `status`=?",
                    userId,
                    TaskStatus.COMPLETED.name()
            );

            while (rst.next()) {
                compTaskArrayList.add(new Task(
                        rst.getString("task_id"),
                        rst.getString("name"),
                        rst.getString("description"),
                        rst.getDate("date"),
                        rst.getTime("time"),
                        TaskStatus.valueOf(rst.getString("status")),
                        rst.getString("user_id")
                ));
            }

            return compTaskArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean addCompletedTask(String completedTaskId, String userId, Date CompleteDdate, Time CompletedTime) throws SQLException {

        ResultSet rst = CrudUtil.execute("SELECT * FROM `task` WHERE `user_id`= ? AND `task_id`=?", userId, completedTaskId);

        while (rst.next()) {
            String taskId = rst.getString("task_id");
            String name = rst.getString("name");
            String description = rst.getString("description");

            Task completedTask = new Task(taskId, name, description, CompleteDdate, CompletedTime, TaskStatus.COMPLETED, userId);


            return addCompletedTask(completedTask);
        }
        return false;
    }

    public Boolean addCompletedTask(Task compTask) throws SQLException {
        Boolean result = CrudUtil.execute(
                "UPDATE `task` SET `status` = ? WHERE `task_id` = ? AND `user_id` = ?",
                compTask.getStatus().name(),
                compTask.getId(),
                compTask.getUserId()
        );
        return result;
    }


    public Boolean removeTask(String taskId, String userId) throws SQLException {
        String query = "DELETE FROM `task` WHERE `task_id`='" + taskId + "' AND `user_id`='" + userId + "'";
        Statement statement = DBconnection.getInstance().getConnection().createStatement();
        return statement.executeUpdate(query) > 0;
    }

    public boolean markTaskAsPending(String id, String userId) throws SQLException {
        Boolean result = CrudUtil.execute(
                "UPDATE `task` SET `status` = ? WHERE `task_id` = ? AND `user_id` = ?",
                TaskStatus.PENDING.name(),
                id, userId);
        return result;
    }
//
//    public Boolean removeCompletedTask(String taskId, String userId) throws SQLException {
//        String query = "DELETE FROM `completedtask` WHERE `comptask_id`='" + taskId + "' AND `user_id`='" + userId + "'";
//        Statement statement = DBconnection.getInstance().getConnection().createStatement();
//        return statement.executeUpdate(query) > 0;
//    }
}
