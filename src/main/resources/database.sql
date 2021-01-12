CREATE DATABASE roulette_game;

CREATE TABLE roulette(
	id INT NOT NULL AUTO_INCREMENT,
	state VARCHAR(20) NULL,
	random_number INT NULL,
	PRIMARY KEY (id)
)

CREATE TABLE bet(
	id INT NOT NULL AUTO_INCREMENT,
	id_roulette INT NOT NULL,
	stake_value VARCHAR(20) NOT NULL,
	bet_type VARCHAR(20) NOT NULL,
	money INT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (id_roulette) REFERENCES roulette(id)
)