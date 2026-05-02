-- Convert movie release_date from VARCHAR to DATE
ALTER TABLE movies 
    ALTER COLUMN release_date TYPE DATE 
    USING (CASE 
        WHEN release_date ~ '^\d{4}-\d{2}-\d{2}$' THEN release_date::DATE
        ELSE NULL -- Or handle other formats if known, but user provided "12th December" which is invalid for DATE
    END);

-- Convert show start_time and end_time from VARCHAR to TIMESTAMP
ALTER TABLE shows 
    ALTER COLUMN start_time TYPE TIMESTAMP 
    USING (CASE 
        WHEN start_time ~ '^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}$' THEN start_time::TIMESTAMP
        ELSE NOW() -- Fallback for invalid formats
    END);

ALTER TABLE shows 
    ALTER COLUMN end_time TYPE TIMESTAMP 
    USING (CASE 
        WHEN end_time ~ '^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}$' THEN end_time::TIMESTAMP
        ELSE NOW() -- Fallback for invalid formats
    END);
