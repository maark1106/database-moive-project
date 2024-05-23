package dto;

import java.util.Date;

public record ReservationDetailsDto(
        String name,
        Date startDate,
        String dayOfWeek,
        int round,
        String startTime,
        int screenId,
        Date paymentDate,
        int paymentAmount,
        int paymentStatus,
        String paymentMethod
) {
}
