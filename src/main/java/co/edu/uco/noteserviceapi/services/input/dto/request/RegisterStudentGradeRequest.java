package co.edu.uco.noteserviceapi.services.input.dto.request;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class RegisterStudentGradeRequest {

    private UUID teacherId;
    @NotNull
    private UUID studentId;
    @NotNull
    private UUID groupId;
    @NotNull
    private GradeType type;
    private BigDecimal score;

    public UUID getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(final UUID teacherId) {
        this.teacherId = teacherId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(final UUID studentId) {
        this.studentId = studentId;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(final UUID groupId) {
        this.groupId = groupId;
    }

    public GradeType getType() {
        return type;
    }

    public void setType(final GradeType type) {
        this.type = type;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(final BigDecimal score) {
        this.score = score;
    }
}
