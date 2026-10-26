package co.edu.uco.noteserviceapi.domain.registerstudentnote.entity;

import java.time.Instant;
import java.util.UUID;

public class GradeEntity {
    UUID id;
    GroupEntity group;
    Instant registrationDateFrom;
    Instant registrationDateTo;

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public Instant getRegistrationDateFrom() {
        return registrationDateFrom;
    }

    public void setRegistrationDateFrom(Instant registrationDateFrom) {
        this.registrationDateFrom = registrationDateFrom;
    }

    public Instant getRegistrationDateTo() {
        return registrationDateTo;
    }

    public void setRegistrationDateTo(Instant registrationDateTo) {
        this.registrationDateTo = registrationDateTo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
