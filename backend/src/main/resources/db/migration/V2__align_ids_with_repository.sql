-- Align relational schema with repository layer expecting csid-based IDs.

-- Roles: add csid and backfill deterministic values for existing seeded roles.
ALTER TABLE roles
    ADD COLUMN IF NOT EXISTS csid VARCHAR(64);

UPDATE roles
SET csid = CASE
    WHEN name = 'ADMIN' THEN 'admin-role-csid'
    WHEN name = 'NORMAL' THEN 'default-role-csid'
    ELSE 'role-' || id::text
END
WHERE csid IS NULL;

ALTER TABLE roles
    ALTER COLUMN csid SET NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'roles_csid_unique'
    ) THEN
        ALTER TABLE roles ADD CONSTRAINT roles_csid_unique UNIQUE (csid);
    END IF;
END $$;

-- Users: add csid and migrate role reference from numeric id to role csid.
ALTER TABLE users
    ADD COLUMN IF NOT EXISTS csid VARCHAR(64);

UPDATE users
SET csid = 'user-' || id::text
WHERE csid IS NULL;

ALTER TABLE users
    ALTER COLUMN csid SET NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'users_csid_unique'
    ) THEN
        ALTER TABLE users ADD CONSTRAINT users_csid_unique UNIQUE (csid);
    END IF;
END $$;

ALTER TABLE users
    ADD COLUMN IF NOT EXISTS role_csid VARCHAR(64);

UPDATE users u
SET role_csid = r.csid
FROM roles r
WHERE u.role_csid IS NULL
  AND u.role_id = r.id;

ALTER TABLE users DROP CONSTRAINT IF EXISTS users_role_id_fkey;
ALTER TABLE users DROP COLUMN IF EXISTS role_id;
ALTER TABLE users RENAME COLUMN role_csid TO role_id;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'users_role_id_fkey'
    ) THEN
        ALTER TABLE users
            ADD CONSTRAINT users_role_id_fkey
            FOREIGN KEY (role_id) REFERENCES roles(csid);
    END IF;
END $$;

