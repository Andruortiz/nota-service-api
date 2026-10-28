package co.edu.uco.noteserviceapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentGradeDomain;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentGradeDomainTest {

    private final StudentGradeDomain domain = new StudentGradeDomain();

    @Test
    @DisplayName("Should accept grades with at most one decimal and convert them to hundredths")
    void shouldAcceptGradesWithAtMostOneDecimalAndConvertThemToHundredths() {
        assertThat(domain.validateAndConvert(new BigDecimal("4.5"))).isEqualTo(450);
        assertThat(domain.validateAndConvert(new BigDecimal("4.50"))).isEqualTo(450);
        assertThat(domain.validateAndConvert(new BigDecimal("0"))).isZero();
        assertThat(domain.validateAndConvert(new BigDecimal("5.0"))).isEqualTo(500);
    }

    @Test
    @DisplayName("Should reject grades with more than one significant decimal")
    void shouldRejectGradesWithMoreThanOneSignificantDecimal() {
        assertThatThrownBy(() -> domain.validateAndConvert(new BigDecimal("4.25")))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_INVALID_DECIMALS.name());
    }

    @Test
    @DisplayName("Should reject null grades and grades outside the range")
    void shouldRejectNullGradesAndGradesOutsideTheRange() {
        assertThatThrownBy(() -> domain.validateAndConvert(null))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_VALUE_OUT_OF_RANGE.name());
        assertThatThrownBy(() -> domain.validateAndConvert(new BigDecimal("5.1")))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_VALUE_OUT_OF_RANGE.name());
        assertThatThrownBy(() -> domain.validateAndConvert(new BigDecimal("-0.1")))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_VALUE_OUT_OF_RANGE.name());
    }
}
