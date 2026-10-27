package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.services.input.dto.response.GroupResponse;
import co.edu.uco.noteserviceapi.services.input.port.ListTeacherGroupsPort;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
public class ListTeacherGroupsUseCase implements ListTeacherGroupsPort {

    private final GroupRepository groupRepository;

    public ListTeacherGroupsUseCase(final GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupResponse> execute(final UUID teacherId) {
        return groupRepository.findActiveByTeacherId(teacherId).stream()
                .map(group -> new GroupResponse(group.getId(), group.getName()))
                .toList();
    }
}
