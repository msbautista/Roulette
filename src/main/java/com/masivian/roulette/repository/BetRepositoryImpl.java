package com.masivian.roulette.repository;

import com.masivian.roulette.entity.Bet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class BetRepositoryImpl implements BetRepository {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public BetRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Bet> betRowMapper = (resultSet, i) -> new Bet(
            resultSet.getInt("id"),
            resultSet.getInt("id_roulette"),
            resultSet.getString("stake_value"),
            resultSet.getString("bet_type"),
            resultSet.getBigDecimal("money")
    );

    @Override
    public Integer saveAndGetId(Bet bet) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO bet (id_roulette, stake_value, bet_type, money) VALUES (?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bet.getIdRoulette());
            ps.setString(2, bet.getStakeValue());
            ps.setString(3, bet.getBetType());
            ps.setInt(4, bet.getMoney().intValue());
            return ps;
        }, keyHolder);
        int idBet = keyHolder.getKey().intValue();

        return idBet;
    }

    @Override
    public Bet findById(int id) {
        Object[] args = {id};
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM bet WHERE id = ?", args, betRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Bet> findByIdRoulette(Integer idRoulette) {
        Object[] args = {idRoulette};
        return jdbcTemplate.query("SELECT * FROM bet WHERE id_roulette = ?", args, betRowMapper);
    }
}
