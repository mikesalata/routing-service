package org.msalata.countryroutingservice.dao;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.msalata.countryroutingservice.dao.model.CountryVO;
import org.msalata.countryroutingservice.exceptions.LoadingDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JsonCountryDataDao implements CountryDataDao {

    Logger logger = LoggerFactory.getLogger(JsonCountryDataDao.class);

    public static final String COUNTRY_NAME_JSON_PROPERTY = "cca3";
    public static final String ADJACENT_COUNTRIES_JSON_PROPERTY = "borders";

    @Value("${routing.data.location}")
    String jsonLocation;

    private HashMap<String, HashSet<String>> countryRoutingData = null;

    @SuppressWarnings("unchecked")
    public Map<String, HashSet<String>> getCountryRoutingData() {

        if (countryRoutingData == null) {
            try {
                List<HashMap<String, Object>> jsonCountries = (List<HashMap<String, Object>>) new JSONParser()
                        .parse(new FileReader(jsonLocation));
                countryRoutingData = (HashMap<String, HashSet<String>>) jsonCountries.stream()
                        .map(jsonCountry -> new CountryVO(
                                (String) jsonCountry.get(COUNTRY_NAME_JSON_PROPERTY),
                                (List<String>) jsonCountry.get(ADJACENT_COUNTRIES_JSON_PROPERTY)))
                        .collect(Collectors.toMap(CountryVO::getName, CountryVO::getBorders));
            } catch (IOException | ParseException e) {
                logger.error(e.getMessage(), e);
                throw new LoadingDataException(e.getMessage());
            }
        }
        return countryRoutingData;
    }
}
