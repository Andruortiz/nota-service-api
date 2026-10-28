package co.edu.uco.noteserviceapi.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.RegistrationPeriodDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.RegistrationPeriodEntity;
import co.edu.uco.noteserviceapi.services.input.dto.response.RegistrationPeriodResponse;
import co.edu.uco.noteserviceapi.services.usecase.ListRegistrationPeriodsUseCase;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListRegistrationPeriodsUseCaseTest {

    private final StudentGradeFixture fixture = new StudentGradeFixture();

    private final ListRegistrationPeriodsUseCase useCase = new ListRegistrationPeriodsUseCase(
            fixture.registrationPeriodRepository, new RegistrationPeriodDomain(), fixture.clock);

    private RegistrationPeriodEntity period(final GradeType type, final Instant from, final Instant to) {
        final RegistrationPeriodEntity period = new RegistrationPeriodEntity();
        period.setType(type);
        period.setRegistrationDateFrom(from);
        period.setRegistrationDateTo(to);
        return period;
    }

    @Test
    @DisplayName("Should list the periods ordered by type flagging which ones are open")
    void shouldListThePeriodsOrderedByTypeFlaggingWhichOnesAreOpen() {
        final Instant now = StudentGradeFixture.NOW;
        final RegistrationPeriodEntity midterm =
                period(GradeType.MIDTERM, now.minusSeconds(7200), now.minusSeconds(3600));
        final RegistrationPeriodEntity followUp =
                period(GradeType.FOLLOW_UP, now.minusSeconds(3600), now.plusSeconds(3600));
        when(fixture.registrationPeriodRepository.findAll()).thenReturn(List.of(midterm, followUp));

        final List<RegistrationPeriodResponse> periods = useCase.execute();

        assertThat(periods).extracting(RegistrationPeriodResponse::type)
                .containsExactly(GradeType.FOLLOW_UP, GradeType.MIDTERM);
        assertThat(periods).extracting(RegistrationPeriodResponse::open).containsExactly(true, false);
    }

    @Test
    @DisplayName("Should return an empty list when no period is defined")
    void shouldReturnAnEmptyListWhenNoPeriodIsDefined() {
        when(fixture.registrationPeriodRepository.findAll()).thenReturn(List.of());

        assertThat(useCase.execute()).isEmpty();
    }
}
