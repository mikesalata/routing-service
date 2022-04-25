package org.msalata.countryroutingservice.algorithm;

import org.msalata.countryroutingservice.algorithm.model.Node;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Maciej Sałata
 */

@Component
@Qualifier("dijkstra")
public class DijkstraRouteAlgorithm implements RouteAlgorithm {

    /**
     * My own implementation of the famous Dijkstra's algorithm: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     * which calculates all shortest paths from origin to other reachable nodes.
     *
     * @param origin starting point
     * @param destination finishing point
     * @param nodesMap model that represents nodes and connections between them (transitions)
     * @return route between origin and destination like ["CZE", "AUT", "ITA"] for given origin:"CZE" and destination:"ITA".
     *
     */
    @Override
    public List<String> calculateRoute(final String origin, final String destination, final Map<String, Node> nodesMap) {

        PriorityQueue<Node> nodesMinimumPriorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getTotalDistance));
        Set<String> visitedNodes = new HashSet<>();

        nodesMap.get(origin).setPrev(null);
        nodesMap.get(origin).setTotalDistance(0);
        nodesMinimumPriorityQueue.add(nodesMap.get(origin));

        while (!nodesMinimumPriorityQueue.isEmpty()) {
            Node visitingNode = nodesMinimumPriorityQueue.poll();
            visitedNodes.add(visitingNode.getName());
            visitingNode.getTransitions().forEach(transition -> {
                Integer currentTransitionNodeTotalDistance = transition.getNode().getTotalDistance();
                Integer totalDistanceToTransitionNodeViaVisiting = visitingNode.getTotalDistance() + transition.getDistance();
                if (currentTransitionNodeTotalDistance > totalDistanceToTransitionNodeViaVisiting) {
                    transition.getNode().setTotalDistance(totalDistanceToTransitionNodeViaVisiting);
                    transition.getNode().setPrev(visitingNode);
                }
                if (!visitedNodes.contains(transition.getNode().getName())) {
                    nodesMinimumPriorityQueue.add(transition.getNode());
                }
            });
        }
        return calculateFinalRouteToDestination(nodesMap.get(destination));
    }

    private List<String> calculateFinalRouteToDestination(Node node) {
        final List<String> route = new ArrayList<>();
        while (node.getPrev() != null) {
            route.add(0, node.getName());
            node = node.getPrev();
        }
        route.add(0, node.getName());
        return route;
    }
}
