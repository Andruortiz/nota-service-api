package co.edu.uco.noteserviceapi.bootstrap;


import co.edu.uco.noteserviceapi.domain.registerstudentnote.*;
import co.edu.uco.noteserviceapi.services.input.port.RegisterStudentGradePort;
import co.edu.uco.noteserviceapi.services.output.port.repository.*;
import co.edu.uco.noteserviceapi.services.usecase.RegisterStudentGradeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class RegisterStudentGradeConfiguration {

    @Bean
    TeacherDomain teacherDomain() { return new TeacherDomain(); }

    @Bean
    StudentDomain studentDomain() { return new StudentDomain(); }

    @Bean
    GroupDomain groupDomain() { return new GroupDomain(); }

    @Bean
    GradeDomain gradeDomain() { return new GradeDomain(); }

    @Bean
    StudentGroupDomain studentGroupDomain() { return new StudentGroupDomain(); }

    @Bean
    StudentGradeDomain studentGradeDomain() { return new StudentGradeDomain(); }

    @Bean
    RegisterStudentGradePort registerStudentGradePort(
            TeacherRepository teacherRepository,
            StudentRepository studentRepository,
            GroupRepository groupRepository,
            StudentGroupRepository studentGroupRepository,
            GradeRepository gradeRepository,
            StudentGradeRepository studentGradeRepository,
            TeacherDomain teacherDomain,
            StudentDomain studentDomain,
            GroupDomain groupDomain,
            GradeDomain gradeDomain,
            StudentGroupDomain studentGroupDomain,
            StudentGradeDomain studentGradeDomain,
            Clock clock) {

        return new RegisterStudentGradeUseCase(
                teacherRepository, studentRepository, groupRepository,
                studentGroupRepository, gradeRepository, studentGradeRepository,
                teacherDomain, studentDomain, groupDomain,
                gradeDomain, studentGroupDomain, studentGradeDomain, clock);
    }
}
