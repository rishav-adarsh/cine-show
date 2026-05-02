-- V9__add_show_seats_and_refactor_bookings.sql
-- Add show_seats table and align bookings table with current entity

-- Refactor bookings table
ALTER TABLE bookings DROP COLUMN IF EXISTS seat_numbers;
ALTER TABLE bookings DROP COLUMN IF EXISTS payment_status;

-- Increase length of ID columns for future-proofing
ALTER TABLE bookings ALTER COLUMN csid TYPE VARCHAR(64);
ALTER TABLE bookings ALTER COLUMN user_id TYPE VARCHAR(64);
ALTER TABLE bookings ALTER COLUMN show_id TYPE VARCHAR(64);

-- Create show_seats table
CREATE TABLE IF NOT EXISTS show_seats (
    id BIGSERIAL PRIMARY KEY,
    csid VARCHAR(64) UNIQUE NOT NULL,
    show_id VARCHAR(64) NOT NULL REFERENCES shows(csid) ON DELETE CASCADE,
    seat_id VARCHAR(64) NOT NULL REFERENCES seats(csid) ON DELETE CASCADE,
    price NUMERIC(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    locked_by VARCHAR(64),
    locked_at TIMESTAMP,
    booking_id VARCHAR(64) REFERENCES bookings(csid),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(show_id, seat_id)
);

CREATE INDEX IF NOT EXISTS idx_show_seats_show_id ON show_seats(show_id);
CREATE INDEX IF NOT EXISTS idx_show_seats_seat_id ON show_seats(seat_id);
CREATE INDEX IF NOT EXISTS idx_show_seats_status ON show_seats(status);
CREATE INDEX IF NOT EXISTS idx_show_seats_booking_id ON show_seats(booking_id);
