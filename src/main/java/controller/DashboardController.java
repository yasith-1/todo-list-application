package controller;

import database.DBconnection;
import javafx.geometry.Pos;
import model.Task;
import org.controlsfx.control.Notifications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            ps.setString(4, task.getDate());
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
}
