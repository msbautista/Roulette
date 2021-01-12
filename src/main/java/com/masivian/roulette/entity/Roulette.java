package com.masivian.roulette.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roulette {

    private Integer id;
    private String state;
    private Integer randomNumber;

}
