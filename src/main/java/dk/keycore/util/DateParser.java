package dk.keycore.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {

    final static SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy | HH:mm:ss", Locale.ENGLISH);
    final static SimpleDateFormat writeFormatter = new SimpleDateFormat("EEEE MMM dd yyyy | HH:mm:ss",Locale.ENGLISH);
    private static Logger logger = Logger.getLogger(DateParser.class);
    /**
     * Parses the format of 'Friday, March 14, 2014 | 21:36:20' to a Date
     * @param dateValue
     * @return
     */
    public static Date parse(final String dateValue)
    {
        try {

            return formatter.parse(dateValue);
        } catch (ParseException e) {
            logger.error("Error parsing date " + e.getMessage());
            logger.error("Expected format [" + formatter.format(new Date())+"], received ["+dateValue+"]");
            return null;
        }
    }

    public static String dateToString(final Date date)
    {
        return writeFormatter.format(date);
    }




}
