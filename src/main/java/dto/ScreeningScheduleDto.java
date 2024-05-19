package dto;

import java.sql.Date;
import java.sql.Time;

public record ScreeningScheduleDto(
        Long id,
        Date startDate,
        String dayOfWeek,
        int round,
        Time startTime,
        Long movieId,
        Long screenId,
        int sellingPrice,
        int standardPrice
) {}
