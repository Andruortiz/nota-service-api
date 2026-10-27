package co.edu.uco.noteserviceapi.bootstrap;

import co.edu.uco.noteserviceapi.services.input.port.ListTeacherGroupsPort;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.usecase.ListTeacherGroupsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListTeacherGroupsConfiguration {

    @Bean
    ListTeacherGroupsPort listTeacherGroupsPort(GroupRepository groupRepository) {
        return new ListTeacherGroupsUseCase(groupRepository);
    }
}
