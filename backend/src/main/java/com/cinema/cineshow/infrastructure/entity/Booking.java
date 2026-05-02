package com.cinema.cineshow.infrastructure.entity;

import java.time.LocalDateTime;

public class Booking {
    private String csid;
    private String userId;
    private String showId;
    private Double totalAmount;
    private BookingStatus status; // PENDING, CONFIRMED, CANCELLED
    private LocalDateTime bookedAt;
    private String transactionId;
    private PaymentMethod paymentMethod;
    private boolean isDeleted = false;

    public Booking() {}

    public Booking(String csid, String userId, String showId, Double totalAmount, BookingStatus status, LocalDateTime bookedAt, String transactionId, PaymentMethod paymentMethod, boolean isDeleted) {
        this.csid = csid;
        this.userId = userId;
        this.showId = showId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.bookedAt = bookedAt;
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.isDeleted = isDeleted;
    }

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

    public boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    public static BookingBuilder builder() {
        return new BookingBuilder();
    }

    public static class BookingBuilder {
        private String csid;
        private String userId;
        private String showId;
        private Double totalAmount;
        private BookingStatus status;
        private LocalDateTime bookedAt;
        private String transactionId;
        private PaymentMethod paymentMethod;
        private boolean isDeleted = false;

        BookingBuilder() {}

        public BookingBuilder csid(String csid) { this.csid = csid; return this; }
        public BookingBuilder userId(String userId) { this.userId = userId; return this; }
        public BookingBuilder showId(String showId) { this.showId = showId; return this; }
        public BookingBuilder totalAmount(Double totalAmount) { this.totalAmount = totalAmount; return this; }
        public BookingBuilder status(BookingStatus status) { this.status = status; return this; }
        public BookingBuilder bookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; return this; }
        public BookingBuilder transactionId(String transactionId) { this.transactionId = transactionId; return this; }
        public BookingBuilder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public BookingBuilder isDeleted(boolean isDeleted) { this.isDeleted = isDeleted; return this; }

        public Booking build() {
            return new Booking(csid, userId, showId, totalAmount, status, bookedAt, transactionId, paymentMethod, isDeleted);
        }
    }
}

