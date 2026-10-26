package co.edu.uco.noteserviceapi.services.input.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class RegisterStudentGradeRequest {

    @NotNull
    private UUID teacherId;
    @NotNull
    private UUID studentId;
    @NotNull
    private UUID gradeId;
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

    public UUID getGradeId() {
        return gradeId;
    }

    public void setGradeId(final UUID gradeId) {
        this.gradeId = gradeId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(final BigDecimal score) {
        this.score = score;
    }
}
