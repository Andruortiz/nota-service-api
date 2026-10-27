package co.edu.uco.noteserviceapi.bootstrap;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GroupDomain;
import co.edu.uco.noteserviceapi.services.input.port.ListGroupStudentsPort;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import co.edu.uco.noteserviceapi.services.usecase.ListGroupStudentsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListGroupStudentsConfiguration {

    @Bean
    ListGroupStudentsPort listGroupStudentsPort(
            GroupRepository groupRepository,
            StudentGroupRepository studentGroupRepository,
            GroupDomain groupDomain) {
        return new ListGroupStudentsUseCase(groupRepository, studentGroupRepository, groupDomain);
    }
}
