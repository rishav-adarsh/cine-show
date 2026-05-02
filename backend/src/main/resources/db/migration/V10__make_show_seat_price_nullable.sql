-- V10__make_show_seat_price_nullable.sql
-- Allow price in show_seats to be nullable

ALTER TABLE show_seats ALTER COLUMN price DROP NOT NULL;
