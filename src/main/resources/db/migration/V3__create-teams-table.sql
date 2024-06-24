CREATE TABLE tb_teams (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    club_id UUID NOT NULL,
    CONSTRAINT fk_club
        FOREIGN KEY(club_id)
        REFERENCES tb_clubs(id)
        ON DELETE CASCADE
);