CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE tb_users (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    role VARCHAR(50),
    email VARCHAR(150) NOT NULL,
    password VARCHAR(500) NOT NULL
);