-- Add csid to movies
ALTER TABLE movies ADD COLUMN IF NOT EXISTS csid VARCHAR(64) UNIQUE NOT NULL;

-- Add csid to theatres
ALTER TABLE theatres ADD COLUMN IF NOT EXISTS csid VARCHAR(64) UNIQUE NOT NULL;

-- Add csid to shows
ALTER TABLE shows ADD COLUMN IF NOT EXISTS csid VARCHAR(64) UNIQUE NOT NULL;

-- Update shows table to use csid for foreign keys
ALTER TABLE shows DROP CONSTRAINT IF EXISTS shows_movie_id_fkey;
ALTER TABLE shows DROP CONSTRAINT IF EXISTS shows_theatre_id_fkey;

ALTER TABLE shows DROP COLUMN IF EXISTS movie_id;
ALTER TABLE shows DROP COLUMN IF EXISTS theatre_id;

ALTER TABLE shows ADD COLUMN movie_id VARCHAR(64) NOT NULL REFERENCES movies(csid);
ALTER TABLE shows ADD COLUMN theatre_id VARCHAR(64) NOT NULL REFERENCES theatres(csid);
