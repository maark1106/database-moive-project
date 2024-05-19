package dto;

public record ScreenDto(
        Long id,
        int seatCount,
        boolean isUsed,
        int columnSeats,
        int rowSeats
) {}
