package com.cinema.cineshow.infrastructure.entity;

import java.time.LocalDateTime;

public class ShowSeat {
    private String csid;
    private String showId;
    private String seatId;
    private Double price;
    private SeatStatus status;
    private String lockedBy;
    private LocalDateTime lockedAt;
    private String bookingId;
    private boolean isDeleted = false;

    public ShowSeat() {}

    public ShowSeat(String csid, String showId, String seatId, Double price, SeatStatus status, String lockedBy, LocalDateTime lockedAt, String bookingId, boolean isDeleted) {
        this.csid = csid;
        this.showId = showId;
        this.seatId = seatId;
        this.price = price;
        this.status = status;
        this.lockedBy = lockedBy;
        this.lockedAt = lockedAt;
        this.bookingId = bookingId;
        this.isDeleted = isDeleted;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }

    public String getShowId() { return showId; }
    public void setShowId(String showId) { this.showId = showId; }

    public String getSeatId() { return seatId; }
    public void setSeatId(String seatId) { this.seatId = seatId; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }

    public String getLockedBy() { return lockedBy; }
    public void setLockedBy(String lockedBy) { this.lockedBy = lockedBy; }

    public LocalDateTime getLockedAt() { return lockedAt; }
    public void setLockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public static ShowSeatBuilder builder() {
        return new ShowSeatBuilder();
    }

    public static class ShowSeatBuilder {
        private String csid;
        private String showId;
        private String seatId;
        private Double price;
        private SeatStatus status;
        private String lockedBy;
        private LocalDateTime lockedAt;
        private String bookingId;
        private boolean isDeleted = false;

        ShowSeatBuilder() {}

        public ShowSeatBuilder csid(String csid) { this.csid = csid; return this; }
        public ShowSeatBuilder showId(String showId) { this.showId = showId; return this; }
        public ShowSeatBuilder seatId(String seatId) { this.seatId = seatId; return this; }
        public ShowSeatBuilder price(Double price) { this.price = price; return this; }
        public ShowSeatBuilder status(SeatStatus status) { this.status = status; return this; }
        public ShowSeatBuilder lockedBy(String lockedBy) { this.lockedBy = lockedBy; return this; }
        public ShowSeatBuilder lockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; return this; }
        public ShowSeatBuilder bookingId(String bookingId) { this.bookingId = bookingId; return this; }
        public ShowSeatBuilder isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public ShowSeat build() {
            return new ShowSeat(csid, showId, seatId, price, status, lockedBy, lockedAt, bookingId, isDeleted);
        }
    }
}
