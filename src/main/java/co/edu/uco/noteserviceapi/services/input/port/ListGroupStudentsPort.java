package co.edu.uco.noteserviceapi.services.input.port;

import co.edu.uco.noteserviceapi.services.input.dto.response.StudentResponse;

import java.util.List;
import java.util.UUID;

public interface ListGroupStudentsPort {
    List<StudentResponse> execute(UUID groupId, UUID teacherId);
}
