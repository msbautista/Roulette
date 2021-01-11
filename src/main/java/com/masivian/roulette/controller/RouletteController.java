package com.masivian.roulette.controller;

import com.masivian.roulette.response.BetResponse;
import com.masivian.roulette.exception.RouletteException;
import com.masivian.roulette.service.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping(value = "/roulette", produces = MediaType.APPLICATION_JSON_VALUE)
public class RouletteController {

    private final int SUCCESSFUL_UPDATE = 1;
    private final String SUCCESS_MESSAGE = "OK";
    private final String SERVER_ERROR_MESSAGE = "ERROR";

    private RouletteService rouletteService;

    @Autowired
    public RouletteController(RouletteService rouletteService) {
        this.rouletteService = rouletteService;
    }

    @GetMapping("/new")
    public Integer newRoulette() {
        return rouletteService.newRoulette();
    }

    @GetMapping("/open/{id}")
    public ResponseEntity<Object> openRoulette(@PathVariable Integer id) {
        try {
            Integer roulette = rouletteService.openRoulette(id);
            if (roulette.equals(SUCCESSFUL_UPDATE)) {
                return new ResponseEntity<>(SUCCESS_MESSAGE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (RouletteException.RouletteDoesNotExist rouletteDoesNotExist) {
            return new ResponseEntity<>(rouletteDoesNotExist.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/close/{id}")
    public ResponseEntity<Object> closeRoulette(@PathVariable Integer id) {
        try {
            List<BetResponse> betResponses = rouletteService.closeRoulette(id);
            return new ResponseEntity<>(betResponses, HttpStatus.OK);
        } catch (RouletteException.RouletteDoesNotExist | RouletteException.RouletteIsClosed rouletteDoesNotExist) {
            return new ResponseEntity<>(rouletteDoesNotExist.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}