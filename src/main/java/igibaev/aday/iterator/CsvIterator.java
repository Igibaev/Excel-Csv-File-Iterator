package igibaev.aday.iterator;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Slf4j
public class CsvIterator<T> implements FileIterator<T> {
    private final CSVReader csvReader;
    private final Iterator<String[]> rowsIterator;
    private final AttributesMapper attributeMapping;
    private final Class<T> tClass;

    public CsvIterator(InputStream inputStream, Class<T> tClass) {
        this.csvReader = new CSVReader(new InputStreamReader(inputStream));
        this.rowsIterator = csvReader.iterator();
        this.attributeMapping = new AttributesMapper(getHeaders());
        this.tClass = tClass;
    }

    private String[] getHeaders() {
        if (hasNext()) {
            return rowsIterator.next();
        }
        throw new NoSuchElementException();
    }

    @Override
    public T next() {
        if (hasNext()) {
            return convertToAbonbase(rowsIterator.next());
        }
        throw new NoSuchElementException("no such element in csv file");
    }

    private T convertToAbonbase(String[] next) {
        attributeMapping.acceptFields(next);
        return (T) attributeMapping.collectObject(newInstance());
    }

    private T newInstance() {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            log.error("Fail when try create new instance [{}]", tClass.getCanonicalName());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return rowsIterator.hasNext();
    }

    @Override
    public void close() {
        try {
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
