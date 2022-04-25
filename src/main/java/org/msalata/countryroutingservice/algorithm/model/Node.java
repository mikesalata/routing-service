package org.msalata.countryroutingservice.algorithm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class Node {
    //Node name.
    private String name;

    //List of adjacent nodes with additional distance information.
    private List<Transition> transitions;

    //Previous node.
    private Node prev;

    //Total calculated distance from origin (route starting point).
    private Integer totalDistance;
}
