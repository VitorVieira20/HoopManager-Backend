CREATE TABLE tb_games (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    date DATE NOT NULL,
    home_team VARCHAR(100) NOT NULL,
    away_team VARCHAR(100) NOT NULL,
    home_score INT NOT NULL,
    away_score INT NOT NULL,
    location VARCHAR(100) NOT NULL
);
