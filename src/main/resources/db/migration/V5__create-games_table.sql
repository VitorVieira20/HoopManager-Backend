CREATE TABLE tb_games (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    date DATE NOT NULL,
    home_team VARCHAR(100) NOT NULL,
    away_team VARCHAR(100) NOT NULL,
    home_score INT,
    away_score INT,
    location VARCHAR(100),
    team_id UUID NOT NULL,
    CONSTRAINT fk_team
        FOREIGN KEY (team_id)
        REFERENCES tb_teams(id)
        ON DELETE CASCADE
);
