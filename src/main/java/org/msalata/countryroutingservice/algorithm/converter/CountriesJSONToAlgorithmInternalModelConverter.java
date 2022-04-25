package org.msalata.countryroutingservice.algorithm.converter;

import org.msalata.countryroutingservice.algorithm.model.Node;
import org.msalata.countryroutingservice.algorithm.model.Transition;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CountriesJSONToAlgorithmInternalModelConverter {

    /**
     * Countries JSON model doesn't provide distances between countries, so it's
     * assumed that distance between any 2 adjacent countries is equal to 1.
     */
    public static final int ADJACENT_COUNTRIES_DISTANCE = 1;

    public final Map<String, Node> convert(final Map<String, HashSet<String>> countryRoutingData) {

        Map<String, Node> convertedNodeData = new HashMap<>();
        countryRoutingData.keySet().forEach(
                key -> convertedNodeData.put(key, new Node(key, null, null, Integer.MAX_VALUE)));
        convertedNodeData.keySet().forEach(key -> convertedNodeData.get(key).setTransitions(
                countryRoutingData.get(key).stream()
                        .map(adjacentCountryKey -> new Transition(convertedNodeData.get(adjacentCountryKey), ADJACENT_COUNTRIES_DISTANCE))
                        .collect(Collectors.toList())
        ));
        return convertedNodeData;
    }
}
