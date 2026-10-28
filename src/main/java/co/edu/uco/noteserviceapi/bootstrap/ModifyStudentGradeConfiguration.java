package co.edu.uco.noteserviceapi.bootstrap;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentGradeDomain;
import co.edu.uco.noteserviceapi.services.input.port.DeleteStudentGradePort;
import co.edu.uco.noteserviceapi.services.input.port.UpdateStudentGradePort;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import co.edu.uco.noteserviceapi.services.usecase.DeleteStudentGradeUseCase;
import co.edu.uco.noteserviceapi.services.usecase.StudentGradeContextResolver;
import co.edu.uco.noteserviceapi.services.usecase.StudentGradeHistoryRecorder;
import co.edu.uco.noteserviceapi.services.usecase.UpdateStudentGradeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModifyStudentGradeConfiguration {

    @Bean
    UpdateStudentGradePort updateStudentGradePort(
            StudentGradeContextResolver contextResolver,
            StudentGradeRepository studentGradeRepository,
            StudentGradeHistoryRecorder historyRecorder,
            StudentGradeDomain studentGradeDomain) {

        return new UpdateStudentGradeUseCase(
                contextResolver, studentGradeRepository, historyRecorder, studentGradeDomain);
    }

    @Bean
    DeleteStudentGradePort deleteStudentGradePort(
            StudentGradeContextResolver contextResolver,
            StudentGradeRepository studentGradeRepository,
            StudentGradeHistoryRecorder historyRecorder) {

        return new DeleteStudentGradeUseCase(contextResolver, studentGradeRepository, historyRecorder);
    }
}
