package com.masivian.roulette.controller;

import com.masivian.roulette.exception.BetException;
import com.masivian.roulette.exception.RouletteException;
import com.masivian.roulette.request.BetColorRequest;
import com.masivian.roulette.request.BetNumericRequest;
import com.masivian.roulette.service.BetService;
import com.masivian.roulette.validate.BetValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Controller
@RestController
@RequestMapping(value = "/bet")
public class BetController {

    private BetService betService;
    private BetValidate betValidate;

    @Autowired
    public BetController(BetService betService, BetValidate betValidate) {
        this.betService = betService;
        this.betValidate = betValidate;
    }

    @PostMapping("/new/number")
    public ResponseEntity<Object> newBetNumeric(@Valid @RequestBody BetNumericRequest request) {
        try {
            betValidate.validateBetNumericRequest(request);
            Integer idBet = betService.createBetAndGetId(request);

            return new ResponseEntity<>(idBet, HttpStatus.OK);
        } catch (BetException.NumberOutOfRange |
                BetException.InvalidMoney |
                RouletteException.RouletteIsNotOpen |
                RouletteException.RouletteDoesNotExist rouletteException) {
            return new ResponseEntity<>(rouletteException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/new/color")
    public ResponseEntity<Object> newBetColor(@Valid @RequestBody BetColorRequest request) {
        try {
            betValidate.validateBetColorRequest(request);
            Integer idBet = betService.createBetAndGetId(request);

            return new ResponseEntity<>(idBet, HttpStatus.OK);
        } catch (BetException.InvalidColor |
                BetException.InvalidMoney |
                RouletteException.RouletteIsNotOpen |
                RouletteException.RouletteDoesNotExist rouletteException) {
            return new ResponseEntity<>(rouletteException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}