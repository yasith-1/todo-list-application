package controller.signup;

import database.DBconnection;
import model.User;
import util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupController {

    private static SignupController instance;

    private SignupController() {
    }

    public static SignupController getInstance() {
        if (instance == null) {
            return instance = new SignupController();
        }
        return instance;
    }

    public Boolean notifyAlreadyUserExist(String name) throws SQLException {
        String query = "SELECT * FROM `user` WHERE `username`='" + name + "'";
        ResultSet resultSet = DBconnection.getInstance().getConnection().createStatement().executeQuery(query);
        return resultSet.next();
    }

    public Boolean addUser(User user) throws SQLException {
//        String query = "INSERT INTO `user` VALUES (?,?,?)";
//        PreparedStatement pst = DBconnection.getInstance().getConnection().prepareStatement(query);
//        pst.setString(1, user.getUserId());
//        pst.setString(2, user.getUsername());
//        pst.setString(3, user.getPassword());
//        return pst.executeUpdate() > 0;
        return CrudUtil.execute("INSERT INTO `user` VALUES (?,?,?)", user.getUserId(), user.getUsername(), user.getPassword());
    }

    public String generateuserId() {

        try {
            ResultSet rst = DBconnection.getInstance().getConnection().createStatement().executeQuery("SELECT `user_id` from `user` ORDER BY user_id DESC LIMIT 1");
            if (rst.next()) {
                String currentId = rst.getString("user_id");
                return String.format("U%03d", Integer.parseInt(currentId.substring(1)) + 1);
            } else {
                return "U001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
