package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final Map<String, Map<String, String>> jsonMapping = new HashMap<>();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // use hashmap that maps country codes to another hashmap that maps language codes to translations
            int i;
            for (i = 0; i < jsonArray.length(); i++) {
                // split individual mappings
                JSONObject countryInfo = jsonArray.getJSONObject(i);

                // create translation mapping
                Map<String, String> translationMapping = new HashMap<>();

                for (String key : countryInfo.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        translationMapping.put(key, countryInfo.getString(key));
                    }
                }
                jsonMapping.put(countryInfo.getString("alpha3"), translationMapping);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        ArrayList<String> langs = new ArrayList<>();
        for (String countryCode : jsonMapping.keySet()) {
            if (countryCode.equals(country)) {
                return new ArrayList<>(jsonMapping.get(countryCode).keySet());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(jsonMapping.keySet());
    }

    @Override
    public String translate(String country, String language) {
        for (String countryCode : jsonMapping.keySet()) {
            if (countryCode.equals(country)) {
                return jsonMapping.get(countryCode).get(language);
            }
        }
        return "Couldn't find translation";
    }
}
