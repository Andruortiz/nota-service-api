package co.edu.uco.noteserviceapi.crosscutting.util.helper;


import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;

public final class ValidationHelper {

    private ValidationHelper() {
    }

    public static <T> T requireNonNull(final T value) {
        if (value == null) {
            throw new BusinessRuleException("invalid.request");
        }
        return value;
    }

    public static String requireValidText(final String value, final int maxLength) {
        final String cleaned = requireNonNull(value).strip();
        if (cleaned.isEmpty() || cleaned.length() > maxLength) {
            throw new BusinessRuleException("invalid.request");
        }
        return cleaned;
    }
}
