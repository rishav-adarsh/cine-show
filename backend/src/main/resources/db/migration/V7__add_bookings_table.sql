-- V7__add_bookings_table.sql
-- Migrate Booking entity from MongoDB to PostgreSQL

CREATE TABLE IF NOT EXISTS bookings (
    id BIGSERIAL PRIMARY KEY,
    csid VARCHAR(36) UNIQUE NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    show_id VARCHAR(36) NOT NULL,
    seat_numbers TEXT NOT NULL,
    total_amount NUMERIC(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    booking_time TIMESTAMP NOT NULL,
    transaction_id VARCHAR(255),
    payment_method VARCHAR(50),
    payment_status VARCHAR(50),
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_booking_user FOREIGN KEY(user_id) REFERENCES users(csid),
    CONSTRAINT fk_booking_show FOREIGN KEY(show_id) REFERENCES shows(csid)
);

CREATE INDEX idx_bookings_user_id ON bookings(user_id);
CREATE INDEX idx_bookings_show_id ON bookings(show_id);
CREATE INDEX idx_bookings_status ON bookings(status);
CREATE INDEX idx_bookings_booking_time ON bookings(booking_time);
CREATE INDEX idx_bookings_csid ON bookings(csid);

