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
                rs.getString("Phone")
            ));
        }

        return users;
    }
}
