package co.edu.uco.noteserviceapi.domain.registerstudentnote;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;

import java.math.BigDecimal;

public class StudentGradeDomain {

    private static final BigDecimal MIN_GRADE =
            BigDecimal.ZERO;

    private static final BigDecimal MAX_GRADE =
            BigDecimal.valueOf(5);

    private static final int MAX_DECIMALS = 1;

    public int validateAndConvert(
            final BigDecimal grade) {

        if (grade == null) {
            throw new BusinessRuleException(
                    MessageCatalog.GRADE_VALUE_OUT_OF_RANGE.name());
        }

        if (grade.stripTrailingZeros().scale()
                > MAX_DECIMALS) {

            throw new BusinessRuleException(
                    MessageCatalog.GRADE_INVALID_DECIMALS.name());
        }

        if (grade.compareTo(MIN_GRADE) < 0
                || grade.compareTo(MAX_GRADE) > 0) {

            throw new BusinessRuleException(
                    MessageCatalog.GRADE_VALUE_OUT_OF_RANGE.name());
        }

        return grade.movePointRight(2)
                .intValueExact();
    }

}
