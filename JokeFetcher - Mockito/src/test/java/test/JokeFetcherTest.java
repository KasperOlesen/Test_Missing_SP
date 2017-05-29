package test;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import testex.FetchJokes.ChuckNorris;
import testex.FetchJokes.EduJoke;
import testex.FetchJokes.FetcherFactory;
import testex.FetchJokes.IJokeFetcher;
import testex.FetchJokes.Moma;
import testex.FetchJokes.Tambal;
import testex.IDateFormatter;
import testex.Joke;
import testex.JokeFetcher;
import testex.Jokes;


/**
 *
 * @author kAlex
 */
@RunWith(MockitoJUnitRunner.class)
public class JokeFetcherTest {

    @Mock private IDateFormatter dateFormatter;
    @Mock private FetcherFactory fetcherFactory;
    @Mock private EduJoke eduJoke;
    @Mock private ChuckNorris chuckNorrisJoke;
    @Mock private Moma momaJoke;
    @Mock private Tambal tambalJoke;

    private JokeFetcher fetcher;
    private Joke mockJoke;

    @Before
    public void setUp() throws Exception {
        mockJoke = new Joke("mock joke", "funny");
        given(eduJoke.getJoke()).willReturn(mockJoke);
        given(chuckNorrisJoke.getJoke()).willReturn(mockJoke);
        given(momaJoke.getJoke()).willReturn(mockJoke);
        given(tambalJoke.getJoke()).willReturn(mockJoke);
        fetcher = new JokeFetcher(dateFormatter, fetcherFactory);
        
        List<IJokeFetcher> fetchers = Arrays.asList(eduJoke, chuckNorrisJoke, momaJoke, tambalJoke);
        when(fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal")).thenReturn(fetchers);
        List<String> types = Arrays.asList("EduJoke", "ChuckNorris", "Moma", "Tambal");
        when(fetcherFactory.getAvailableTypes()).thenReturn(types);


    }

    @Test
    public void testGetAvailableTypes() {
        List<String> types = fetcher.getAvailableTypes();
        assertThat(types, containsInAnyOrder("EduJoke", "ChuckNorris", "Moma", "Tambal"));
    }

    @Test
    public void checkIfValidToken() {
        boolean valid = fetcher.isStringValid("EduJoke,ChuckNorris,Moma,Tambal");
        boolean invalid = fetcher.isStringValid("asd,qwe,rty,123");
        assertThat(valid, is(true));
        assertThat(invalid, is(false));
    }

    @Test
    public void testGetJokes() throws Exception {
        given(dateFormatter.getFormattedDate(eq("Europe/Copenhagen"), anyObject())).willReturn("25 maj. 2017 11:20 AM");
        Jokes jokes = fetcher.getJokes("EduJoke,ChuckNorris,Moma,Tambal", "Europe/Copenhagen");
        assertThat(jokes.getTimeZoneString(), is("25 maj. 2017 11:20 AM"));
        verify(dateFormatter, times(1)).getFormattedDate(anyObject(), anyObject());
        List<Joke> list = jokes.getJokes();
        assertThat(list, hasSize(4));
        assertThat(jokes.getTimeZoneString(), is("25 maj. 2017 11:20 AM"));
        verify(dateFormatter, times(1)).getFormattedDate(anyObject(), anyObject());
        verify(fetcherFactory, times(1)).getJokeFetchers(anyString());

        for(Joke joke : list) {
            assertThat(joke.getJoke(), equalTo(mockJoke.getJoke()));
            assertThat(joke.getReference(), equalTo(mockJoke.getReference()));
        }
        verify(eduJoke, times(1)).getJoke();
        verify(chuckNorrisJoke, times(1)).getJoke();
        verify(momaJoke, times(1)).getJoke();
        verify(tambalJoke, times(1)).getJoke();
    }

    @Test
    public void testFetcher() {
        List<IJokeFetcher> result = fetcherFactory.getJokeFetchers("EduJoke,ChuckNorris,Moma,Tambal");
        assertThat(result, hasSize(4));
        result.forEach((fetch) -> assertThat(fetch, instanceOf(IJokeFetcher.class)));
    }

}
