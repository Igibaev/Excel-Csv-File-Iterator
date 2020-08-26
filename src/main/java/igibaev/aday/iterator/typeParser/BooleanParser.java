package igibaev.aday.iterator.typeParser;


import igibaev.aday.iterator.exceptions.FieldParserException;

public class BooleanParser implements FieldParser<Boolean> {
    @Override
    public Boolean parse(String value) throws FieldParserException {
        return Boolean.parseBoolean(value);
    }
}
