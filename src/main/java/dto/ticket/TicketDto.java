package dto.ticket;

public class TicketDto {
    private Long id;
    private boolean isTicketed;
    private Long screeningScheduleId;
    private Long seatId;
    private Long reservationId;
    private Long screenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsTicketed() {
        return isTicketed;
    }

    public void setIsTicketed(boolean isTicketed) {
        this.isTicketed = isTicketed;
    }

    public Long getScreeningScheduleId() {
        return screeningScheduleId;
    }

    public void setScreeningScheduleId(Long screeningScheduleId) {
        this.screeningScheduleId = screeningScheduleId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }
}

