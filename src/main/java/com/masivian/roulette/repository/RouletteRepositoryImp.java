package com.masivian.roulette.repository;

import com.masivian.roulette.entity.Roulette;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class RouletteRepositoryImp implements RouletteRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public RouletteRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Roulette> rouletteRowMapper = (resultSet, i) -> new Roulette(
            resultSet.getInt("id"),
            resultSet.getString("state"),
            resultSet.getInt("random_number")
    );

    @Override
    public Integer saveAndGetId(Roulette roulette) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO roulette (state, random_number) VALUES (?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, roulette.getState());
            ps.setObject(2, roulette.getRandomNumber());
            return ps;
        }, keyHolder);
        int idRoulette = keyHolder.getKey().intValue();

        return idRoulette;
    }

    @Override
    public Integer update(Roulette roulette) {
        return jdbcTemplate.update("UPDATE roulette SET state = ?, random_number = ? WHERE id = ?",
                roulette.getState(),
                roulette.getRandomNumber(),
                roulette.getId()
        );
    }

    @Override
    public Roulette findById(int id) {
        Object[] args = {id};
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM roulette WHERE id = ?", args, rouletteRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

}
