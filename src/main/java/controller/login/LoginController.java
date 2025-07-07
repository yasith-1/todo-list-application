package controller.login;

import controller.dashboard.TodoDashboardController;
import database.DBconnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    private static LoginController instance;
    private LoginController() {
    }

    public static LoginController getInstance() {
        if (instance == null) {
            return instance = new LoginController();
        }
        return instance;
    }

    public Boolean userAuthentication(String username, String password) {
        String query = "SELECT `user_id`,`username` FROM `user` WHERE `username`=? AND `password`=?";
        try {
            PreparedStatement ps = DBconnection.getInstance().getConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                // Set it in DashboardController
                TodoDashboardController.getInstance().setLoggedUserName(resultSet.getString("user_id"), resultSet.getString("username"));

                return true; // Successful login
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
