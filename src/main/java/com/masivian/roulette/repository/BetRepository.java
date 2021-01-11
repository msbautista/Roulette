package com.masivian.roulette.repository;

import com.masivian.roulette.entity.Bet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository {

    Integer saveAndGetId(Bet bet);

    Bet findById(int id);

    List<Bet> findByIdRoulette(Integer idRoulette);

}
