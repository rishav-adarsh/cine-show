package com.cinema.cineshow.infrastructure.nosql.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "bookings")
public class Booking {
    @Id
    private String csid;
    private String userId;
    private String showId;
    private List<String> seatNumbers;
    private Double totalAmount;
    private String status; // PENDING, CONFIRMED, CANCELLED
    private LocalDateTime bookingTime;
    private PaymentDetails paymentDetails;

    public static class PaymentDetails {
        private String transactionId;
        private String paymentMethod;
        private String paymentStatus;

        public PaymentDetails() {}

        public PaymentDetails(String transactionId, String paymentMethod, String paymentStatus) {
            this.transactionId = transactionId;
            this.paymentMethod = paymentMethod;
            this.paymentStatus = paymentStatus;
        }

        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        public String getPaymentStatus() { return paymentStatus; }
        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    }

    public Booking() {}

    public Booking(String csid, String userId, String showId, List<String> seatNumbers, Double totalAmount, String status, LocalDateTime bookingTime, PaymentDetails paymentDetails) {
        this.csid = csid;
        this.userId = userId;
        this.showId = showId;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.status = status;
        this.bookingTime = bookingTime;
        this.paymentDetails = paymentDetails;
    }

    public String getCsid() { return csid; }
    public void setCsid(String csid) { this.csid = csid; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getShowId() { return showId; }
    public void setShowId(String showId) { this.showId = showId; }
    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public PaymentDetails getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(PaymentDetails paymentDetails) { this.paymentDetails = paymentDetails; }
}
