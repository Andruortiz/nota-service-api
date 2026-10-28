package co.edu.uco.noteserviceapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.RegistrationPeriodDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.RegistrationPeriodEntity;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationPeriodDomainTest {

    private static final Instant FROM = Instant.parse("2026-11-01T00:00:00Z");
    private static final Instant TO = Instant.parse("2026-11-30T23:59:59Z");

    private final RegistrationPeriodDomain domain = new RegistrationPeriodDomain();

    private RegistrationPeriodEntity period() {
        final RegistrationPeriodEntity period = new RegistrationPeriodEntity();
        period.setType(GradeType.FOLLOW_UP);
        period.setRegistrationDateFrom(FROM);
        period.setRegistrationDateTo(TO);
        return period;
    }

    @Test
    @DisplayName("Should be open inside the period and on both inclusive boundaries")
    void shouldBeOpenInsideThePeriodAndOnBothInclusiveBoundaries() {
        assertThat(domain.isOpen(period(), FROM)).isTrue();
        assertThat(domain.isOpen(period(), Instant.parse("2026-11-15T12:00:00Z"))).isTrue();
        assertThat(domain.isOpen(period(), TO)).isTrue();
    }

    @Test
    @DisplayName("Should be closed one second before the start and one second after the end")
    void shouldBeClosedOneSecondBeforeTheStartAndOneSecondAfterTheEnd() {
        assertThat(domain.isOpen(period(), FROM.minusSeconds(1))).isFalse();
        assertThat(domain.isOpen(period(), TO.plusSeconds(1))).isFalse();
    }

    @Test
    @DisplayName("Should reject registration outside the period with the period message")
    void shouldRejectRegistrationOutsideThePeriodWithThePeriodMessage() {
        assertThatThrownBy(() -> domain.validateOpen(period(), TO.plusSeconds(1)))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_OUTSIDE_REGISTRATION_PERIOD.name());
    }
}
