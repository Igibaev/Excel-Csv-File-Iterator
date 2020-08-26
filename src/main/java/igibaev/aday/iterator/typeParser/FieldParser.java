package igibaev.aday.iterator.typeParser;


import igibaev.aday.iterator.exceptions.FieldParserException;

/**
 * Конвертор полей из файлов в типы
 * может быть любой
 * @param <T> преобразуйемый тип
 *           кидает ошибку если тип невозвожно преобразовать в конктреный тип
 */
public interface FieldParser<T> {
    T parse(String value) throws FieldParserException;

    default T parseField(String value) throws FieldParserException {
        value = value.trim();
        if (value.isBlank()) {
            return null;
        }
        return parse(value);
    }
}
