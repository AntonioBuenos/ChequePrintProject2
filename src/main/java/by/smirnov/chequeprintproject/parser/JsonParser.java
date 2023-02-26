package by.smirnov.chequeprintproject.parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.smirnov.chequeprintproject.exceptionhandler.ExceptionConstants.JSON_PARSING_ERROR_MESSAGE;

public class JsonParser {

    private static final String JSON_FIELD_PATTERN = "(\"[\\w]+\":)((\\[.*])|(\\{.*})|([@.\\w\\s\\d\"]+))";
    private static final String JSON_OBJECT_PATTERN = "(\"[\\w]+\":)(\\{.*})";
    private static final String JSON_FIELD_OUTPUT_FORMAT = "\"%s\":%s";

    public <T> T deserialize(String json, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        var output = clazz.getDeclaredConstructor().newInstance();

        List<Field> fields = List.of(output.getClass().getDeclaredFields());

        List<String> pairs = getPairs(json.trim());
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim().replaceAll("\"", "");
            String value = keyValue[1].trim();

            Field field = getPairField(fields, key);
            field.setAccessible(true);
            Class<?> type = field.getType();
            field.set(output, convertValue(type, value));
        }
        return output;
    }

    private Object convertValue(Class<?> type, String value) throws NoSuchFieldException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object parsed = null;
        if (String.class.equals(type)) parsed = value;
        else if (Integer.class.equals(type) || int.class.equals(type)) {
            parsed = Integer.parseInt(value);
        } else if (Long.class.equals(type) || long.class.equals(type)) {
            parsed = Long.parseLong(value);
        } else if (Double.class.equals(type) || double.class.equals(type)) {
            parsed = Double.parseDouble(value);
        } else if (Boolean.class.equals(type) || boolean.class.equals(type)) {
            parsed = Boolean.parseBoolean(value);
        } else if (Timestamp.class.equals(type)) {
            parsed = Timestamp.valueOf(value);
        } else if (value.matches(JSON_OBJECT_PATTERN)) {
            parsed = deserialize(value, type);
        }
        return parsed;
    }

    private Field getPairField(List<Field> fields, String key) throws NoSuchFieldException {
        Field field = null;
        for (Field element : fields) {
            if (element.getName().equals(key)) {
                field = element;
                break;
            }
        }
        if (field == null) throw new NoSuchFieldException(String.format(JSON_PARSING_ERROR_MESSAGE));
        return field;
    }

    private List<String> getPairs(String json) {
        String object = json.substring(1, json.length() - 1).trim();
        List<String> pairs = new ArrayList<>();
        Pattern pattern = Pattern.compile(JSON_FIELD_PATTERN);
        Matcher matcher = pattern.matcher(object);
        while (matcher.find()){
            String result = matcher.group();
            pairs.add(result);
        }
        return pairs;
    }

    public String serialize(Object object) throws IllegalAccessException {
        List<Field> fields = List.of(object.getClass().getDeclaredFields());

        StringJoiner joiner = new StringJoiner(",", "{", "}");

        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object fieldObject = field.get(object);
            if(fieldObject instanceof Number || fieldObject instanceof String || fieldObject instanceof Timestamp
                    || fieldObject instanceof Boolean) {
                joiner.add(String.format(JSON_FIELD_OUTPUT_FORMAT, name, fieldObject));
            }
            else joiner.add(String.format(JSON_FIELD_OUTPUT_FORMAT, name, serialize(fieldObject)));
        }
        return joiner.toString();
    }
}
