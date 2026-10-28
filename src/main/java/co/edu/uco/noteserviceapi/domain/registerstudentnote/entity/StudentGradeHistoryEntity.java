package co.edu.uco.noteserviceapi.domain.registerstudentnote.entity;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeChangeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;

import java.time.Instant;
import java.util.UUID;

public class StudentGradeHistoryEntity {

    private UUID id;
    private StudentGroupEntity studentGroup;
    private GradeType type;
    private GradeChangeType changeType;
    private Integer previousValue;
    private Integer newValue;
    private UUID changedBy;
    private Instant changedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StudentGroupEntity getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroupEntity studentGroup) {
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
