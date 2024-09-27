package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String exit = "quit";

            String country = promptForCountry(translator);
            if (exit.equals(country)) {
                break;
            }

            String language = promptForLanguage(translator, country);
            if (language.equals(exit)) {
                break;
            }

            CountryCodeConverter ccon = new CountryCodeConverter();
            LanguageCodeConverter lcon = new LanguageCodeConverter();
            String ccode = ccon.fromCountry(country);
            String lcode = lcon.fromLanguage(language);

            System.out.println(country + " in " + language + " is " + translator.translate(ccode, lcode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (exit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        ArrayList<String> fullNameCountries = new ArrayList<>();
        CountryCodeConverter converter = new CountryCodeConverter();

        // convert codes to country names
        for (String country : countries) {
            fullNameCountries.add(converter.fromCountryCode(country));
        }

        // sort alphabetically
        Collections.sort(fullNameCountries);

        for (String country : fullNameCountries) {
            System.out.println(country);
        }
        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        CountryCodeConverter cconverter = new CountryCodeConverter();
        String countryCode = cconverter.fromCountry(country);

        List<String> langs = translator.getCountryLanguages(countryCode);
        ArrayList<String> fullNameLanguages = new ArrayList<String>();
        LanguageCodeConverter converter = new LanguageCodeConverter();

        // convert codes to country names
        for (String lang : langs) {
            fullNameLanguages.add(converter.fromLanguageCode(lang));
        }

        // sort alphabetically
        Collections.sort(fullNameLanguages);

        for (String lang : fullNameLanguages) {
            System.out.println(lang);
        }
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

}
