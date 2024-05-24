package dao.user;

import dto.UserReservationInfoDto;
import utils.databaseUtils.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserReservationInfoDao {

    public List<UserReservationInfoDto> getUserReservationInfo(long memberId) {
        List<UserReservationInfoDto> userReservationInfos = new ArrayList<>();

        String sql = "SELECT " +
                "m.title AS 영화명, " +
                "ss.start_date AS 상영일, " +
                "ss.screen_id AS 상영관번호, " +
                "s.row_num AS 좌석번호_열, " +
                "s.column_num AS 좌석번호_행, " +
                "ss.selling_price AS 판매가격, " +
                "t.reservation_id AS 예약ID, " +
                "t.seat_id AS 좌석ID, " +
                "r.member_id AS 회원ID, " +
                "m.id AS 영화ID " + // movieId 추가
                "FROM ticket t " +
                "JOIN screening_schedule ss ON t.screening_schedule_id = ss.id " +
                "JOIN seat s ON t.seat_id = s.id " +
                "JOIN movie m ON ss.movie_id = m.id " +
                "JOIN reservation r ON t.reservation_id = r.id " +
                "WHERE r.member_id = ?;";

        try {
            Connection conn = User.getUserConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserReservationInfoDto reservation = new UserReservationInfoDto(
                        rs.getString("영화명"),
                        rs.getDate("상영일"),
                        rs.getInt("상영관번호"),
                        rs.getInt("좌석번호_열"),
                        rs.getInt("좌석번호_행"),
                        rs.getInt("판매가격"),
                        rs.getLong("예약ID"),
                        rs.getLong("좌석ID"),
                        rs.getLong("회원ID"),
                        rs.getLong("영화ID") // movieId 추가
                );
                userReservationInfos.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userReservationInfos;
    }
}
