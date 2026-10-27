package co.edu.uco.noteserviceapi.services.input.port;

import co.edu.uco.noteserviceapi.services.input.dto.response.GroupResponse;

import java.util.List;
import java.util.UUID;

public interface ListTeacherGroupsPort {
    List<GroupResponse> execute(UUID teacherId);
}
