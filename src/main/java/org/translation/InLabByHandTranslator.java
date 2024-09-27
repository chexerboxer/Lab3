package org.translation;

// ADDED java.util.Hashtable
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {

    public static final String CANADA = "can";

    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */
    @Override
    public List<String> getCountryLanguages(String country) {
        // LANGUAGES ADDED: Spanish (es), Japanese (ja), Dutch (nl), Greek (el), Thai (th), Korean (ko)
        if (CANADA.equals(country)) {
            return new ArrayList<>(List.of("de", "en", "zh", "es", "ja", "nl", "el", "th", "ko"));
        }
        return new ArrayList<>();
    }

    /**
     * Returns the country abbreviations for all countries whose translations are
     * available from this Translator.
     *
     * @return list of country abbreviations for which we have translations available
     */
    @Override
    public List<String> getCountries() {
        return new ArrayList<>(List.of(CANADA));
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     *
     * @param country  the country
     * @param language the language
     * @return the name of the country in the given language or null if no translation is available
     */

    @Override
    public String translate(String country, String language) {
        // restructure if-statements
        if (!country.equals(CANADA) || !(getCountries().contains(language))) {
            return null;
        }
        else {
            // create mapping for target languages to translations of Canada
            Hashtable<String, String> translationsForCanada = new Hashtable<>();

            // load values
            translationsForCanada.put("de", "Kanada");
            translationsForCanada.put("en", "Canada");
            translationsForCanada.put("zh", "加拿大");
            translationsForCanada.put("es", "Canadá");
            translationsForCanada.put("ja", "カナダ");
            translationsForCanada.put("nl", "Canada");
            translationsForCanada.put("el", "Καναδάς");
            translationsForCanada.put("th", "แคนาดา");
            translationsForCanada.put("ko", "캐나다");

            return translationsForCanada.get(language);
        }

    }
}
