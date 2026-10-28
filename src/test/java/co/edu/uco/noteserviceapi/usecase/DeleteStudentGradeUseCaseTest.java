package co.edu.uco.noteserviceapi.usecase;

import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.GROUP_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.NOW;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.STUDENT_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.TEACHER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeChangeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeHistoryEntity;
import co.edu.uco.noteserviceapi.services.usecase.DeleteStudentGradeUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeleteStudentGradeUseCaseTest {

    private final StudentGradeFixture fixture = new StudentGradeFixture();

    private final DeleteStudentGradeUseCase useCase = new DeleteStudentGradeUseCase(
            fixture.contextResolver,
            fixture.studentGradeRepository,
            fixture.historyRecorder);

    @Test
    @DisplayName("Should delete the grade and record the deletion with its previous value")
    void shouldDeleteTheGradeAndRecordTheDeletionWithItsPreviousValue() {
        final StudentGradeEntity grade = fixture.existingGrade(GradeType.FOLLOW_UP, 450);

        useCase.execute(GROUP_ID, STUDENT_ID, GradeType.FOLLOW_UP, TEACHER_ID);

        verify(fixture.studentGradeRepository).delete(grade.getId());

        final StudentGradeHistoryEntity history = fixture.capturedHistory();
        assertThat(history.getChangeType()).isEqualTo(GradeChangeType.DELETED);
        assertThat(history.getPreviousValue()).isEqualTo(450);
        assertThat(history.getNewValue()).isNull();
        assertThat(history.getChangedBy()).isEqualTo(TEACHER_ID);
        assertThat(history.getChangedAt()).isEqualTo(NOW);
    }

    @Test
    @DisplayName("Should fail when the student has no grade of that type")
    void shouldFailWhenTheStudentHasNoGradeOfThatType() {
        assertThatThrownBy(() -> useCase.execute(GROUP_ID, STUDENT_ID, GradeType.FOLLOW_UP, TEACHER_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(MessageCatalog.STUDENT_GRADE_NOT_FOUND.name());

        verify(fixture.studentGradeRepository, never()).delete(any());
        verify(fixture.historyRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should fail when the registration period is closed")
    void shouldFailWhenTheRegistrationPeriodIsClosed() {
        fixture.existingGrade(GradeType.FOLLOW_UP, 450);
        fixture.closedPeriod(GradeType.FOLLOW_UP);

        assertThatThrownBy(() -> useCase.execute(GROUP_ID, STUDENT_ID, GradeType.FOLLOW_UP, TEACHER_ID))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_OUTSIDE_REGISTRATION_PERIOD.name());

        verify(fixture.studentGradeRepository, never()).delete(any());
    }
}
