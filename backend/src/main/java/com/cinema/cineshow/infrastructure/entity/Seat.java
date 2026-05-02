package com.cinema.cineshow.infrastructure.entity;

public class Seat {
    private String csid;
    private String theatreId;
    private String seatNumber; // e.g., "A1", "B2"
    private Integer row;
    private Integer col;
    private boolean isDeleted = false;

    public Seat() {}

    public Seat(String csid, String theatreId, String seatNumber, Integer row, Integer col) {
        this.csid = csid;
        this.theatreId = theatreId;
        this.seatNumber = seatNumber;
        this.row = row;
        this.col = col;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }

    public String getTheatreId() { return theatreId; }
    public void setTheatreId(String theatreId) { this.theatreId = theatreId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Integer getRow() { return row; }
    public void setRow(Integer row) { this.row = row; }

    public Integer getCol() { return col; }
    public void setCol(Integer col) { this.col = col; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public static SeatBuilder builder() {
        return new SeatBuilder();
    }

    public static class SeatBuilder {
        private String csid;
        private String theatreId;
        private String seatNumber;
        private Integer row;
        private Integer col;
        private boolean isDeleted = false;

        SeatBuilder() {}

        public SeatBuilder csid(String csid) { this.csid = csid; return this; }
        public SeatBuilder theatreId(String theatreId) { this.theatreId = theatreId; return this; }
        public SeatBuilder seatNumber(String seatNumber) { this.seatNumber = seatNumber; return this; }
        public SeatBuilder row(Integer row) { this.row = row; return this; }
        public SeatBuilder col(Integer col) { this.col = col; return this; }
        public SeatBuilder isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public Seat build() {
            return new Seat(csid, theatreId, seatNumber, row, col);
        }
    }
}
