package dao.user;

import dto.ScreenDto;
import utils.databaseUtils.User;

import java.sql.*;

public class ScreenDao {

    public ScreenDto getScreenById(Long screenId) {
        String sql = "SELECT * FROM screen WHERE id = ?";
        try (Connection conn = User.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, screenId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ScreenDto(
                        rs.getLong("id"),
                        rs.getInt("seat_count"),
                        rs.getBoolean("is_used"),
                        rs.getInt("column_seats"),
                        rs.getInt("row_seats")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

