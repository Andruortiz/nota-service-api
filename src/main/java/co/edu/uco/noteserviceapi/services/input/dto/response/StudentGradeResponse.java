package co.edu.uco.noteserviceapi.services.input.dto.response;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;

import java.math.BigDecimal;
import java.util.UUID;

public record StudentGradeResponse(UUID id, GradeType type, BigDecimal value) {
}
