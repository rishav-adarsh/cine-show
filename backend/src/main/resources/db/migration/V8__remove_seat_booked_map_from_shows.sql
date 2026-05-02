-- V8__remove_seat_booked_map_from_shows.sql
-- Remove the old seat_booked_map column from shows table as we now use show_seats table

ALTER TABLE shows DROP COLUMN IF EXISTS seat_booked_map;
