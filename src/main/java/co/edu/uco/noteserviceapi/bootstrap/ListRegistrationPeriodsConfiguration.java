package co.edu.uco.noteserviceapi.bootstrap;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.RegistrationPeriodDomain;
import co.edu.uco.noteserviceapi.services.input.port.ListRegistrationPeriodsPort;
import co.edu.uco.noteserviceapi.services.output.port.repository.RegistrationPeriodRepository;
import co.edu.uco.noteserviceapi.services.usecase.ListRegistrationPeriodsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ListRegistrationPeriodsConfiguration {

    @Bean
    ListRegistrationPeriodsPort listRegistrationPeriodsPort(
            RegistrationPeriodRepository registrationPeriodRepository,
            RegistrationPeriodDomain registrationPeriodDomain,
            Clock clock) {
        return new ListRegistrationPeriodsUseCase(registrationPeriodRepository, registrationPeriodDomain, clock);
    }
}
