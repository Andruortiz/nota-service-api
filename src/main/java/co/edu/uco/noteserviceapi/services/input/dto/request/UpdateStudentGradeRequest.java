package co.edu.uco.noteserviceapi.services.input.dto.request;

import java.math.BigDecimal;

public class UpdateStudentGradeRequest {

    private BigDecimal score;

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(final BigDecimal score) {
        this.score = score;
    }
}
