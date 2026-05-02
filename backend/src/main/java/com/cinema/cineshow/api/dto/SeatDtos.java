package com.cinema.cineshow.api.dto;

import java.util.List;

public class SeatDtos {
    private SeatDtos() {}

    public static class SeatResponse {
        private String csid;
        private String theatreId;
        private String seatNumber;
        private Integer row;
        private Integer col;

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
    }

    public static class TheatreSeatSetupRequest {
        private Integer rows;
        private Integer cols;

        public Integer getRows() { return rows; }
        public void setRows(Integer rows) { this.rows = rows; }

        public Integer getCols() { return cols; }
        public void setCols(Integer cols) { this.cols = cols; }
    }
}
