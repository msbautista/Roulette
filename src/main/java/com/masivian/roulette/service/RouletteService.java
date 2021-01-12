package com.masivian.roulette.service;

import com.masivian.roulette.entity.*;
import com.masivian.roulette.response.BetResponse;
import com.masivian.roulette.exception.RouletteException;
import com.masivian.roulette.repository.BetRepository;
import com.masivian.roulette.entity.RouletteState;
import com.masivian.roulette.repository.RouletteRepository;
import com.masivian.roulette.response.RouletteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouletteService {

    private final Integer MAX_NUMBER_ROULETTE = 36;
    private final Integer MULTIPLY_BET_NUMBER = 5;
    private final double MULTIPLY_BET_COLOR = 1.8;

    private RouletteRepository rouletteRepository;
    private BetRepository betRepository;

    @Autowired
    public RouletteService(RouletteRepository rouletteRepository, BetRepository betRepository) {
        this.rouletteRepository = rouletteRepository;
        this.betRepository = betRepository;
    }

    public Integer newRoulette(){
        Roulette roulette = new Roulette();
        roulette.setState(RouletteState.CREATED.name());

        return rouletteRepository.saveAndGetId(roulette);
    }

    public List<RouletteResponse> getAllRoulettes(){
        List<Roulette> all = rouletteRepository.findAll();
        List<RouletteResponse> responseList = new ArrayList<>();
        for (Roulette roulette: all) {
            RouletteResponse rouletteResponse = new RouletteResponse();
            rouletteResponse.setId(roulette.getId());
            rouletteResponse.setState(roulette.getState());
            rouletteResponse.setRandomNumber(roulette.getRandomNumber());
            responseList.add(rouletteResponse);
        }
        return responseList;
    }

    public Integer openRoulette(Integer id) throws RouletteException.RouletteDoesNotExist {
        Roulette byId = rouletteRepository.findById(id);
        if(byId == null){
            throw new RouletteException.RouletteDoesNotExist();
        }
        Roulette roulette = new Roulette();
        roulette.setId(id);
        roulette.setState(RouletteState.OPEN.name());

        return rouletteRepository.update(roulette);
    }

    public List<BetResponse> closeRoulette(Integer id) throws RouletteException.RouletteDoesNotExist, RouletteException.RouletteIsClosed {
        Roulette byId = rouletteRepository.findById(id);
        if(byId == null){
            throw new RouletteException.RouletteDoesNotExist();
        }
        if(byId.getState().equals(RouletteState.CLOSED.name())){
            throw new RouletteException.RouletteIsClosed();
        }
        Integer randomNumber = getRandomNumber();
        Roulette roulette = new Roulette();
        roulette.setId(id);
        roulette.setState(RouletteState.CLOSED.name());
        roulette.setRandomNumber(randomNumber);
        rouletteRepository.update(roulette);
        List<Bet> bets = betRepository.findByIdRoulette(id);
        List<BetResponse> results = getResultsByBetsAndRandomNumber(bets, randomNumber);

        return results;
    }

    private List<BetResponse> getResultsByBetsAndRandomNumber(List<Bet> bets, int numberRandom){
        BetColor colorThrown = getColorByNumber(numberRandom);
        List<BetResponse> results = new ArrayList<>();
        for (Bet bet : bets) {
            if(bet.getBetType().equals(BetType.COLOR.name())){
                BetResponse betResponseByColor = getBetResponseByColor(bet, colorThrown);
                results.add(betResponseByColor);
            }
            if (bet.getBetType().equals(BetType.NUMBER.name())){
                BetResponse betNumericResult = getBetNumericResult(bet, numberRandom);
                results.add(betNumericResult);
            }
        }

        return results;
    }

    private Integer getRandomNumber(){
        return (int) (Math.random() * MAX_NUMBER_ROULETTE);
    }

    private BetColor getColorByNumber(int number){
        if(isPair(number)){
            return BetColor.RED;
        } else {
            return BetColor.BLACK;
        }
    }

    private boolean isPair(int number){
        return number % 2 == 0;
    }

    private BetResponse getBetResponseByColor(Bet bet, BetColor colorThrown){
        BetResponse response;
        if(bet.getStakeValue().equals(colorThrown.name())){
            response = getWinnerBetResponse(bet);
        } else {
            response = getLoserBetResponse(bet);
        }

        return response;
    }

    private BetResponse getBetNumericResult(Bet bet, int numberRandom){
        BetResponse response;
        if(bet.getStakeValue().equals(numberRandom)){
            response = getWinnerBetResponse(bet);
        } else {
            response = getLoserBetResponse(bet);
        }

        return response;
    }

    private BetResponse getWinnerBetResponse(Bet bet){
        BetResponse response = new BetResponse();
        response.setIdBet(bet.getId());
        response.setResult(BetResult.WINNER.name());
        response.setBetType(bet.getBetType());
        BigDecimal earnedMoney = getEarnedMoney(bet.getMoney(), BetType.valueOf(bet.getBetType()));
        response.setMoney(earnedMoney);

        return response;
    }

    private BigDecimal getEarnedMoney(BigDecimal moneyBet, BetType betType){
        if(betType == BetType.NUMBER){
            return moneyBet.multiply(BigDecimal.valueOf(MULTIPLY_BET_NUMBER));
        } else {
            return moneyBet.multiply(BigDecimal.valueOf(MULTIPLY_BET_COLOR));
        }
    }

    private BetResponse getLoserBetResponse(Bet bet){
        BetResponse response = new BetResponse();
        response.setIdBet(bet.getId());
        response.setResult(BetResult.LOSER.name());
        response.setBetType(bet.getBetType());
        response.setMoney(BigDecimal.ZERO);

        return response;
    }

}