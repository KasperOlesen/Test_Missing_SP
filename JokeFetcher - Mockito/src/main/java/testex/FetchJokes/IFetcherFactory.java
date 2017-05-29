
package testex.FetchJokes;

import java.util.List;

/**
 *
 * @author kAlex
 */
public interface IFetcherFactory {
    List<String> getAvailableTypes();
    List<IJokeFetcher> getJokeFetchers(String jokesToFetch);
}
