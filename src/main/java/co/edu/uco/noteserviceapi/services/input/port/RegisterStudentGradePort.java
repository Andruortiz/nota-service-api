package co.edu.uco.noteserviceapi.services.input.port;

import co.edu.uco.noteserviceapi.services.input.dto.request.RegisterStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.RegisterStudentGradeResponse;

public interface RegisterStudentGradePort {
    RegisterStudentGradeResponse execute(
            RegisterStudentGradeRequest request);
}
