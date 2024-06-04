package dao.user;

import dto.ReservationDto;
import dto.SeatDto;
import dto.TicketDto;
import utils.databaseUtils.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserReservationCreateDao {

    public Long createReservation(ReservationDto reservation) {
        String sql = "INSERT INTO reservation (payment_method, payment_status, payment_amount, payment_date, member_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = User.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, reservation.paymentMethod());
            stmt.setBoolean(2, reservation.paymentStatus());
            stmt.setInt(3, reservation.paymentAmount());
            stmt.setDate(4, reservation.paymentDate());
            stmt.setLong(5, reservation.memberId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long createSeat(SeatDto seat) {
        String sql = "INSERT INTO seat (is_used, row_num, column_num, screen_id, screening_schedule_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = User.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setBoolean(1, seat.isUsed());
            stmt.setInt(2, seat.rowNum());
            stmt.setInt(3, seat.columnNum());
            stmt.setLong(4, seat.screenId());
            stmt.setLong(5, seat.screeningScheduleId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createTicket(TicketDto ticket) {
        String sql = "INSERT INTO ticket (is_ticketed, screening_schedule_id, seat_id, reservation_id, screen_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = User.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, ticket.isTicketed());
            stmt.setLong(2, ticket.screeningScheduleId());
            stmt.setLong(3, ticket.seatId());
            stmt.setLong(4, ticket.reservationId());
            stmt.setLong(5, ticket.screenId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
