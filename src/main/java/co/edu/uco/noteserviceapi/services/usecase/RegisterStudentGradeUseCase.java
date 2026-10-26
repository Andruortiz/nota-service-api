package co.edu.uco.noteserviceapi.services.usecase;

import java.time.Clock;
import java.time.Instant;

import co.edu.uco.noteserviceapi.crosscutting.exception.ConflictException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.crosscutting.util.helper.UuidHelper;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.*;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GradeEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.TeacherEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uco.noteserviceapi.services.input.dto.request.RegisterStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.RegisterStudentGradeResponse;
import co.edu.uco.noteserviceapi.services.input.port.RegisterStudentGradePort;
import co.edu.uco.noteserviceapi.services.output.port.repository.GradeRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.TeacherRepository;

//@Service
@Transactional
public class RegisterStudentGradeUseCase
        implements RegisterStudentGradePort {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final GradeRepository gradeRepository;
    private final StudentGradeRepository studentGradeRepository;

    private final TeacherDomain teacherDomain;
    private final StudentDomain studentDomain;
    private final GroupDomain groupDomain;
    private final GradeDomain gradeDomain;
    private final StudentGroupDomain studentGroupDomain;
    private final StudentGradeDomain studentGradeDomain;

    private final Clock clock;

    public RegisterStudentGradeUseCase(
            final TeacherRepository teacherRepository,
            final StudentRepository studentRepository,
            final GroupRepository groupRepository,
            final StudentGroupRepository studentGroupRepository,
            final GradeRepository gradeRepository,
            final StudentGradeRepository studentGradeRepository,
            final TeacherDomain teacherDomain,
            final StudentDomain studentDomain,
            final GroupDomain groupDomain,
            final GradeDomain gradeDomain,
            final StudentGroupDomain studentGroupDomain,
            final StudentGradeDomain studentGradeDomain,
            final Clock clock) {

        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.gradeRepository = gradeRepository;
        this.studentGradeRepository = studentGradeRepository;

        this.teacherDomain = teacherDomain;
        this.studentDomain = studentDomain;
        this.groupDomain = groupDomain;
        this.gradeDomain = gradeDomain;
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

        final GradeEntity grade = gradeRepository.findById(request.getGradeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.GRADE_NOT_FOUND.name()));
        gradeDomain.validateRegistrationPeriod(grade, Instant.now(clock));

        final GroupEntity group = grade.getGroup();
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

        if (studentGradeRepository.existsByGradeIdAndStudentGroupId(
                grade.getId(), studentGroup.getId())) {
            throw new ConflictException(
                    MessageCatalog.STUDENT_GRADE_ALREADY_REGISTERED.name());
        }

        final StudentGradeEntity studentGrade = new StudentGradeEntity();
        studentGrade.setId(UuidHelper.generate());
        studentGrade.setGrade(grade);
        studentGrade.setStudentGroup(studentGroup);
        studentGrade.setValue(value);

        studentGradeRepository.create(studentGrade);

        return new RegisterStudentGradeResponse(studentGrade.getId());
    }
}
