package src.Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import src.Model.DBConnection;
import src.Model.UserDAO;
import src.Model.UserModel;

public class UserController {
    public static List<UserModel> getAllUser(String flightId) {
        List<UserModel> users = null; // khai báo danh sách để trả về

        try {
            Connection conn = DBConnection.getConnection();
            UserDAO userDAO = new UserDAO(conn);
            users = userDAO.getAllUser(flightId);
            conn.close(); // Đóng kết nối sau khi dùng xong
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
