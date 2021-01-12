package com.masivian.roulette.repository;

import com.masivian.roulette.entity.Roulette;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouletteRepository {

    Integer saveAndGetId(Roulette roulette);

    Integer update(Roulette roulette);

    Roulette findById(int id);

    List<Roulette> findAll();

}
