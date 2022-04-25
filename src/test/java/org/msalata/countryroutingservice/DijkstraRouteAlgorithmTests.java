package org.msalata.countryroutingservice;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.msalata.countryroutingservice.algorithm.DijkstraRouteAlgorithm;
import org.msalata.countryroutingservice.algorithm.RouteAlgorithm;
import org.msalata.countryroutingservice.algorithm.model.Node;
import org.msalata.countryroutingservice.algorithm.model.Transition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijkstraRouteAlgorithmTests {

    @Test
    public void calculateRoute_ReturnsCorrectRoute_WhenRouteIsPossible() {
        final Map<String, Node> nodesMap = prepareNodesData();
        RouteAlgorithm routeAlgorithm = new DijkstraRouteAlgorithm();
        List<String> calculatedRoute = routeAlgorithm.calculateRoute("SF", "NYC", nodesMap);
        List<String> expectedRoute = Lists.newArrayList("SF", "Seattle", "Chicago", "NYC");
        Assertions.assertEquals(expectedRoute, calculatedRoute);
        Assertions.assertEquals(9, nodesMap.get("NYC").getTotalDistance());
    }

    @Test
    public void calculateRoute_ReturnsOnlyDestination_WhenRouteIsNotPossible() {
        final Map<String, Node> nodesMap = prepareNodesData();
        RouteAlgorithm routeAlgorithm = new DijkstraRouteAlgorithm();
        List<String> calculatedRoute = routeAlgorithm.calculateRoute("SF", "Portland", nodesMap);
        List<String> expectedRoute = Lists.newArrayList("Portland");
        Assertions.assertEquals(expectedRoute, calculatedRoute);
        Assertions.assertNull(nodesMap.get("Portland").getPrev());
    }

    @Test
    public void calculateRoute_ReturnsOnlyDestination_WhenSourceAndDestinationAreSame() {
        final Map<String, Node> nodesMap = prepareNodesData();
        RouteAlgorithm routeAlgorithm = new DijkstraRouteAlgorithm();
        List<String> calculatedRoute = routeAlgorithm.calculateRoute("SF", "SF", nodesMap);
        List<String> expectedRoute = Lists.newArrayList("SF");
        Assertions.assertEquals(expectedRoute, calculatedRoute);
        Assertions.assertNull(nodesMap.get("SF").getPrev());
        Assertions.assertEquals(0, nodesMap.get("SF").getTotalDistance());
    }

    private static Map<String, Node> prepareNodesData() {
        Node sf = new Node("SF", null, null, Integer.MAX_VALUE);
        Node seattle = new Node("Seattle", null, null, Integer.MAX_VALUE);
        Node idaho = new Node("Idaho", null, null, Integer.MAX_VALUE);
        Node chicago = new Node("Chicago", null, null, Integer.MAX_VALUE);
        Node nyc = new Node("NYC", null, null, Integer.MAX_VALUE);
        Node portland = new Node("Portland", null, null, Integer.MAX_VALUE);

        sf.setTransitions(List.of(new Transition(seattle, 3), new Transition(idaho, 5)));
        seattle.setTransitions(List.of(new Transition(chicago, 2), new Transition(sf, 3), new Transition(idaho, 1)));
        idaho.setTransitions(List.of(new Transition(sf, 5), new Transition(seattle, 1), new Transition(chicago, 3), new Transition(nyc, 6)));
        chicago.setTransitions(List.of(new Transition(seattle, 2), new Transition(idaho, 3), new Transition(nyc, 4)));
        nyc.setTransitions(List.of(new Transition(chicago, 4), new Transition(idaho, 6)));

        final Map<String, Node> nodesMap = new HashMap<>();
        nodesMap.put(seattle.getName(), seattle);
        nodesMap.put(sf.getName(), sf);
        nodesMap.put(idaho.getName(), idaho);
        nodesMap.put(chicago.getName(), chicago);
        nodesMap.put(nyc.getName(), nyc);
        nodesMap.put(portland.getName(), portland);
        return nodesMap;
    }
}
