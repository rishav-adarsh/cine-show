-- Add missing columns to movies table
ALTER TABLE movies ADD COLUMN IF NOT EXISTS description TEXT;
ALTER TABLE movies ADD COLUMN IF NOT EXISTS genre VARCHAR(100);
ALTER TABLE movies ADD COLUMN IF NOT EXISTS language VARCHAR(100);
