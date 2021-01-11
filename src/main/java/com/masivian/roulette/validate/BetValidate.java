package com.masivian.roulette.validate;

import com.masivian.roulette.entity.BetColor;
import com.masivian.roulette.exception.BetException;
import com.masivian.roulette.request.BetColorRequest;
import com.masivian.roulette.request.BetNumericRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BetValidate {

    private final int MAX_BET_VALUE = 36;
    private final int MIN_BET_VALUE = 0;
    private final int MAX_BET_MONEY = 10000;
    private final int MIN_BET_MONEY = 0;
    private final int MONEY_IS_LESS_THAN_VALUE_PASSED = -1;
    private final int MONEY_IS_GREATER_THAN_VALUE_PASSED = 1;

    public void validateBetColorRequest(BetColorRequest request) throws BetException.InvalidColor, BetException.InvalidMoney {
        String color = request.getColor().toUpperCase();
        List<String> betColors = Stream.of(BetColor.values())
                                        .map(BetColor::name)
                                        .collect(Collectors.toList());
        if(!betColors.contains(color)){
            throw new BetException.InvalidColor();
        }
        BigDecimal money = request.getMoney();
        if(moneyIsLessThanMin(money) || moneyIsGreaterThanMax(money)){
            throw new BetException.InvalidMoney();
        }
    }

    public void validateBetNumericRequest(BetNumericRequest request) throws BetException.NumberOutOfRange, BetException.InvalidMoney {
        if(request.getNumber() < MIN_BET_VALUE || request.getNumber() > MAX_BET_VALUE){
            throw new BetException.NumberOutOfRange();
        }
        BigDecimal money = request.getMoney();
        if(moneyIsLessThanMin(money) || moneyIsGreaterThanMax(money)){
            throw new BetException.InvalidMoney();
        }
    }

    private boolean moneyIsLessThanMin(BigDecimal money){
        return money.compareTo(BigDecimal.valueOf(MIN_BET_MONEY)) == MONEY_IS_LESS_THAN_VALUE_PASSED;
    }

    private boolean moneyIsGreaterThanMax(BigDecimal money){
        return money.compareTo(BigDecimal.valueOf(MAX_BET_MONEY)) == MONEY_IS_GREATER_THAN_VALUE_PASSED;
    }

}
