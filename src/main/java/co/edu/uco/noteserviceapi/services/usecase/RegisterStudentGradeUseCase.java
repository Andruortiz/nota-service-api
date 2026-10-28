package co.edu.uco.noteserviceapi.services.usecase;

import java.time.Clock;
import java.time.Instant;

import co.edu.uco.noteserviceapi.crosscutting.exception.ConflictException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.crosscutting.util.helper.UuidHelper;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.*;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.RegistrationPeriodEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.TeacherEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uco.noteserviceapi.services.input.dto.request.RegisterStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.RegisterStudentGradeResponse;
import co.edu.uco.noteserviceapi.services.input.port.RegisterStudentGradePort;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.RegistrationPeriodRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.TeacherRepository;

//@Service
@Transactional
public class RegisterStudentGradeUseCase
        implements RegisterStudentGradePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterStudentGradeUseCase.class);

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final RegistrationPeriodRepository registrationPeriodRepository;
    private final StudentGradeRepository studentGradeRepository;

    private final TeacherDomain teacherDomain;
    private final StudentDomain studentDomain;
    private final GroupDomain groupDomain;
    private final RegistrationPeriodDomain registrationPeriodDomain;
    private final StudentGroupDomain studentGroupDomain;
    private final StudentGradeDomain studentGradeDomain;

    private final Clock clock;

    public RegisterStudentGradeUseCase(
            final TeacherRepository teacherRepository,
            final StudentRepository studentRepository,
            final GroupRepository groupRepository,
            final StudentGroupRepository studentGroupRepository,
            final RegistrationPeriodRepository registrationPeriodRepository,
            final StudentGradeRepository studentGradeRepository,
            final TeacherDomain teacherDomain,
            final StudentDomain studentDomain,
            final GroupDomain groupDomain,
            final RegistrationPeriodDomain registrationPeriodDomain,
            final StudentGroupDomain studentGroupDomain,
            final StudentGradeDomain studentGradeDomain,
            final Clock clock) {

        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.registrationPeriodRepository = registrationPeriodRepository;
        this.studentGradeRepository = studentGradeRepository;

        this.teacherDomain = teacherDomain;
        this.studentDomain = studentDomain;
        this.groupDomain = groupDomain;
        this.registrationPeriodDomain = registrationPeriodDomain;
        this.studentGroupDomain = studentGroupDomain;
        this.studentGradeDomain = studentGradeDomain;

        this.clock = clock;
    }

    @Override
    public RegisterStudentGradeResponse execute(
            final RegisterStudentGradeRequest request) {

        final int value = studentGradeDomain.validateAndConvert(request.getScore());

        final TeacherEntity teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.TEACHER_NOT_FOUND.name()));
        teacherDomain.validateActive(teacher);

        final GroupEntity group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.GROUP_NOT_FOUND.name()));
        groupDomain.validateActive(group);
        groupDomain.validateAssignedTeacher(group, teacher.getId());

        final StudentEntity student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.STUDENT_NOT_FOUND.name()));
        studentDomain.validateActive(student);

        final StudentGroupEntity studentGroup = studentGroupRepository
                .findByStudentIdAndGroupId(student.getId(), group.getId())
                .orElse(null);
        studentGroupDomain.validateEnrollment(studentGroup);

        final GradeType type = request.getType();
        final RegistrationPeriodEntity period = registrationPeriodRepository.findById(type)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.REGISTRATION_PERIOD_NOT_FOUND.name()));
        registrationPeriodDomain.validateOpen(period, Instant.now(clock));

        if (studentGradeRepository.existsByStudentGroupIdAndType(studentGroup.getId(), type)) {
            throw new ConflictException(
                    MessageCatalog.STUDENT_GRADE_ALREADY_REGISTERED.name());
        }

        final StudentGradeEntity studentGrade = new StudentGradeEntity();
        studentGrade.setId(UuidHelper.generate());
        studentGrade.setType(type);
        studentGrade.setStudentGroup(studentGroup);
        studentGrade.setValue(value);

        studentGradeRepository.create(studentGrade);

        LOGGER.info("Student grade registered: id={}, type={}, group={}, student={}, teacher={}",
                studentGrade.getId(), type, group.getId(), student.getId(), teacher.getId());

        return new RegisterStudentGradeResponse(studentGrade.getId());
    }
}
