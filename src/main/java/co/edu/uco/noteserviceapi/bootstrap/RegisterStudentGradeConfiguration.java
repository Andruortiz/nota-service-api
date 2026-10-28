package co.edu.uco.noteserviceapi.bootstrap;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.*;
import co.edu.uco.noteserviceapi.services.input.port.RegisterStudentGradePort;
import co.edu.uco.noteserviceapi.services.output.port.repository.*;
import co.edu.uco.noteserviceapi.services.usecase.RegisterStudentGradeUseCase;
import co.edu.uco.noteserviceapi.services.usecase.StudentGradeContextResolver;
import co.edu.uco.noteserviceapi.services.usecase.StudentGradeHistoryRecorder;
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
    RegistrationPeriodDomain registrationPeriodDomain() { return new RegistrationPeriodDomain(); }

    @Bean
    StudentGroupDomain studentGroupDomain() { return new StudentGroupDomain(); }

    @Bean
    StudentGradeDomain studentGradeDomain() { return new StudentGradeDomain(); }

    @Bean
    StudentGradeContextResolver studentGradeContextResolver(
            TeacherRepository teacherRepository,
            GroupRepository groupRepository,
            StudentRepository studentRepository,
            StudentGroupRepository studentGroupRepository,
            RegistrationPeriodRepository registrationPeriodRepository,
            TeacherDomain teacherDomain,
            GroupDomain groupDomain,
            StudentDomain studentDomain,
            StudentGroupDomain studentGroupDomain,
            RegistrationPeriodDomain registrationPeriodDomain,
            Clock clock) {

        return new StudentGradeContextResolver(
                teacherRepository, groupRepository, studentRepository,
                studentGroupRepository, registrationPeriodRepository,
                teacherDomain, groupDomain, studentDomain,
                studentGroupDomain, registrationPeriodDomain, clock);
    }

    @Bean
    StudentGradeHistoryRecorder studentGradeHistoryRecorder(
            StudentGradeHistoryRepository studentGradeHistoryRepository,
            Clock clock) {

        return new StudentGradeHistoryRecorder(studentGradeHistoryRepository, clock);
    }

    @Bean
    RegisterStudentGradePort registerStudentGradePort(
            StudentGradeContextResolver contextResolver,
            StudentGradeRepository studentGradeRepository,
            StudentGradeHistoryRecorder historyRecorder,
            StudentGradeDomain studentGradeDomain) {

        return new RegisterStudentGradeUseCase(
                contextResolver, studentGradeRepository, historyRecorder, studentGradeDomain);
    }
}
