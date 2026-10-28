package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "registration_period")
public class RegistrationPeriodJpaEntity {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false, length = 20)
    private GradeType type;

    @Column(name = "registration_date_from", nullable = false)
    private Instant registrationDateFrom;

    @Column(name = "registration_date_to", nullable = false)
    private Instant registrationDateTo;

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
