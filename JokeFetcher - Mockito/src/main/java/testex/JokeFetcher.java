package testex;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import testex.FetchJokes.FetcherFactory;
import testex.FetchJokes.IJokeFetcher;
import testex.FetchJokes.IFetcherFactory;

/**
 * Class used to fetch jokes from a number of external joke API's
 */
public class JokeFetcher {
    private IDateFormatter dateFormatter;
    private IFetcherFactory fetchFactory;
    private final List<String> availableTypes = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");

    public JokeFetcher(IDateFormatter dateFormatter, IFetcherFactory fetchFactory) {
        this.dateFormatter = dateFormatter;
        this.fetchFactory = fetchFactory;
    }

    /**
     * The valid string values to use in a call to getJokes(..)
     *
     * @return All the valid strings that can be used
     */
    public List<String> getAvailableTypes() {
        return availableTypes;
    }

    /**
     * Verifies whether a provided value is a valid string (contained in
     * availableTypes)
     *
     * @param jokeTokens. Example (with valid values only):
     * "EduJoke,ChuckNorris,Moma,Tambal"
     * @return true if the param was a valid value, otherwise false
     */
    public boolean isStringValid(String jokeTokens) {
        String[] tokens = jokeTokens.split(",");
        for (String token : tokens) {
            if (!availableTypes.contains(token)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fetch jokes from external API's as given in the input string -
     * jokesToFetch
     *
     * @param jokesToFetch A comma separated string with values (contained in
     * availableTypes) indicating the jokes to fetch. Example:
     * "EduJoke,ChuckNorris,Moma,Tambal" will return four jokes 
     * @param timeZone. Must be a valid timeZone string as returned by:
     * TimeZone.getAvailableIDs()
     * @return A Jokes instance with the requested jokes + time zone adjusted
     * string representing fetch time (the jokes list can contain null values,
     * if a server did not respond correctly)
     * @throws JokeException. Thrown if either of the two input arguments
     * contains illegal values
     */
    public Jokes getJokes(String jokesToFetch, String timeZone) throws JokeException {
        isStringValid(jokesToFetch);
        Jokes jokes = new Jokes();
        for (IJokeFetcher fetcher : fetchFactory.getJokeFetchers(jokesToFetch)) {
            jokes.addJoke(fetcher.getJoke());
        }
        String tzString = dateFormatter.getFormattedDate(timeZone, new Date());
        jokes.setTimeZoneString(tzString);
        return jokes;
    }

    /**
     * DO NOT TEST this function. It's included only to get a quick way of
     * executing the code
     *
     * @param args
     */
    public static void main(String[] args) throws JokeException {
        JokeFetcher jf = new JokeFetcher(new DateFormatter(), new FetcherFactory());
        Jokes jokes = jf.getJokes("EduJoke,ChuckNorris,Moma,Tambal", "Europe/Copenhagen");
        jokes.getJokes().forEach((joke) -> {
            System.out.println(joke);
        });
        System.out.println("Is String Valid: " + jf.isStringValid("edu_prog,xxx"));
    }
}
