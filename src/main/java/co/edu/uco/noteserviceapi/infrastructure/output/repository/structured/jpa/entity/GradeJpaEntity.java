package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "grade")
public class GradeJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupJpaEntity group;

    @Column(name = "registration_date_from", nullable = false)
    private Instant registrationDateFrom;

    @Column(name = "registration_date_to", nullable = false)
    private Instant registrationDateTo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GroupJpaEntity getGroup() {
        return group;
    }

    public void setGroup(GroupJpaEntity group) {
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
}
