package org.msalata.countryroutingservice.service;

import lombok.RequiredArgsConstructor;
import org.msalata.countryroutingservice.algorithm.RouteAlgorithm;
import org.msalata.countryroutingservice.algorithm.converter.CountriesJSONToAlgorithmInternalModelConverter;
import org.msalata.countryroutingservice.dao.CountryDataDao;
import org.msalata.countryroutingservice.exceptions.RouteNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RouteCalculationService {

    @Qualifier("dijkstra")
    private final RouteAlgorithm routeAlgorithm;

    private final CountryDataDao countryDataDao;

    private final CountriesJSONToAlgorithmInternalModelConverter internalModelConverter;

    public List<String> calculateShortestRoute(final String origin, final String destination) {
        Map<String, HashSet<String>> countryRoutingData = countryDataDao.getCountryRoutingData();
        if (origin == null || countryRoutingData.get(origin) == null || destination == null || countryRoutingData.get(destination) == null) {
            throw new IllegalArgumentException("Cannot calculate route. Given origin or destination doesn't exist.");
        }
        List<String> calculatedPath = routeAlgorithm.calculateRoute(origin, destination, internalModelConverter.convert(countryRoutingData));
        if (!origin.equals(destination) && (calculatedPath == null || calculatedPath.size() == 1) ) {
            throw new RouteNotFoundException("Route doesn't exist for provided countries.");
        }
        return calculatedPath;
    }
}
