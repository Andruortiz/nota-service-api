package co.edu.uco.noteserviceapi.domain.registerstudentnote.entity;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;

import java.time.Instant;

public class RegistrationPeriodEntity {
    GradeType type;
    Instant registrationDateFrom;
    Instant registrationDateTo;

    public GradeType getType() {
        return type;
    }

    public void setType(GradeType type) {
        this.type = type;
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
}
