CREATE TABLE tb_game_info (
    id SERIAL PRIMARY KEY,
    player_id UUID NOT NULL,
    points INT NOT NULL,
    assists INT NOT NULL,
    rebounds INT NOT NULL,
    game_id UUID NOT NULL,
    CONSTRAINT fk_player
        FOREIGN KEY(player_id)
        REFERENCES tb_players(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_game
        FOREIGN KEY(game_id)
        REFERENCES tb_games(id)
        ON DELETE CASCADE
);