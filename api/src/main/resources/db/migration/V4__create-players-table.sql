CREATE TABLE tb_players (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(50),
    team_id UUID NOT NULL,
    CONSTRAINT fk_team
        FOREIGN KEY(team_id)
        REFERENCES tb_teams(id)
        ON DELETE CASCADE
);
