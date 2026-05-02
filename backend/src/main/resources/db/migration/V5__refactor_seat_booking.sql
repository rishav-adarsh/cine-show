-- Refactor seat booking: introduce seats table, remove fixed seat grid from theatres and shows

-- Create seats table
CREATE TABLE IF NOT EXISTS seats (
    csid VARCHAR(64) PRIMARY KEY,
    theatre_id VARCHAR(64) NOT NULL REFERENCES theatres(csid) ON DELETE CASCADE,
    seat_number VARCHAR(10) NOT NULL,
    row_num INTEGER NOT NULL,
    col_num INTEGER NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(theatre_id, seat_number)
);

-- Create index on theatre_id for seats
CREATE INDEX IF NOT EXISTS idx_seats_theatre_id ON seats(theatre_id);

-- Remove fixed seat columns from theatres
ALTER TABLE theatres DROP COLUMN IF EXISTS seat_rows;
ALTER TABLE theatres DROP COLUMN IF EXISTS seat_cols;

-- Add soft delete and version to existing tables
ALTER TABLE movies ADD COLUMN IF NOT EXISTS is_deleted BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE users ADD COLUMN IF NOT EXISTS is_deleted BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE users ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT NOW();
ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

ALTER TABLE theatres ADD COLUMN IF NOT EXISTS is_deleted BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE shows ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';
ALTER TABLE shows ADD COLUMN IF NOT EXISTS is_deleted BOOLEAN NOT NULL DEFAULT FALSE;

-- Note: shows.seat_booked_map remains JSONB, now stores array of seat IDs (strings) instead of boolean grid
