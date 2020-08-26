package igibaev.aday.iterator.typeParser;

import igibaev.aday.iterator.exceptions.FieldParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class LocalDateTimeParser implements FieldParser<LocalDateTime> {
    private final Logger logger = LoggerFactory.getLogger(LocalDateTimeParser.class);
    private final String DATE_PATTERN = "MMM d, u h:m:s a";
    private final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern(DATE_PATTERN)
            .toFormatter(Locale.ENGLISH);
    @Override
    public LocalDateTime parse(String value) throws FieldParserException {
        try {
            return LocalDateTime.parse(value, formatter);
        } catch (DateTimeParseException e) {
            logger.warn("cannot parse date:[{}], date pattern:[{}]", value, DATE_PATTERN);
            throw new FieldParserException(e.getCause());
        }
    }
}
