package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeChangeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.services.input.port.DeleteStudentGradePort;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public class DeleteStudentGradeUseCase implements DeleteStudentGradePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteStudentGradeUseCase.class);

    private final StudentGradeContextResolver contextResolver;
    private final StudentGradeRepository studentGradeRepository;
    private final StudentGradeHistoryRecorder historyRecorder;

    public DeleteStudentGradeUseCase(
            final StudentGradeContextResolver contextResolver,
            final StudentGradeRepository studentGradeRepository,
            final StudentGradeHistoryRecorder historyRecorder) {
        this.contextResolver = contextResolver;
        this.studentGradeRepository = studentGradeRepository;
        this.historyRecorder = historyRecorder;
    }

    @Override
    public void execute(
            final UUID groupId,
            final UUID studentId,
            final GradeType type,
            final UUID teacherId) {

        final StudentGradeContext context = contextResolver.resolve(teacherId, groupId, studentId);
        contextResolver.validateOpenPeriod(type);

        final StudentGradeEntity studentGrade = studentGradeRepository
                .findByStudentGroupIdAndType(context.studentGroup().getId(), type)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.STUDENT_GRADE_NOT_FOUND.name()));

        studentGradeRepository.delete(studentGrade.getId());

        historyRecorder.record(
                context.studentGroup(), type, GradeChangeType.DELETED, studentGrade.getValue(), null, teacherId);

        LOGGER.info("Student grade deleted: id={}, type={}, group={}, student={}, teacher={}",
                studentGrade.getId(), type, groupId, studentId, teacherId);
    }
}
