package src.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // Generate next user ID (e.g., U001, U002)
    public String generateUserId() throws SQLException {
        String sql = "SELECT Id FROM user ORDER BY Id DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String lastId = rs.getString("Id"); // e.g., U023
                int number = Integer.parseInt(lastId.substring(1)); // extract 23
                return String.format("U%03d", number + 1); // U024
            } else {
                return "U001";
            }
        }
    }

    public List<UserModel> getAllUser(String flightId) throws SQLException {
        List<UserModel> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE Id IN (" +
                "SELECT userId FROM ticket WHERE flightId = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        }
        return users;
    }

    public UserModel createUser(UserModel user) throws SQLException {
        // Generate new user ID
        String newUserId = generateUserId();
        user.setId(newUserId);

        // SQL to insert new user
        String sql = "INSERT INTO user (Id, Date, FullName, Gender, Age, Phone) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getId());
            stmt.setDate(2, user.getDate());
            stmt.setString(3, user.getFullName());

            // Normalize gender to 'M' or 'F'
            String gender = user.getGender();
            if (gender.equalsIgnoreCase("Male")) {
                gender = "M";
            } else if (gender.equalsIgnoreCase("Female")) {
                gender = "F";
            }
            stmt.setString(4, gender);

            stmt.setInt(5, user.getAge());
            stmt.setString(6, user.getPhone());

            int rowsInserted = stmt.executeUpdate();
            System.out.println("User inserted: " + rowsInserted);
        }

        return user;
    }

    public void setUser(UserModel user) throws SQLException {
        String sql = "UPDATE user SET Date = ?, FullName = ?, Gender = ?, Age = ?, Phone = ? WHERE Id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
            System.out.println("User updated: " + rowsUpdated);
        }
    }

    public UserModel getUser(String flightId, String userId) throws SQLException {
        String sql = "SELECT u.* FROM Ticket t " +
                "JOIN User u ON t.UserId = u.Id " +
                "WHERE t.FlightId = ? AND t.UserId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, flightId);
            stmt.setString(2, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserModel(
                        rs.getString("Id"),
                        rs.getDate("Date"),
                        rs.getString("FullName"),
                        rs.getString("Gender"),
                        rs.getInt("Age"),
                        rs.getString("Phone"));
            } else {
                return null;
            }
        }
    }

    public UserModel getUser(String userId) throws SQLException {
        UserModel user = null;
        String sql = "SELECT * FROM user WHERE Id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new UserModel(
                            rs.getString("Id"),
                            rs.getDate("Date"),
                            rs.getString("FullName"),
                            rs.getString("Gender"),
                            rs.getInt("Age"),
                            rs.getString("Phone"));
                }
            }
        }
        return user;
    }
}
