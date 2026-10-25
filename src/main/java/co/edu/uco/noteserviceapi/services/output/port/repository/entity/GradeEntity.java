package co.edu.uco.noteserviceapi.services.output.port.repository.entity;

import java.time.Instant;
import java.util.UUID;

public class GradeEntity {
    UUID id;
    GroupEntity group;
    Instant registrationDateFrom;
    Instant registrationDateTo;

    public Instant getRegistrationStartDate() {
        return registrationDateFrom;
    }

    public Instant getRegistrationEndDate() {
        return registrationDateTo;
    }

}
