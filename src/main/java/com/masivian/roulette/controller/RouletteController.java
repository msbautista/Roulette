package com.masivian.roulette.controller;

import com.masivian.roulette.response.ApiResponse;
import com.masivian.roulette.response.BetResponse;
import com.masivian.roulette.exception.RouletteException;
import com.masivian.roulette.response.RouletteResponse;
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
@RequestMapping(value = "/roulette")
public class RouletteController {

    private final int SUCCESSFUL_UPDATE = 1;
    private final String SUCCESS_MESSAGE = "successful operation";
    private final String SERVER_ERROR_MESSAGE = "error during operation";

    private RouletteService rouletteService;

    @Autowired
    public RouletteController(RouletteService rouletteService) {
        this.rouletteService = rouletteService;
    }

    @GetMapping("/new")
    public ResponseEntity<ApiResponse> newRoulette() {
        Integer rouletteId = rouletteService.newRoulette();

        return new ApiResponse(rouletteId).send(HttpStatus.OK);
    }

    @GetMapping("/open/{id}")
    public ResponseEntity<Object> openRoulette(@PathVariable Integer id) {
        try {
            Integer roulette = rouletteService.openRoulette(id);
            if (roulette.equals(SUCCESSFUL_UPDATE)) {

                return new ApiResponse(SUCCESS_MESSAGE).send(HttpStatus.OK, null);
            } else {

                return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, SERVER_ERROR_MESSAGE);
            }
        } catch (RouletteException.RouletteDoesNotExist ex) {

            return new ApiResponse().send(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/close/{id}")
    public ResponseEntity<Object> closeRoulette(@PathVariable Integer id) {
        try {
            List<BetResponse> betResponses = rouletteService.closeRoulette(id);

            return new ApiResponse(betResponses).send(HttpStatus.OK, null);
        } catch (RouletteException.RouletteDoesNotExist | RouletteException.RouletteIsClosed ex) {

            return new ApiResponse().send(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRoulettes(){
        List<RouletteResponse> allRoulettes = rouletteService.getAllRoulettes();

        return new ApiResponse(allRoulettes).send(HttpStatus.OK, null);
    }


}