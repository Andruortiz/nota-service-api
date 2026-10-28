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
    RegistrationPeriodDomain registrationPeriodDomain() { return new RegistrationPeriodDomain(); }

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
            RegistrationPeriodRepository registrationPeriodRepository,
            StudentGradeRepository studentGradeRepository,
            TeacherDomain teacherDomain,
            StudentDomain studentDomain,
            GroupDomain groupDomain,
            RegistrationPeriodDomain registrationPeriodDomain,
            StudentGroupDomain studentGroupDomain,
            StudentGradeDomain studentGradeDomain,
            Clock clock) {

        return new RegisterStudentGradeUseCase(
                teacherRepository, studentRepository, groupRepository,
                studentGroupRepository, registrationPeriodRepository, studentGradeRepository,
                teacherDomain, studentDomain, groupDomain,
                registrationPeriodDomain, studentGroupDomain, studentGradeDomain, clock);
    }
}
