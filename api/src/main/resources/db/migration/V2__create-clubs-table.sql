CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_clubs (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    phone BIGINT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    instagram VARCHAR(100),
    facebook VARCHAR(100),
    twitter VARCHAR(100),
    owner_id UUID NOT NULL,
        CONSTRAINT fk_user
            FOREIGN KEY(owner_id)
            REFERENCES tb_users(id)
            ON DELETE CASCADE
);
