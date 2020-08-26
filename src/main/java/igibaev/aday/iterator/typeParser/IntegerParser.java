package igibaev.aday.iterator.typeParser;


import igibaev.aday.iterator.exceptions.FieldParserException;

public class IntegerParser implements FieldParser<Integer> {
    @Override
    public Integer parse(String value) throws FieldParserException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new FieldParserException(e.getCause());
        }
    }
}
