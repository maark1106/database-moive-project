package dto;

import java.util.Date;

public record UserReservationInfoDto(
        String title,
        Date startDate,
        int screenId,
        int rowNum,
        int columnNum,
        int sellingPrice,
        long reservationId,
        long seatId,
        long memberId,
        long movieId // 추가
) {
}


