package org.msalata.countryroutingservice.algorithm;

import org.msalata.countryroutingservice.algorithm.model.Node;

import java.util.List;
import java.util.Map;

public interface RouteAlgorithm {
    List<String> calculateRoute(final String origin, final String destination, final Map<String, Node> nodesMap);
}
