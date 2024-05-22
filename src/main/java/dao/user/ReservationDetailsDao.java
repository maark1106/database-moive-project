package dao.user;

import dto.ReservationDetailsDto;
import dto.UserReservationInfoDto;
import utils.databaseUtils.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDetailsDao {

    public List<ReservationDetailsDto> getReservationDetails(UserReservationInfoDto reservation) {
        List<ReservationDetailsDto> reservationDetails = new ArrayList<>();

        String sql = "SELECT " +
                "m.name AS 이름, " +
                "ss.start_date AS 시작일, " +
                "DAYNAME(ss.start_date) AS 요일, " +
                "ss.round AS 회차, " +
                "ss.start_time AS 시작시간, " +
                "ss.screen_id AS 상영관번호, " +
                "r.payment_date AS 결제일, " +
                "r.payment_amount AS 결제금액, " +
                "r.payment_status AS 결제상태, " +
                "r.payment_method AS 결제수단 " +
                "FROM reservation r " +
                "JOIN member m ON r.member_id = m.id " +
                "JOIN ticket t ON r.id = t.reservation_id " +
                "JOIN screening_schedule ss ON t.screening_schedule_id = ss.id " +
                "WHERE r.id = ?";

        try {
            Connection conn = User.getUserConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, reservation.reservationId());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ReservationDetailsDto detail = new ReservationDetailsDto(
                        rs.getString("이름"),
                        rs.getDate("시작일"),
                        rs.getString("요일"),
                        rs.getInt("회차"),
                        rs.getString("시작시간"),
                        rs.getInt("상영관번호"),
                        rs.getDate("결제일"),
                        rs.getInt("결제금액"),
                        rs.getInt("결제상태"),
                        rs.getString("결제수단")
                );
                reservationDetails.add(detail);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservationDetails;
    }
}
