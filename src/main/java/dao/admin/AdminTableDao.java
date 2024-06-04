package dao.admin;

import dto.MemberDto;
import dto.MovieDto;
import dto.ReservationDto;
import dto.ScreenDto;
import dto.ScreeningScheduleDto;
import dto.SeatDto;
import dto.TicketDto;
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
                MovieDto movie = new MovieDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getInt("running_time"),
                        rs.getString("director"),
                        rs.getString("actor"),
                        rs.getString("genre"),
                        rs.getString("description"),
                        rs.getDate("release_date"),
                        rs.getDouble("rating"),
                        rs.getInt("age_limit")
                );

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
                MemberDto member = new MemberDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getString("email")
                );

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
                ScreenDto screen = new ScreenDto(
                        rs.getLong("id"),
                        rs.getInt("seat_count"),
                        rs.getBoolean("is_used"),
                        rs.getInt("column_seats"),
                        rs.getInt("row_seats")
                );

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
                ScreeningScheduleDto screeningSchedule = new ScreeningScheduleDto(
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
                SeatDto seat = new SeatDto(
                        rs.getLong("id"),
                        rs.getBoolean("is_used"),
                        rs.getInt("row_num"),
                        rs.getInt("column_num"),
                        rs.getLong("screen_id"),
                        rs.getLong("screening_schedule_id")
                );

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
                ReservationDto reservation = new ReservationDto(
                        rs.getLong("id"),
                        rs.getString("payment_method"),
                        rs.getBoolean("payment_status"),
                        rs.getInt("payment_amount"),
                        rs.getDate("payment_date"),
                        rs.getLong("member_id")
                );

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
                TicketDto ticket = new TicketDto(
                        rs.getLong("id"),
                        rs.getBoolean("is_ticketed"),
                        rs.getLong("screening_schedule_id"),
                        rs.getLong("seat_id"),
                        rs.getLong("reservation_id"),
                        rs.getLong("screen_id")
                );

                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }
}
