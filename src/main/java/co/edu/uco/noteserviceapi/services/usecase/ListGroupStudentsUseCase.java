package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GroupDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentResponse;
import co.edu.uco.noteserviceapi.services.input.port.ListGroupStudentsPort;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
public class ListGroupStudentsUseCase implements ListGroupStudentsPort {

    private final GroupRepository groupRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final GroupDomain groupDomain;

    public ListGroupStudentsUseCase(
            final GroupRepository groupRepository,
            final StudentGroupRepository studentGroupRepository,
            final GroupDomain groupDomain) {
        this.groupRepository = groupRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.groupDomain = groupDomain;
    }

    @Override
    public List<StudentResponse> execute(final UUID groupId, final UUID teacherId) {
        final GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.GROUP_NOT_FOUND.name()));
        groupDomain.validateAssignedTeacher(group, teacherId);

        return studentGroupRepository.findActiveByGroupId(groupId).stream()
                .filter(studentGroup -> studentGroup.getStudent().isActive())
                .map(studentGroup -> new StudentResponse(
                        studentGroup.getStudent().getId(),
                        studentGroup.getStudent().getName()))
                .toList();
    }
}
