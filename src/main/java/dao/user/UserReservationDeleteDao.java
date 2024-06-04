package dao.user;

import utils.databaseUtils.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserReservationDeleteDao {

    public boolean deleteReservation(long reservationId) {
        String deleteTicketSql = "DELETE FROM ticket WHERE reservation_id = ?";
        String deleteSeatSql = "DELETE FROM seat WHERE id IN (SELECT seat_id FROM ticket WHERE reservation_id = ?)";
        String deleteReservationSql = "DELETE FROM reservation WHERE id = ?";

        try (Connection conn = User.getUserConnection();
             PreparedStatement deleteTicketStmt = conn.prepareStatement(deleteTicketSql);
             PreparedStatement deleteSeatStmt = conn.prepareStatement(deleteSeatSql);
             PreparedStatement deleteReservationStmt = conn.prepareStatement(deleteReservationSql)) {

            conn.setAutoCommit(false);

            // 티켓 삭제
            deleteTicketStmt.setLong(1, reservationId);
            deleteTicketStmt.executeUpdate();

            // 좌석 삭제
            deleteSeatStmt.setLong(1, reservationId);
            deleteSeatStmt.executeUpdate();

            // 예약 삭제
            deleteReservationStmt.setLong(1, reservationId);
            deleteReservationStmt.executeUpdate();

            conn.commit();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
