package dao.user;

import dto.SeatDto;
import utils.databaseUtils.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDao {

    public List<SeatDto> getSeatsByScreenId(Long screenId) {
        List<SeatDto> seats = new ArrayList<>();
        String sql = "SELECT * FROM seat WHERE screen_id = ?";

        try (Connection conn = User.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, screenId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SeatDto seat = new SeatDto(
                        rs.getLong("id"),
                        rs.getBoolean("is_used"),
                        rs.getInt("row_num"),
                        rs.getInt("column_num"),
                        rs.getLong("screen_id")
                );
                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    public boolean updateSeatUsage(Long seatId, boolean isUsed) {
        String sql = "UPDATE seat SET is_used = ? WHERE id = ?";
        try (Connection conn = User.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isUsed);
            stmt.setLong(2, seatId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

