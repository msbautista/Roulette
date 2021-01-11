package com.masivian.roulette.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class BetColorRequest {

    @NotNull
    private Integer idRoulette;
    @NotBlank
    @NotNull
    private String color;
    @NotNull
    private BigDecimal money;

}
