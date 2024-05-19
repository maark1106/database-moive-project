package dto.screen;

public class ScreenDto {
    private Long id;
    private int seatCount;
    private boolean isUsed;
    private int columnSeats;
    private int rowSeats;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public int getColumnSeats() {
        return columnSeats;
    }

    public void setColumnSeats(int columnSeats) {
        this.columnSeats = columnSeats;
    }

    public int getRowSeats() {
        return rowSeats;
    }

    public void setRowSeats(int rowSeats) {
        this.rowSeats = rowSeats;
    }
}
