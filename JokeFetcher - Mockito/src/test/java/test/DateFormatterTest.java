/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Date;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;
import static org.junit.Assert.*;
import testex.DateFormatter;
import testex.JokeException;

/**
 *
 * @author kAlex
 */
public class DateFormatterTest {

    @Test
    public void testFormattedDate() throws Exception {
        String expected = "24 maj 2017 07:46 AM";
        Date time = new Date(1495604817531L);
        DateFormatter dateFormatter = new DateFormatter();
        String formattedDate = dateFormatter.getFormattedDate("Europe/Paris", time);
        System.out.println("formatted date:");
        System.out.println(formattedDate);
        assertThat(formattedDate, equalTo(expected));
    }

    @Test(expected = JokeException.class)
    public void testFormattedDateException() throws Exception {
        Date time = new Date();
        DateFormatter dateFormatter = new DateFormatter();
        dateFormatter.getFormattedDate("Unknown", time);
    }

}
