package igibaev.aday.iterator;

/**
 * Итератор для чтения файлов xlsx и csv
 * Т объект преобразования
 * метод next вернет готовый обьект для сохранения в базу или отправки в брокер сообщений
 * @param <T>
 */
public interface FileIterator<T> extends AutoCloseable{
    /**
     * Читает из файла и преобразовывает в объект Т
     * @return возвращает обьект Т, если файл пустой вернет null
     */
    T next();

    /**
     * вернет если еще есть строки в файле которые можно прочитать, не гарантирует что обьект будет вовзращен после вызова next()
     * @return true if there at least on row in file
     */
    boolean hasNext();

    /**
     * close input stream
     */
    void close();
}
