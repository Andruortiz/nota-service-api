package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GroupDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentGradeResponse;
import co.edu.uco.noteserviceapi.services.input.port.ListStudentGradesPort;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
public class ListStudentGradesUseCase implements ListStudentGradesPort {

    private final GroupRepository groupRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final StudentGradeRepository studentGradeRepository;
    private final GroupDomain groupDomain;

    public ListStudentGradesUseCase(
            final GroupRepository groupRepository,
            final StudentGroupRepository studentGroupRepository,
            final StudentGradeRepository studentGradeRepository,
            final GroupDomain groupDomain) {
        this.groupRepository = groupRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.studentGradeRepository = studentGradeRepository;
        this.groupDomain = groupDomain;
    }

    @Override
    public List<StudentGradeResponse> execute(
            final UUID groupId, final UUID studentId, final UUID teacherId) {

        final GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.GROUP_NOT_FOUND.name()));
        groupDomain.validateAssignedTeacher(group, teacherId);

        final StudentGroupEntity studentGroup = studentGroupRepository
                .findByStudentIdAndGroupId(studentId, groupId)
                .orElseThrow(() -> new BusinessRuleException(
                        MessageCatalog.STUDENT_GROUP_NOT_FOUND.name()));

        return studentGradeRepository.findByStudentGroupId(studentGroup.getId()).stream()
                .map(studentGrade -> new StudentGradeResponse(
                        studentGrade.getId(),
                        studentGrade.getGrade().getId(),
                        BigDecimal.valueOf(studentGrade.getValue(), 2)))
                .toList();
    }
}
