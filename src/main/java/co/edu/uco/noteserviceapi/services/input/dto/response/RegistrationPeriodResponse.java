package co.edu.uco.noteserviceapi.services.input.dto.response;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;

import java.time.Instant;

public record RegistrationPeriodResponse(
        GradeType type,
        Instant registrationDateFrom,
        Instant registrationDateTo,
        boolean open) {
}
