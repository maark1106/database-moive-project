package dto;

import java.sql.Date;

public record ReservationDto(
        Long id,
        String paymentMethod,
        boolean paymentStatus,
        int paymentAmount,
        Date paymentDate,
        Long memberId
) {}
