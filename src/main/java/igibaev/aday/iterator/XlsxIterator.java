package igibaev.aday.iterator;

import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.NoSuchElementException;

// TODO: 15.08.20 Add java docs, and write tests

/**
 * 1. у данной библиотеки нет стопера пустых значений, может читать пустые строки
 * 2. читает даже те столбцы которые скрыты
 */
@Slf4j
public class XlsxIterator<T> implements FileIterator<T> {
    private final Iterator<Row> rowIterator;
    private final ReadableWorkbook workbook;
    private final AttributesMapper attributeMapping;
    private final Class<T> tClass;

    public XlsxIterator(InputStream inputStream, Class<T> tClass) throws IOException {
        this.workbook = new ReadableWorkbook(inputStream);
        rowIterator = workbook.getFirstSheet().openStream().iterator();
        attributeMapping = new AttributesMapper(getHeaders());
        this.tClass = tClass;
        inputStream.close();
    }

    @Override
    public boolean hasNext() {
        return rowIterator.hasNext();
    }

    @Override
    public void close() {
        try {
            this.workbook.close();
        } catch (IOException e) {
            // TODO: 19.08.20 handle exception and log it
            e.printStackTrace();
        }
    }

    @Override
    public T next() {
        if (hasNext()) {
            return mapToObject(rowIterator.next());
        }
        throw new NoSuchElementException("no such element in excel file");
    }

    private String[] getHeaders() {
        if (hasNext()) {
            return convertToTextArray(rowIterator.next());
        }
        throw new NoSuchElementException("no such headers element in excel file");
    }

    private String[] convertToTextArray(Row next) {
       String[] row = new String[next.getCellCount()];
       for (int i=0; i < next.getCellCount(); i++) {
           Cell cell = next.getCell(i);
           if (cell != null) {
               row[i] = cell.getRawValue();
           }
       }
        return row;
    }

    private T mapToObject(Row next) {
        String[] row = convertToTextArray(next);
        if (attributeMapping.acceptFields(row)) {
            return (T) attributeMapping.collectObject(newInstance());
        } else {
            return null;
        }
    }

    private T newInstance() {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            log.error("Fail when try create new instance [{}]", tClass.getCanonicalName());
            throw new RuntimeException(e);
        }
    }

}
