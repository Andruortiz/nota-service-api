package co.edu.uco.noteserviceapi.crosscutting.parameter;

public enum ParameterCatalog {

    MIN_GRADE_VALUE("0.00"),
    MAX_GRADE_VALUE("5.00"),
    MAX_GRADE_DECIMALS("2"),

    DEFAULT_PAGE_SIZE("20"),
    MAX_PAGE_SIZE("100"),

    CACHE_TTL_MINUTES("30"),

    REGISTRATION_PERIOD_ENABLED("true");

    private final String defaultValue;

    ParameterCatalog(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

}