package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GroupDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.RegistrationPeriodDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentGroupDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.TeacherDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.RegistrationPeriodEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.TeacherEntity;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.RegistrationPeriodRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.TeacherRepository;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public class StudentGradeContextResolver {

    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final RegistrationPeriodRepository registrationPeriodRepository;

    private final TeacherDomain teacherDomain;
    private final GroupDomain groupDomain;
    private final StudentDomain studentDomain;
    private final StudentGroupDomain studentGroupDomain;
    private final RegistrationPeriodDomain registrationPeriodDomain;

    private final Clock clock;

    public StudentGradeContextResolver(
            final TeacherRepository teacherRepository,
            final GroupRepository groupRepository,
            final StudentRepository studentRepository,
            final StudentGroupRepository studentGroupRepository,
            final RegistrationPeriodRepository registrationPeriodRepository,
            final TeacherDomain teacherDomain,
            final GroupDomain groupDomain,
            final StudentDomain studentDomain,
            final StudentGroupDomain studentGroupDomain,
            final RegistrationPeriodDomain registrationPeriodDomain,
            final Clock clock) {

        this.teacherRepository = teacherRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.registrationPeriodRepository = registrationPeriodRepository;

        this.teacherDomain = teacherDomain;
        this.groupDomain = groupDomain;
        this.studentDomain = studentDomain;
        this.studentGroupDomain = studentGroupDomain;
        this.registrationPeriodDomain = registrationPeriodDomain;

        this.clock = clock;
    }

    public StudentGradeContext resolve(
            final UUID teacherId,
            final UUID groupId,
            final UUID studentId) {

        final TeacherEntity teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.TEACHER_NOT_FOUND.name()));
        teacherDomain.validateActive(teacher);

        final GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.GROUP_NOT_FOUND.name()));
        groupDomain.validateActive(group);
        groupDomain.validateAssignedTeacher(group, teacher.getId());

        final StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.STUDENT_NOT_FOUND.name()));
        studentDomain.validateActive(student);

        final StudentGroupEntity studentGroup = studentGroupRepository
                .findByStudentIdAndGroupId(student.getId(), group.getId())
                .orElse(null);
        studentGroupDomain.validateEnrollment(studentGroup);

        return new StudentGradeContext(teacher, group, student, studentGroup);
    }

    public void validateOpenPeriod(final GradeType type) {
        final RegistrationPeriodEntity period = registrationPeriodRepository.findById(type)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.REGISTRATION_PERIOD_NOT_FOUND.name()));
        registrationPeriodDomain.validateOpen(period, Instant.now(clock));
    }
}
