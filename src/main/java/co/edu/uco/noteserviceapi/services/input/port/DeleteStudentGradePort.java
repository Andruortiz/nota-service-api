package co.edu.uco.noteserviceapi.services.input.port;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;

import java.util.UUID;

public interface DeleteStudentGradePort {
    void execute(UUID groupId, UUID studentId, GradeType type, UUID teacherId);
}
