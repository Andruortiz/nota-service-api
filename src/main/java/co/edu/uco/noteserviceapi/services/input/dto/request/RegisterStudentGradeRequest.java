package co.edu.uco.noteserviceapi.services.input.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class RegisterStudentGradeRequest {

    private UUID teacherId;
    @NotNull
    private UUID studentId;
    @NotNull
    private UUID groupId;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(final BigDecimal score) {
        this.score = score;
    }
}
