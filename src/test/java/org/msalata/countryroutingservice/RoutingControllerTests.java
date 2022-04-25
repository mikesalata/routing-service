package org.msalata.countryroutingservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.msalata.countryroutingservice.controller.RoutingController;
import org.msalata.countryroutingservice.exceptions.RouteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@SpringBootTest
class RoutingControllerTests {

    @Autowired
    RoutingController routingController;

    @Test
    void getRoute_ReturnsCorrectRoute_WhenRouteExists() {
        ResponseEntity<List<String>> routeResponse = routingController.getRoute("CZE", "ITA");
        Assertions.assertEquals(HttpStatus.OK, routeResponse.getStatusCode());
        Assertions.assertNotNull(routeResponse.getBody());
        Assertions.assertEquals(3, routeResponse.getBody().size());
        Assertions.assertEquals("CZE", routeResponse.getBody().get(0));
        Assertions.assertEquals("AUT", routeResponse.getBody().get(1));
        Assertions.assertEquals("ITA", routeResponse.getBody().get(2));
    }

    @Test
    void getRoute_ThrowsIllegalArgumentException_WhenRouteDoesntExist() {
        Exception exception = Assertions.assertThrows(RouteNotFoundException.class, () ->
                routingController.getRoute("CZE", "ATA"));
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Route doesn't exist for provided countries."));
    }

    @Test
    void getRouteReturnsIllegalArgumentExceptionWhenOriginDoesntExist() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> routingController.getRoute("AAA", "ITA"));
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Cannot calculate route. Given origin or destination doesn't exist."));
    }

    @Test
    void getRouteReturnsIllegalArgumentExceptionWhenDestinationDoesntExist() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> routingController.getRoute("CZE", "BBB"));
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains("Cannot calculate route. Given origin or destination doesn't exist."));
    }

}
