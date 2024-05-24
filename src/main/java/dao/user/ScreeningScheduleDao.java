package dao.user;

import dto.ScreeningScheduleDto;
import utils.databaseUtils.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreeningScheduleDao {

    public List<ScreeningScheduleDto> getScreeningSchedules(Long movieId) {
        List<ScreeningScheduleDto> schedules = new ArrayList<>();
        String sql = "SELECT * FROM screening_schedule WHERE movie_id = ?";

        try (Connection conn = User.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, movieId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ScreeningScheduleDto schedule = new ScreeningScheduleDto(
                        rs.getLong("id"),
                        rs.getDate("start_date"),
                        rs.getString("day_of_week"),
                        rs.getInt("round"),
                        rs.getTime("start_time"),
                        rs.getLong("movie_id"),
                        rs.getLong("screen_id"),
                        rs.getInt("selling_price"),
                        rs.getInt("standard_price")
                );
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }
}
