package project.springboot.template.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
    public static Integer extractMonth(Instant instant) {
        LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return now.getMonthValue();
    }

    public static Integer extractYear(Instant instant) {
        LocalDateTime now = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return now.getYear();
    }
}
