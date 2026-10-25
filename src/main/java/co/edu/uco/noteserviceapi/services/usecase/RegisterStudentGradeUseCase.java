package co.edu.uco.noteserviceapi.services.usecase;

import java.time.Clock;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.*;
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

        throw new UnsupportedOperationException(
                "RegisterStudentGradeUseCase not implemented yet");
    }
}