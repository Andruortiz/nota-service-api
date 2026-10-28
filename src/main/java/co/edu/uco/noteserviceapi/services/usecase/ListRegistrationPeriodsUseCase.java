package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.RegistrationPeriodDomain;
import co.edu.uco.noteserviceapi.services.input.dto.response.RegistrationPeriodResponse;
import co.edu.uco.noteserviceapi.services.input.port.ListRegistrationPeriodsPort;
import co.edu.uco.noteserviceapi.services.output.port.repository.RegistrationPeriodRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Transactional(readOnly = true)
public class ListRegistrationPeriodsUseCase implements ListRegistrationPeriodsPort {

    private final RegistrationPeriodRepository registrationPeriodRepository;
    private final RegistrationPeriodDomain registrationPeriodDomain;
    private final Clock clock;

    public ListRegistrationPeriodsUseCase(
            final RegistrationPeriodRepository registrationPeriodRepository,
            final RegistrationPeriodDomain registrationPeriodDomain,
            final Clock clock) {
        this.registrationPeriodRepository = registrationPeriodRepository;
        this.registrationPeriodDomain = registrationPeriodDomain;
        this.clock = clock;
    }

    @Override
    public List<RegistrationPeriodResponse> execute() {
        final Instant now = Instant.now(clock);
        return registrationPeriodRepository.findAll().stream()
                .sorted(Comparator.comparing(period -> period.getType()))
                .map(period -> new RegistrationPeriodResponse(
                        period.getType(),
                        period.getRegistrationDateFrom(),
                        period.getRegistrationDateTo(),
                        registrationPeriodDomain.isOpen(period, now)))
                .toList();
    }
}
