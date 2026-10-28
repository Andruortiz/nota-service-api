package co.edu.uco.noteserviceapi.usecase;

import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.ANOTHER_TEACHER_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.GROUP_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.STUDENT_GROUP_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.STUDENT_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.TEACHER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GroupDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentGradeResponse;
import co.edu.uco.noteserviceapi.services.usecase.ListStudentGradesUseCase;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListStudentGradesUseCaseTest {

    private final StudentGradeFixture fixture = new StudentGradeFixture();

    private final ListStudentGradesUseCase useCase = new ListStudentGradesUseCase(
            fixture.groupRepository,
            fixture.studentGroupRepository,
            fixture.studentGradeRepository,
            new GroupDomain());

    @Test
    @DisplayName("Should list the grades converting hundredths to a decimal value")
    void shouldListTheGradesConvertingHundredthsToADecimalValue() {
        final StudentGradeEntity grade = fixture.existingGrade(GradeType.MIDTERM, 375);
        when(fixture.studentGradeRepository.findByStudentGroupId(STUDENT_GROUP_ID)).thenReturn(List.of(grade));

        final List<StudentGradeResponse> grades = useCase.execute(GROUP_ID, STUDENT_ID, TEACHER_ID);

        assertThat(grades).hasSize(1);
        assertThat(grades.get(0).type()).isEqualTo(GradeType.MIDTERM);
        assertThat(grades.get(0).value()).isEqualByComparingTo(new BigDecimal("3.75"));
    }

    @Test
    @DisplayName("Should return an empty list when the student has no grades")
    void shouldReturnAnEmptyListWhenTheStudentHasNoGrades() {
        assertThat(useCase.execute(GROUP_ID, STUDENT_ID, TEACHER_ID)).isEmpty();
    }

    @Test
    @DisplayName("Should fail when the group belongs to another teacher")
    void shouldFailWhenTheGroupBelongsToAnotherTeacher() {
        assertThatThrownBy(() -> useCase.execute(GROUP_ID, STUDENT_ID, ANOTHER_TEACHER_ID))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GROUP_TEACHER_NOT_ASSIGNED.name());
    }

    @Test
    @DisplayName("Should fail when the student is not enrolled in the group")
    void shouldFailWhenTheStudentIsNotEnrolledInTheGroup() {
        when(fixture.studentGroupRepository.findByStudentIdAndGroupId(STUDENT_ID, GROUP_ID))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(GROUP_ID, STUDENT_ID, TEACHER_ID))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.STUDENT_GROUP_NOT_FOUND.name());
    }
}
