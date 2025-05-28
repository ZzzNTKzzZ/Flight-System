package src.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public List<UserModel> getAllUser(String flightId) throws SQLException {
        List<UserModel> users = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM user " +
                "WHERE Id IN ( " +
                "    SELECT userId " +
                "    FROM ticket " +
                "    WHERE flightId = ?" +
                ");";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, flightId);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            users.add(new UserModel(
                    rs.getString("Id"),
                    rs.getDate("Date"),
                    rs.getString("FullName"),
                    rs.getString("Gender"),
                    rs.getInt("Age"),
                    rs.getString("Phone")));
        }

        return users;
    }

    public void setUser(UserModel user) throws SQLException {
        String sql = "UPDATE user SET " +
                "Date = ?, " +
                "FullName = ?, " +
                "Gender = ?, " +
                "Age = ?, " +
                "Phone = ? " +
                "WHERE Id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, user.getDate());
        stmt.setString(2, user.getFullName());
        String gender = user.getGender();
        if (gender.equalsIgnoreCase("Male")) {
            gender = "M";
        } else if (gender.equalsIgnoreCase("Female")) {
            gender = "F";
        }
        stmt.setString(3, gender);
        stmt.setInt(4, user.getAge());
        stmt.setString(5, user.getPhone());
        stmt.setString(6, user.getId());

        int rowsUpdated = stmt.executeUpdate();
        System.out.println("Rows updated: " + rowsUpdated);
    }

    public UserModel getUser(String flightId, String userId) throws SQLException {
        String sql = "SELECT u.* " +
                "FROM Ticket t " +
                "JOIN User u ON t.UserId = u.Id " +
                "WHERE t.FlightId = ? AND t.UserId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, flightId);
        stmt.setString(2, userId);
        ResultSet rs = stmt.executeQuery();

        try {
            if (rs.next()) {
                UserModel user = new UserModel(
                        rs.getString("Id"),
                        rs.getDate("Date"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getInt("Age"),
                        rs.getString("Phone"));
                return user;
            } else {
                return null;
            }
        } finally {
            rs.close();
            stmt.close();
        }
    }
}
