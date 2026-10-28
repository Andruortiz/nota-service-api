package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.crosscutting.util.helper.UuidHelper;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeChangeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeHistoryEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeHistoryRepository;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public class StudentGradeHistoryRecorder {

    private final StudentGradeHistoryRepository studentGradeHistoryRepository;
    private final Clock clock;

    public StudentGradeHistoryRecorder(
            final StudentGradeHistoryRepository studentGradeHistoryRepository,
            final Clock clock) {
        this.studentGradeHistoryRepository = studentGradeHistoryRepository;
        this.clock = clock;
    }

    public void record(
            final StudentGroupEntity studentGroup,
            final GradeType type,
            final GradeChangeType changeType,
            final Integer previousValue,
            final Integer newValue,
            final UUID changedBy) {

        final StudentGradeHistoryEntity history = new StudentGradeHistoryEntity();
        history.setId(UuidHelper.generate());
        history.setStudentGroup(studentGroup);
        history.setType(type);
        history.setChangeType(changeType);
        history.setPreviousValue(previousValue);
        history.setNewValue(newValue);
        history.setChangedBy(changedBy);
        history.setChangedAt(Instant.now(clock));

        studentGradeHistoryRepository.create(history);
    }
}
