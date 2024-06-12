package project.springboot.template.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class CompareUtil {
    public static Boolean compareEqualBigDecimal(BigDecimal o1, BigDecimal o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null || o2 == null) return false;
        return o1.compareTo(o2) == 0;
    }
    public static Boolean compareEqualString(String o1, String o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null || o2 == null) return false;
        o1 = o1.trim().replace("\r", "");
        o2 = o2.trim().replace("\r", "");
        return o1.equalsIgnoreCase(o2);
    }

    public static Boolean compareEqualLong(Long o1, Long o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null || o2 == null) return false;
        return o1.equals(o2);
    }

    public static Boolean compareEqualInstant(Instant o1, Instant o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null || o2 == null) return false;
        return o1.equals(o2);
    }

    public static Boolean compareEqualLists(List<?> l1, List<?> l2) {
        int l1HashCode = Objects.hash(l1.toArray());
        int l2HashCode = Objects.hash(l2.toArray());
        return l1HashCode == l2HashCode;
    }
}
