package com.masivian.roulette.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BetResponse {

    private Integer idBet;
    private BigDecimal money;
    private String result;
    private String betType;

}
