package org.msalata.countryroutingservice.algorithm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

/**
 * Abstract structure that represents connection between nodes.
 */
public class Transition {

    //Node which transistion points to
    private Node node;

    //Distance between 2 nodes which are connected by this transition.
    private Integer distance;
}
