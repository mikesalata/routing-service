package org.msalata.countryroutingservice.exceptions;

public class LoadingDataException extends RuntimeException {
    public LoadingDataException(String message) {
        super("Cannot load country routing data due to: " + message);
    }
}
