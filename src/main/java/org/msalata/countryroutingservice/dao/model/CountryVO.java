package org.msalata.countryroutingservice.dao.model;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;

@Getter
public class CountryVO {

    public CountryVO(String name, List<String> borders) {
        this.name = name;
        this.borders = new HashSet<>(borders);
    }

    private final String name;
    private final HashSet<String> borders;
}
