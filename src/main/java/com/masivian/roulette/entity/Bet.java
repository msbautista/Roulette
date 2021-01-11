package com.masivian.roulette.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bet {

    private Integer id;
    private Integer idRoulette;
    private String stakeValue;
    private String betType;
    private BigDecimal money;

}