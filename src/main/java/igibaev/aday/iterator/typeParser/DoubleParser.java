package igibaev.aday.iterator.typeParser;


import igibaev.aday.iterator.exceptions.FieldParserException;

public class DoubleParser implements FieldParser<Double>{
    @Override
    public Double parse(String value) throws FieldParserException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new FieldParserException(e.getCause());
        }
    }
}
