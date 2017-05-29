package testex;

import java.util.Date;

/**
 *
 * @author kAlex
 */
public interface IDateFormatter {
    String getFormattedDate(String timeZone, Date time) throws JokeException;
}
