package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeChangeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "student_grade_history")
public class StudentGradeHistoryJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_group_id", nullable = false, updatable = false)
    private StudentGroupJpaEntity studentGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false, length = 20)
    private GradeType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false, updatable = false, length = 20)
    private GradeChangeType changeType;

    @Column(name = "previous_value", updatable = false)
    private Integer previousValue;

    @Column(name = "new_value", updatable = false)
    private Integer newValue;

    @Column(name = "changed_by", nullable = false, updatable = false)
    private UUID changedBy;

    @Column(name = "changed_at", nullable = false, updatable = false)
    private Instant changedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StudentGroupJpaEntity getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroupJpaEntity studentGroup) {
        this.studentGroup = studentGroup;
    }

    public GradeType getType() {
        return type;
    }

    public void setType(GradeType type) {
        this.type = type;
    }

    public GradeChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(GradeChangeType changeType) {
        this.changeType = changeType;
    }

    public Integer getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(Integer previousValue) {
        this.previousValue = previousValue;
    }

    public Integer getNewValue() {
        return newValue;
    }

    public void setNewValue(Integer newValue) {
        this.newValue = newValue;
    }

    public UUID getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(UUID changedBy) {
        this.changedBy = changedBy;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Instant changedAt) {
        this.changedAt = changedAt;
    }
}
