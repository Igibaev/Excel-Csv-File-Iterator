package igibaev.aday.iterator.annotations;


import igibaev.aday.iterator.typeParser.FieldParser;
import igibaev.aday.iterator.typeParser.StringParser;

public @interface FieldAttributes {
    String name();
    Class<? extends FieldParser> parser() default StringParser.class;
    boolean isRequired() default true;
}
