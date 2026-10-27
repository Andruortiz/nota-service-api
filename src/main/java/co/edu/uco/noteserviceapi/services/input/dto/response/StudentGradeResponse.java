package co.edu.uco.noteserviceapi.services.input.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record StudentGradeResponse(UUID id, UUID gradeId, BigDecimal value) {
}
