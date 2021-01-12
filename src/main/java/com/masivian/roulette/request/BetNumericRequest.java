package com.masivian.roulette.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class BetNumericRequest {

    @NotNull
    private Integer idRoulette;
    @NotNull
    private Integer number;
    private BigDecimal money;

}
