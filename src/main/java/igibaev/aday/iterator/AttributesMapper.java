package igibaev.aday.iterator;



import igibaev.aday.iterator.annotations.FieldAttributes;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AttributesMapper implements FieldsCollector {
    private final Map<String, String> attributeMap = new HashMap<>();
    private final String[] headers;

    public AttributesMapper(String[] headers) {
        setAttributeMap(headers);
        this.headers = headers;
    }

    @Override
    public boolean acceptFields(String[] fields) {
        if (isFieldsAreEmpty(fields)) {
            return false;
        }
        validRow(fields);
        for (int i = 0; i < headers.length; i++) {
            attributeMap.put(headers[i], fields[i]);
        }
        return true;
    }

    @Override
    public Object collectObject(Object data) {
        Field[] fields = data.getClass().getDeclaredFields();
        try {
            for (Field field: fields) {
                if (field.isAnnotationPresent(FieldAttributes.class)) {
                    FieldAttributes attribute = field.getAnnotation(FieldAttributes.class);
                    String fieldAttributeName = attribute.name();
                    Method reflectedMethod = attribute.parser().getDeclaredMethod("parseField", String.class);
                    final MethodHandles.Lookup lookup = MethodHandles.lookup();
                    MethodHandle methodHandle = lookup.unreflect(reflectedMethod);
                    String value = attributeMap.get(fieldAttributeName);
                    if (attribute.isRequired() && value == null) {
                        throw new RuntimeException(fieldAttributeName + " header is missing");
                    }
                    if (field.trySetAccessible()) {
                        Object fieldObjectValue = methodHandle.invokeExact(value);
                        field.set(data, fieldObjectValue);
                    } else {
                        throw new IllegalAccessException("cannot set access to field: " + data.getClass().getName());
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            // TODO: 19.08.20 handle logs
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return data;
    }

    private void setAttributeMap(String[] headers) {
        for (String header : headers) {
            attributeMap.put(header, null);
        }
    }

    private void validRow(String[] row) {
        if (row.length < headers.length) {
            // TODO: 19.08.20 handle custom exception
            throw new RuntimeException("invalid row, row length doesnt equal headers lenght");
        }
    }

    private boolean isFieldsAreEmpty(String[] row) {
        return row.length == 0;
    }
}
