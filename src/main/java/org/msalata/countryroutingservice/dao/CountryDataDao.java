package org.msalata.countryroutingservice.dao;

import java.util.HashSet;
import java.util.Map;

public interface CountryDataDao {
    Map<String, HashSet<String>> getCountryRoutingData();
}
