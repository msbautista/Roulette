package com.masivian.roulette.service;

import com.masivian.roulette.entity.Bet;
import com.masivian.roulette.entity.BetType;
import com.masivian.roulette.entity.Roulette;
import com.masivian.roulette.exception.RouletteException;
import com.masivian.roulette.repository.BetRepository;
import com.masivian.roulette.repository.RouletteRepository;
import com.masivian.roulette.request.BetColorRequest;
import com.masivian.roulette.request.BetNumericRequest;
import com.masivian.roulette.validate.BetValidate;
import com.masivian.roulette.entity.RouletteState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetService {

    private BetRepository betRepository;
    private RouletteRepository rouletteRepository;
    private BetValidate betValidate;

    @Autowired
    public BetService(BetRepository betRepository, RouletteRepository rouletteRepository, BetValidate betValidate) {
        this.betRepository = betRepository;
        this.rouletteRepository = rouletteRepository;
        this.betValidate = betValidate;
    }

    public Integer createBetAndGetId(BetNumericRequest request) throws RouletteException.RouletteIsNotOpen, RouletteException.RouletteDoesNotExist {
        validateRoulette(request.getIdRoulette());
        Bet bet = new Bet();
        bet.setIdRoulette(request.getIdRoulette());
        bet.setStakeValue(request.getNumber().toString());
        bet.setBetType(BetType.NUMBER.name());
        bet.setMoney(request.getMoney());

        return betRepository.saveAndGetId(bet);
    }

    public Integer createBetAndGetId(BetColorRequest request) throws RouletteException.RouletteIsNotOpen, RouletteException.RouletteDoesNotExist {
        validateRoulette(request.getIdRoulette());
        Bet bet = new Bet();
        bet.setIdRoulette(request.getIdRoulette());
        bet.setStakeValue(request.getColor().toUpperCase());
        bet.setBetType(BetType.COLOR.name());
        bet.setMoney(request.getMoney());

        return betRepository.saveAndGetId(bet);
    }

    private void validateRoulette(int idRoulette) throws RouletteException.RouletteDoesNotExist, RouletteException.RouletteIsNotOpen {
        Roulette byId = rouletteRepository.findById(idRoulette);
        if (byId == null) {
            throw new RouletteException.RouletteDoesNotExist();
        }
        String state = byId.getState();
        if (!state.equals(RouletteState.OPEN.name())) {
            throw new RouletteException.RouletteIsNotOpen();
        }
    }
}

