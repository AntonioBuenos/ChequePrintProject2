package by.smirnov.chequeprintproject.parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {
    private static final String XML_FIELD_PATTERN = "(<[\\w]+>)([\\w\\d\\s@.]+)(</[\\w]+>)";
    private static final String XML_OBJECT_PATTERN = "(<[\\w]+>)([\\w\\d\\s@.<>/\\n]+)(</[\\w]+>)";

    public <T> T deserialize(String json, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        var output = clazz.getDeclaredConstructor().newInstance();

        List<Field> fields = List.of(output.getClass().getDeclaredFields());

        List<String> pairs = getPairs(json.trim(), clazz.getSimpleName());
        for (String pair : pairs) {
            int keyEnd = pair.indexOf('>');
            String key = pair.substring(1, keyEnd);
            int closinTagStart = pair.indexOf("</");
            String value = pair.substring(keyEnd + 1, closinTagStart);

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
        if (value.equals("null")) parsed = null;
        else if (String.class.equals(type)) parsed = value;
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
        } else if (value.matches(XML_OBJECT_PATTERN)) {
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
        if (field == null) throw new NoSuchFieldException();
        return field;
    }

    private List<String> getPairs(String xmlLine, String className) {
        String regex = String.format("(<)([/]?)%s", className);
        xmlLine = xmlLine.replaceAll(regex, "");
        List<String> pairs = new ArrayList<>();
        Pattern pattern = Pattern.compile(XML_FIELD_PATTERN);
        Matcher matcher = pattern.matcher(xmlLine);
        while (matcher.find()) {
            String result = matcher.group();
            pairs.add(result);
        }
        return pairs;
    }
}
