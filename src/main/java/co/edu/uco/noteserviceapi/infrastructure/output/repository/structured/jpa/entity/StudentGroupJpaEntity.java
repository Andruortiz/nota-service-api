package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity;
import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "student_group",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "group_id"}))
public class StudentGroupJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentJpaEntity student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupJpaEntity group;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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

    public StudentJpaEntity getStudent() {
        return student;
    }

    public void setStudent(StudentJpaEntity student) {
        this.student = student;
    }
}
