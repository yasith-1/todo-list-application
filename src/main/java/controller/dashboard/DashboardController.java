package controller.dashboard;

import database.DBconnection;
import model.CompletedTask;
import model.Task;
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
            Connection connection = DBconnection.getInstance().getConnection();
            String sql = "INSERT INTO `task` (`task_id`,`name`,`description`,`date`,`time`,`status_id`,`user_id`) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, genarateTaskId());
            ps.setString(2, task.getName());
            ps.setString(3, task.getDescription());
            ps.setDate(4, task.getDate());
            ps.setString(5, task.getTime());
            ps.setInt(6, task.getStatusId());
            ps.setString(7, userId);

            return ps.executeUpdate() > 0;

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
            ResultSet rst = DBconnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM task WHERE user_id='" + userId + "'");
            while (rst.next()) {
                taskArrayList.add(new Task(
                        rst.getString("task_id"),
                        rst.getString("name"),
                        rst.getString("description"),
                        rst.getDate("date"),
                        rst.getString("time"),
                        rst.getInt("status_id"),
                        rst.getString("user_id")
                ));
            }
            return taskArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<CompletedTask> loadCompletedTask() {
        ArrayList<CompletedTask> compTaskArrayList = new ArrayList<>();
        try {
            ResultSet rst = DBconnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM `completedtask` WHERE user_id='" + userId + "'");
            while (rst.next()) {
                compTaskArrayList.add(new CompletedTask(
                        rst.getString("comptask_id"),
                        rst.getString("name"),
                        rst.getString("description"),
                        rst.getDate("date"),
                        rst.getString("time"),
                        rst.getString("user_id"),
                        rst.getInt("status_id")
                ));
            }
            return compTaskArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean addCompletedTask(String completedTaskId, String userId, Date CompleteDdate, String CompletedTime) throws SQLException {
        String query = "SELECT * FROM `task` WHERE `user_id`='" + userId + "' AND `task_id`='" + completedTaskId + "'";
        ResultSet rst = DBconnection.getInstance().getConnection().createStatement().executeQuery(query);

        while (rst.next()) {
            String taskId = rst.getString("task_id");
            String name = rst.getString("name");
            String description = rst.getString("description");
            int statusId = 2;

            CompletedTask completedTask = new CompletedTask(taskId, name, description, CompleteDdate, CompletedTime, userId, statusId);

            return addCompletedTask(completedTask);
        }
        return false;
    }

    public Boolean addCompletedTask(CompletedTask compTask) throws SQLException {
        String query = "INSERT INTO `completedtask` VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pst = DBconnection.getInstance().getConnection().prepareStatement(query);
        pst.setString(1, compTask.getId());
        pst.setString(2, compTask.getName());
        pst.setString(3, compTask.getDescription());
        pst.setObject(4, compTask.getDate());
        pst.setString(5, compTask.getTime());
        pst.setString(6, compTask.getUserId());
        pst.setInt(7, compTask.getStatusId());

        return pst.executeUpdate() > 0;
    }

    public Boolean removeTask(String taskId, String userId) throws SQLException {
        String query = "DELETE FROM `task` WHERE `task_id`='" + taskId + "' AND `user_id`='" + userId + "'";
        Statement statement = DBconnection.getInstance().getConnection().createStatement();
        return statement.executeUpdate(query) > 0;
    }

    public Boolean removeCompletedTask(String taskId, String userId) throws SQLException {
        String query = "DELETE FROM `completedtask` WHERE `comptask_id`='" + taskId + "' AND `user_id`='" + userId + "'";
        Statement statement = DBconnection.getInstance().getConnection().createStatement();
        return statement.executeUpdate(query) > 0;
    }
}
