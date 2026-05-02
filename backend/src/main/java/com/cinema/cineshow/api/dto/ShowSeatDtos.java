package com.cinema.cineshow.api.dto;

import com.cinema.cineshow.infrastructure.entity.SeatStatus;
import java.time.LocalDateTime;
import java.util.List;

public class ShowSeatDtos {
    private ShowSeatDtos() {}

    public static class ShowSeatResponse {
        private String csid;
        private String showId;
        private String seatId;
        private Double price;
        private SeatStatus status;
        private String lockedBy;
        private LocalDateTime lockedAt;
        private String bookingId;

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
    }

    public static class LockSeatsRequest {
        private List<String> seatIds;
        private String userId;

        public List<String> getSeatIds() { return seatIds; }
        public void setSeatIds(List<String> seatIds) { this.seatIds = seatIds; }

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }
}
