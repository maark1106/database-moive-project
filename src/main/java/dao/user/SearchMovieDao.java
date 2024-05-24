package dao.user;

import dto.MovieDto;
import utils.databaseUtils.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchMovieDao {

    public List<MovieDto> searchMovies(String title, String director, String actor, String genre) {
        List<MovieDto> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie WHERE title LIKE ? AND director LIKE ? AND actor LIKE ? AND genre LIKE ?";

        try (Connection conn = User.getUserConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title + "%");
            stmt.setString(2, "%" + director + "%");
            stmt.setString(3, "%" + actor + "%");
            stmt.setString(4, "%" + genre + "%");

            ResultSet rs = stmt.executeQuery();

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
}

