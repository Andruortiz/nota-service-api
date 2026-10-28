package co.edu.uco.noteserviceapi.usecase;

import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.ANOTHER_TEACHER_ID;
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
import co.edu.uco.noteserviceapi.services.input.dto.request.UpdateStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentGradeResponse;
import co.edu.uco.noteserviceapi.services.usecase.UpdateStudentGradeUseCase;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateStudentGradeUseCaseTest {

    private final StudentGradeFixture fixture = new StudentGradeFixture();

    private final UpdateStudentGradeUseCase useCase = new UpdateStudentGradeUseCase(
            fixture.contextResolver,
            fixture.studentGradeRepository,
            fixture.historyRecorder,
            fixture.studentGradeDomain);

    private UpdateStudentGradeRequest request(final String score) {
        final UpdateStudentGradeRequest request = new UpdateStudentGradeRequest();
        request.setScore(score == null ? null : new BigDecimal(score));
        return request;
    }

    @Test
    @DisplayName("Should update the grade and record the change with its previous value")
    void shouldUpdateTheGradeAndRecordTheChangeWithItsPreviousValue() {
        final StudentGradeEntity grade = fixture.existingGrade(GradeType.FOLLOW_UP, 300);

        final StudentGradeResponse response = useCase.execute(
                GROUP_ID, STUDENT_ID, GradeType.FOLLOW_UP, TEACHER_ID, request("4.5"));

        assertThat(grade.getValue()).isEqualTo(450);
        verify(fixture.studentGradeRepository).update(grade);
        assertThat(response.value()).isEqualByComparingTo("4.50");

        final StudentGradeHistoryEntity history = fixture.capturedHistory();
        assertThat(history.getChangeType()).isEqualTo(GradeChangeType.UPDATED);
        assertThat(history.getPreviousValue()).isEqualTo(300);
        assertThat(history.getNewValue()).isEqualTo(450);
        assertThat(history.getChangedBy()).isEqualTo(TEACHER_ID);
        assertThat(history.getChangedAt()).isEqualTo(NOW);
    }

    @Test
    @DisplayName("Should fail when the student has no grade of that type")
    void shouldFailWhenTheStudentHasNoGradeOfThatType() {
        assertThatThrownBy(() -> useCase.execute(
                GROUP_ID, STUDENT_ID, GradeType.FOLLOW_UP, TEACHER_ID, request("4.5")))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(MessageCatalog.STUDENT_GRADE_NOT_FOUND.name());

        verify(fixture.historyRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should fail when the registration period is closed")
    void shouldFailWhenTheRegistrationPeriodIsClosed() {
        fixture.existingGrade(GradeType.FOLLOW_UP, 300);
        fixture.closedPeriod(GradeType.FOLLOW_UP);

        assertThatThrownBy(() -> useCase.execute(
                GROUP_ID, STUDENT_ID, GradeType.FOLLOW_UP, TEACHER_ID, request("4.5")))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_OUTSIDE_REGISTRATION_PERIOD.name());

        verify(fixture.studentGradeRepository, never()).update(any());
        verify(fixture.historyRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should fail when the group belongs to another teacher")
    void shouldFailWhenTheGroupBelongsToAnotherTeacher() {
        fixture.existingGrade(GradeType.FOLLOW_UP, 300);

        assertThatThrownBy(() -> useCase.execute(
                GROUP_ID, STUDENT_ID, GradeType.FOLLOW_UP, ANOTHER_TEACHER_ID, request("4.5")))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GROUP_TEACHER_NOT_ASSIGNED.name());

        verify(fixture.studentGradeRepository, never()).update(any());
    }

    @Test
    @DisplayName("Should reject an invalid grade before touching any data")
    void shouldRejectAnInvalidGradeBeforeTouchingAnyData() {
        fixture.existingGrade(GradeType.FOLLOW_UP, 300);

        assertThatThrownBy(() -> useCase.execute(
                GROUP_ID, STUDENT_ID, GradeType.FOLLOW_UP, TEACHER_ID, request("4.25")))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_INVALID_DECIMALS.name());

        verify(fixture.studentGradeRepository, never()).update(any());
    }
}
