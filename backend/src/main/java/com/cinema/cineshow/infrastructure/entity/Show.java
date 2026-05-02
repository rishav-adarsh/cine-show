package com.cinema.cineshow.infrastructure.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Show {
    private String csid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double ticketPrice = 300.0;
    private String movieId;
    private String theatreId;
    private List<String> bookedSeatIds;
    private ShowStatus status = ShowStatus.ACTIVE;
    private boolean isDeleted = false;

    public Show() {}

    public Show(String csid, LocalDateTime startTime, LocalDateTime endTime, Double ticketPrice, String movieId, String theatreId, List<String> bookedSeatIds, ShowStatus status, boolean isDeleted) {
        this.csid = csid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ticketPrice = ticketPrice;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.bookedSeatIds = bookedSeatIds;
        this.status = status;
        this.isDeleted = isDeleted;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getTheatreId() { return theatreId; }
    public void setTheatreId(String theatreId) { this.theatreId = theatreId; }

    public List<String> getBookedSeatIds() { return bookedSeatIds; }
    public void setBookedSeatIds(List<String> bookedSeatIds) { this.bookedSeatIds = bookedSeatIds; }

    public ShowStatus getStatus() { return status; }
    public void setStatus(ShowStatus status) { this.status = status; }

    public boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public static ShowBuilder builder() {
        return new ShowBuilder();
    }

    public static class ShowBuilder {
        private String csid;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Double ticketPrice = 300.0;
        private String movieId;
        private String theatreId;
        private List<String> bookedSeatIds;
        private ShowStatus status = ShowStatus.ACTIVE;
        private boolean isDeleted = false;

        ShowBuilder() {}

        public ShowBuilder csid(String csid) { this.csid = csid; return this; }
        public ShowBuilder startTime(LocalDateTime startTime) { this.startTime = startTime; return this; }
        public ShowBuilder endTime(LocalDateTime endTime) { this.endTime = endTime; return this; }
        public ShowBuilder ticketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; return this; }
        public ShowBuilder movieId(String movieId) { this.movieId = movieId; return this; }
        public ShowBuilder theatreId(String theatreId) { this.theatreId = theatreId; return this; }
        public ShowBuilder bookedSeatIds(List<String> bookedSeatIds) { this.bookedSeatIds = bookedSeatIds; return this; }
        public ShowBuilder status(ShowStatus status) { this.status = status; return this; }
        public ShowBuilder isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public Show build() {
            return new Show(csid, startTime, endTime, ticketPrice, movieId, theatreId, bookedSeatIds, status, isDeleted);
        }
    }
}
