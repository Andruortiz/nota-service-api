package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeChangeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentGradeDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.services.input.dto.request.UpdateStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentGradeResponse;
import co.edu.uco.noteserviceapi.services.input.port.UpdateStudentGradePort;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Transactional
public class UpdateStudentGradeUseCase implements UpdateStudentGradePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStudentGradeUseCase.class);

    private final StudentGradeContextResolver contextResolver;
    private final StudentGradeRepository studentGradeRepository;
    private final StudentGradeHistoryRecorder historyRecorder;
    private final StudentGradeDomain studentGradeDomain;

    public UpdateStudentGradeUseCase(
            final StudentGradeContextResolver contextResolver,
            final StudentGradeRepository studentGradeRepository,
            final StudentGradeHistoryRecorder historyRecorder,
            final StudentGradeDomain studentGradeDomain) {
        this.contextResolver = contextResolver;
        this.studentGradeRepository = studentGradeRepository;
        this.historyRecorder = historyRecorder;
        this.studentGradeDomain = studentGradeDomain;
    }

    @Override
    public StudentGradeResponse execute(
            final UUID groupId,
            final UUID studentId,
            final GradeType type,
            final UUID teacherId,
            final UpdateStudentGradeRequest request) {

        final int value = studentGradeDomain.validateAndConvert(request.getScore());

        final StudentGradeContext context = contextResolver.resolve(teacherId, groupId, studentId);
        contextResolver.validateOpenPeriod(type);

        final StudentGradeEntity studentGrade = studentGradeRepository
                .findByStudentGroupIdAndType(context.studentGroup().getId(), type)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageCatalog.STUDENT_GRADE_NOT_FOUND.name()));

        final int previousValue = studentGrade.getValue();
        studentGrade.setValue(value);
        studentGradeRepository.update(studentGrade);

        historyRecorder.record(
                context.studentGroup(), type, GradeChangeType.UPDATED, previousValue, value, teacherId);

        LOGGER.info("Student grade updated: id={}, type={}, group={}, student={}, teacher={}",
                studentGrade.getId(), type, groupId, studentId, teacherId);

        return new StudentGradeResponse(studentGrade.getId(), type, BigDecimal.valueOf(value, 2));
    }
}
