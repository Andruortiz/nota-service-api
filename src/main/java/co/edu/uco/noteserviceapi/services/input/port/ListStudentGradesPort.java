package co.edu.uco.noteserviceapi.services.input.port;

import co.edu.uco.noteserviceapi.services.input.dto.response.StudentGradeResponse;

import java.util.List;
import java.util.UUID;

public interface ListStudentGradesPort {
    List<StudentGradeResponse> execute(UUID groupId, UUID studentId, UUID teacherId);
}
