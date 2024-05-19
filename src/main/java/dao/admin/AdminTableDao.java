package dao.admin;

import dto.member.MemberDto;
import dto.movie.MovieDto;
import dto.reservation.ReservationDto;
import dto.screen.ScreenDto;
import dto.screeningschedule.ScreeningScheduleDto;
import dto.seat.SeatDto;
import dto.ticket.TicketDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.databaseUtils.Admin;

public class AdminTableDao {

    private Connection conn = Admin.getAdminConnection();
    private Statement stmt = null;

    public List<MovieDto> getAllMovies() {
        List<MovieDto> movies = new ArrayList<>();
        String sql = "SELECT * FROM MOVIE";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                MovieDto movie = new MovieDto();
                movie.setId(rs.getLong("id"));
                movie.setTitle(rs.getString("title"));
                movie.setRunningTime(rs.getInt("running_time"));
                movie.setDirector(rs.getString("director"));
                movie.setActor(rs.getString("actor"));
                movie.setGenre(rs.getString("genre"));
                movie.setDescription(rs.getString("description"));
                movie.setReleaseDate(rs.getDate("release_date"));
                movie.setRating(rs.getDouble("rating"));
                movie.setAgeLimit(rs.getInt("age_limit"));

                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public List<MemberDto> getAllMembers() {
        List<MemberDto> members = new ArrayList<>();
        String sql = "SELECT * FROM member";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                MemberDto member = new MemberDto();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                member.setPhoneNumber(rs.getString("phone_number"));
                member.setEmail(rs.getString("email"));

                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public List<ScreenDto> getAllScreens() {
        List<ScreenDto> screens = new ArrayList<>();
        String sql = "SELECT * FROM screen";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ScreenDto screen = new ScreenDto();
                screen.setId(rs.getLong("id"));
                screen.setSeatCount(rs.getInt("seat_count"));
                screen.setIsUsed(rs.getBoolean("is_used"));
                screen.setColumnSeats(rs.getInt("column_seats"));
                screen.setRowSeats(rs.getInt("row_seats"));

                screens.add(screen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return screens;
    }

    public List<ScreeningScheduleDto> getAllScreenSchedules() {
        List<ScreeningScheduleDto> screeningSchedules = new ArrayList<>();
        String sql = "SELECT * FROM screening_schedule";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ScreeningScheduleDto screeningSchedule = new ScreeningScheduleDto();

                screeningSchedule.setId(rs.getLong("id"));
                screeningSchedule.setStartDate(rs.getDate("start_date"));
                screeningSchedule.setDayOfWeek(rs.getString("day_of_week"));
                screeningSchedule.setRound(rs.getInt("round"));
                screeningSchedule.setStartTime(rs.getTime("start_time"));
                screeningSchedule.setMovieId(rs.getLong("movie_id"));
                screeningSchedule.setScreenId(rs.getLong("screen_id"));
                screeningSchedule.setSellingPrice(rs.getInt("selling_price"));
                screeningSchedule.setStandardPrice(rs.getInt("standard_price"));

                screeningSchedules.add(screeningSchedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return screeningSchedules;
    }

    public List<SeatDto> getAllSeats() {
        List<SeatDto> seats = new ArrayList<>();
        String sql = "SELECT * FROM seat";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                SeatDto seat = new SeatDto();
                seat.setId(rs.getLong("id"));
                seat.setIsUsed(rs.getBoolean("is_used"));
                seat.setRowNum(rs.getInt("row_num"));
                seat.setColumnNum(rs.getInt("column_num"));
                seat.setScreenId(rs.getLong("screen_id"));

                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    public List<ReservationDto> getAllReservations() {
        List<ReservationDto> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ReservationDto reservation = new ReservationDto();
                reservation.setId(rs.getLong("id"));
                reservation.setPaymentMethod(rs.getString("payment_method"));
                reservation.setPaymentStatus(rs.getBoolean("payment_status"));
                reservation.setPaymentAmount(rs.getInt("payment_amount"));
                reservation.setPaymentDate(rs.getDate("payment_date"));
                reservation.setMemberId(rs.getLong("member_id"));

                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public List<TicketDto> getAllTickets() {
        List<TicketDto> tickets = new ArrayList<>();
        String sql = "SELECT * FROM ticket";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                TicketDto ticket = new TicketDto();
                ticket.setId(rs.getLong("id"));
                ticket.setIsTicketed(rs.getBoolean("is_ticketed"));
                ticket.setScreeningScheduleId(rs.getLong("screening_schedule_id"));
                ticket.setSeatId(rs.getLong("seat_id"));
                ticket.setReservationId(rs.getLong("reservation_id"));
                ticket.setScreenId(rs.getLong("screen_id"));

                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
}
