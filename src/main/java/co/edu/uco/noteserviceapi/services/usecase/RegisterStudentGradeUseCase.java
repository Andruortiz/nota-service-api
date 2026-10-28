package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.crosscutting.exception.ConflictException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.crosscutting.util.helper.UuidHelper;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeChangeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentGradeDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.services.input.dto.request.RegisterStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.RegisterStudentGradeResponse;
import co.edu.uco.noteserviceapi.services.input.port.RegisterStudentGradePort;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class RegisterStudentGradeUseCase
        implements RegisterStudentGradePort {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterStudentGradeUseCase.class);

    private final StudentGradeContextResolver contextResolver;
    private final StudentGradeRepository studentGradeRepository;
    private final StudentGradeHistoryRecorder historyRecorder;
    private final StudentGradeDomain studentGradeDomain;

    public RegisterStudentGradeUseCase(
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
    public RegisterStudentGradeResponse execute(
            final RegisterStudentGradeRequest request) {

        final int value = studentGradeDomain.validateAndConvert(request.getScore());

        final StudentGradeContext context = contextResolver.resolve(
                request.getTeacherId(), request.getGroupId(), request.getStudentId());

        final GradeType type = request.getType();
        contextResolver.validateOpenPeriod(type);

        if (studentGradeRepository.existsByStudentGroupIdAndType(context.studentGroup().getId(), type)) {
            throw new ConflictException(
                    MessageCatalog.STUDENT_GRADE_ALREADY_REGISTERED.name());
        }

        final StudentGradeEntity studentGrade = new StudentGradeEntity();
        studentGrade.setId(UuidHelper.generate());
        studentGrade.setType(type);
        studentGrade.setStudentGroup(context.studentGroup());
        studentGrade.setValue(value);

        studentGradeRepository.create(studentGrade);

        historyRecorder.record(
                context.studentGroup(), type, GradeChangeType.CREATED, null, value, request.getTeacherId());

        LOGGER.info("Student grade registered: id={}, type={}, group={}, student={}, teacher={}",
                studentGrade.getId(), type, context.group().getId(), context.student().getId(),
                context.teacher().getId());

        return new RegisterStudentGradeResponse(studentGrade.getId());
    }
}
