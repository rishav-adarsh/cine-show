package com.cinema.cineshow.api.dto;

import com.cinema.cineshow.infrastructure.entity.BookingStatus;
import com.cinema.cineshow.infrastructure.entity.PaymentMethod;
import java.time.LocalDateTime;
import java.util.List;

public class BookingDtos {
    private BookingDtos() {}

    public static class BookingCreateRequest {
        private String userId;
        private String showId;
        private List<String> seatIds;
        private Double totalAmount;
        private PaymentMethod paymentMethod;
        private String transactionId;

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public String getShowId() { return showId; }
        public void setShowId(String showId) { this.showId = showId; }

        public List<String> getSeatIds() { return seatIds; }
        public void setSeatIds(List<String> seatIds) { this.seatIds = seatIds; }

        public Double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

        public PaymentMethod getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    }

    public static class BookingResponse {
        private String csid;
        private String userId;
        private String showId;
        private Double totalAmount;
        private BookingStatus status;
        private LocalDateTime bookedAt;
        private String transactionId;
        private PaymentMethod paymentMethod;

        public String getCsid() { return csid; }
        public void setCsid(String csid) { this.csid = csid; }

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public String getShowId() { return showId; }
        public void setShowId(String showId) { this.showId = showId; }

        public Double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

        public BookingStatus getStatus() { return status; }
        public void setStatus(BookingStatus status) { this.status = status; }

        public LocalDateTime getBookedAt() { return bookedAt; }
        public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }

        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

        public PaymentMethod getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    }
}
