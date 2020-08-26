package igibaev.aday.iterator;

/**
 * Accept rows from file (csv/xlsx), and collect object
  */
public interface FieldsCollector {
    /**
     * Take row, and if it acceptable, accept it and return true, otherwise return false
     *
     * @param fields {@link String[]} read one row from file and collect fields
     * @return true if acceptable
     */
    boolean acceptFields(String[] fields);

    // TODO: 19.08.20 ОПИСАТЬ АБСТРАКТНО НА КАКОЙ ОСНОВЕ ПРОИСХОДИТ ПРЕОБРАЗОВАНИЕ ПРОЧИТАННЫХ СТРОК В ГОТОВЫЕ ОБЪЕКТЫ
    /**
     *
     * Collect object from accepted fields
     * @param newInstance new instance of object
     * @return return new Object
     */
    Object collectObject(Object newInstance);
}
