package co.edu.uco.noteserviceapi.usecase;

import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.GROUP_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.STUDENT_GROUP_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.STUDENT_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.TEACHER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ConflictException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeChangeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeHistoryEntity;
import co.edu.uco.noteserviceapi.services.input.dto.request.RegisterStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.RegisterStudentGradeResponse;
import co.edu.uco.noteserviceapi.services.usecase.RegisterStudentGradeUseCase;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class RegisterStudentGradeUseCaseTest {

    private final StudentGradeFixture fixture = new StudentGradeFixture();

    private final RegisterStudentGradeUseCase useCase = new RegisterStudentGradeUseCase(
            fixture.contextResolver,
            fixture.studentGradeRepository,
            fixture.historyRecorder,
            fixture.studentGradeDomain);

    private RegisterStudentGradeRequest request(final GradeType type, final String score) {
        final RegisterStudentGradeRequest request = new RegisterStudentGradeRequest();
        request.setTeacherId(TEACHER_ID);
        request.setGroupId(GROUP_ID);
        request.setStudentId(STUDENT_ID);
        request.setType(type);
        request.setScore(new BigDecimal(score));
        return request;
    }

    @Test
    @DisplayName("Should register the grade in hundredths and record its creation")
    void shouldRegisterTheGradeInHundredthsAndRecordItsCreation() {
        final RegisterStudentGradeResponse response = useCase.execute(request(GradeType.FOLLOW_UP, "4.5"));

        final ArgumentCaptor<StudentGradeEntity> captor = ArgumentCaptor.forClass(StudentGradeEntity.class);
        verify(fixture.studentGradeRepository).create(captor.capture());
        final StudentGradeEntity saved = captor.getValue();
        assertThat(saved.getValue()).isEqualTo(450);
        assertThat(saved.getType()).isEqualTo(GradeType.FOLLOW_UP);
        assertThat(saved.getStudentGroup().getId()).isEqualTo(STUDENT_GROUP_ID);
        assertThat(response.getId()).isEqualTo(saved.getId());

        final StudentGradeHistoryEntity history = fixture.capturedHistory();
        assertThat(history.getChangeType()).isEqualTo(GradeChangeType.CREATED);
        assertThat(history.getPreviousValue()).isNull();
        assertThat(history.getNewValue()).isEqualTo(450);
    }

    @Test
    @DisplayName("Should fail when the student already has a grade of that type")
    void shouldFailWhenTheStudentAlreadyHasAGradeOfThatType() {
        when(fixture.studentGradeRepository.existsByStudentGroupIdAndType(STUDENT_GROUP_ID, GradeType.FOLLOW_UP))
                .thenReturn(true);

        assertThatThrownBy(() -> useCase.execute(request(GradeType.FOLLOW_UP, "4.5")))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining(MessageCatalog.STUDENT_GRADE_ALREADY_REGISTERED.name());

        verify(fixture.studentGradeRepository, never()).create(any());
        verify(fixture.historyRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should fail when the registration period is closed")
    void shouldFailWhenTheRegistrationPeriodIsClosed() {
        fixture.closedPeriod(GradeType.FOLLOW_UP);

        assertThatThrownBy(() -> useCase.execute(request(GradeType.FOLLOW_UP, "4.5")))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GRADE_OUTSIDE_REGISTRATION_PERIOD.name());

        verify(fixture.studentGradeRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should fail when no registration period is defined for the type")
    void shouldFailWhenNoRegistrationPeriodIsDefinedForTheType() {
        assertThatThrownBy(() -> useCase.execute(request(GradeType.MIDTERM, "4.5")))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(MessageCatalog.REGISTRATION_PERIOD_NOT_FOUND.name());
    }

    @Test
    @DisplayName("Should fail when the student is not enrolled in the group")
    void shouldFailWhenTheStudentIsNotEnrolledInTheGroup() {
        when(fixture.studentGroupRepository.findByStudentIdAndGroupId(STUDENT_ID, GROUP_ID))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(request(GradeType.FOLLOW_UP, "4.5")))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.STUDENT_GROUP_NOT_FOUND.name());

        verify(fixture.studentGradeRepository, never()).create(any());
    }

    @Test
    @DisplayName("Should fail when the teacher does not exist")
    void shouldFailWhenTheTeacherDoesNotExist() {
        final RegisterStudentGradeRequest request = request(GradeType.FOLLOW_UP, "4.5");
        request.setTeacherId(UUID.fromString("019a0000-0000-7000-8000-0000000000ff"));

        assertThatThrownBy(() -> useCase.execute(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(MessageCatalog.TEACHER_NOT_FOUND.name());
    }
}
