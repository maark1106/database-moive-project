package dto;

import java.sql.Date;
import java.sql.ResultSet;

public record MovieDto (
        Long id,
        String title,
        int runningTime,
        String director,
        String actor,
        String genre,
        String description,
        java.sql.Date releaseDate,
        double rating,
        int ageLimit
){ }
