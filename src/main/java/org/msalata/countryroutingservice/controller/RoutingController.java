package org.msalata.countryroutingservice.controller;

import lombok.RequiredArgsConstructor;
import org.msalata.countryroutingservice.service.RouteCalculationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequiredArgsConstructor
public class RoutingController {

    private final RouteCalculationService routeCalculationService;

    @GetMapping("/routing/{origin}/{destination}")
    public ResponseEntity<List<String>> getRoute(@PathVariable("origin") String origin,
                                                 @PathVariable("destination") String destination) {
        return new ResponseEntity<>(routeCalculationService.calculateShortestRoute(origin, destination), HttpStatus.OK);
    }
}
