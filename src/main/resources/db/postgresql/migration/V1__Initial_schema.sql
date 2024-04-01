CREATE TABLE IF NOT EXISTS refresh_token (
    id SERIAL PRIMARY KEY,
    wallet_address VARCHAR(255) NOT NULL unique,
    refresh_token TEXT NOT NULL
    );
