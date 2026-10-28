package co.edu.uco.noteserviceapi.services.input.port;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.services.input.dto.request.UpdateStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentGradeResponse;

import java.util.UUID;

public interface UpdateStudentGradePort {
    StudentGradeResponse execute(
            UUID groupId,
            UUID studentId,
            GradeType type,
            UUID teacherId,
            UpdateStudentGradeRequest request);
}
