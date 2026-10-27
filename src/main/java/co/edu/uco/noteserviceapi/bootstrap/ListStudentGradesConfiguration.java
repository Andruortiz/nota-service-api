package co.edu.uco.noteserviceapi.bootstrap;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GroupDomain;
import co.edu.uco.noteserviceapi.services.input.port.ListStudentGradesPort;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import co.edu.uco.noteserviceapi.services.usecase.ListStudentGradesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListStudentGradesConfiguration {

    @Bean
    ListStudentGradesPort listStudentGradesPort(
            GroupRepository groupRepository,
            StudentGroupRepository studentGroupRepository,
            StudentGradeRepository studentGradeRepository,
            GroupDomain groupDomain) {
        return new ListStudentGradesUseCase(
                groupRepository, studentGroupRepository, studentGradeRepository, groupDomain);
    }
}
