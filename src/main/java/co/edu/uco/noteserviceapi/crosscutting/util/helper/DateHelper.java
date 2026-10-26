package co.edu.uco.noteserviceapi.crosscutting.util.helper;

import java.time.Instant;

public final class DateHelper {

    private DateHelper() {
        super();
    }

    public static boolean isBefore(
            final Instant value,
            final Instant reference) {

        return value != null
                && reference != null
                && value.isBefore(reference);
    }

    public static boolean isAfter(
            final Instant value,
            final Instant reference) {

        return value != null
                && reference != null
                && value.isAfter(reference);
    }

    public static boolean isBetween(
            final Instant value,
            final Instant start,
            final Instant end) {

        return value != null
                && start != null
                && end != null
                && !value.isBefore(start)
                && !value.isAfter(end);
    }

    public static boolean isNull(
            final Instant value) {

        return value == null;
    }

}
