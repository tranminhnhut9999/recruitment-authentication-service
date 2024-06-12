package project.springboot.template.util;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

    public static Long objectToLong(Object o) {
        String s = objectToString(o);
        return stringToLong(s);
    }

    public static List<String> stringToList(String s, String separator) {
        if (!StringUtils.hasText(s) || !StringUtils.hasText(separator)) {
            return new ArrayList<>();
        }

        return Arrays.stream(s.split(separator)).filter(Objects::nonNull)
                .filter(str -> !str.trim().isEmpty()).collect(Collectors.toList());
    }

    public static List<String> stringToList(String s) {
        if (!StringUtils.hasText(s)) {
            return new ArrayList<>();
        }
        return Arrays.stream(s.split("#")).filter(Objects::nonNull)
                .filter(str -> !str.trim().isEmpty()).collect(Collectors.toList());
    }

    public static <T> String listToString(List<T> objects) {
        if (objects == null) {
            return null;
        }
        if (objects.isEmpty()) {
            return Strings.EMPTY;
        }
        return Strings.join(objects, ',');
    }


    public static List<String> stringToListSpiltComma(String s) {
        if (!StringUtils.hasText(s)) {
            return new ArrayList<>();
        }
        return Arrays.stream(s.split(",")).filter(Objects::nonNull)
                .filter(str -> !str.trim().isEmpty()).collect(Collectors.toList());
    }

    public static Double stringToDouble(String s) {
        Double n = null;
        try {
            n = Double.parseDouble(s);
        } catch (Exception ignored) {
        }
        return n;
    }

    public static Long stringToLong(String s) {
        Long n = null;
        try {
            n = new Long(s);
        } catch (Exception ignored) {
        }
        return n;
    }

    public static Integer stringToInteger(String s) {
        Integer n = null;
        try {
            n = Integer.parseInt(s);
        } catch (Exception ignored) {
        }
        return n;
    }

    public static BigDecimal stringToBigDecimal(String s) {
        if (s == null) return null;
        try {
            return new BigDecimal(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static String objectToString(Object object) {
        if (object == null) return null;
        try {
            return object.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String objectToStringSpace(Object object) {
        if (object == null) return "";
        try {
            return object.toString();
        } catch (Exception e) {
            return null;
        }
    }


    public static String capitalizeFirstLetter(String string) {
        if (string == null || string.trim().isEmpty()) {
            return "";
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String formatDate(Instant instant, String formatDate) {
        if (instant == null || formatDate == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate)
                .withZone(ZoneId.systemDefault());

        return formatter.format(instant);
    }

    public static Boolean checkRegex(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    public static String formatMoney(BigDecimal input) {
        if (input == null) {
            return null;
        }
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi"));
        return numberFormat.format(input);
    }

}
