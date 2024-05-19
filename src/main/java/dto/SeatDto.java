package dto;

public record SeatDto(
        Long id,
        boolean isUsed,
        int rowNum,
        int columnNum,
        Long screenId
) {}
