package co.edu.uco.noteserviceapi.services.input.port;

import co.edu.uco.noteserviceapi.services.input.dto.response.RegistrationPeriodResponse;

import java.util.List;

public interface ListRegistrationPeriodsPort {
    List<RegistrationPeriodResponse> execute();
}
