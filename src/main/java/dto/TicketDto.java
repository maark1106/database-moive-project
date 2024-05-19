package dto;

public record TicketDto(
        Long id,
        boolean isTicketed,
        Long screeningScheduleId,
        Long seatId,
        Long reservationId,
        Long screenId
) {}
