package co.edu.uco.noteserviceapi.usecase;

import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.ANOTHER_TEACHER_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.GROUP_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.STUDENT_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.TEACHER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.exception.ResourceNotFoundException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GroupDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentResponse;
import co.edu.uco.noteserviceapi.services.usecase.ListGroupStudentsUseCase;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListGroupStudentsUseCaseTest {

    private final StudentGradeFixture fixture = new StudentGradeFixture();

    private final ListGroupStudentsUseCase useCase = new ListGroupStudentsUseCase(
            fixture.groupRepository, fixture.studentGroupRepository, new GroupDomain());

    @Test
    @DisplayName("Should list only the active students of the group")
    void shouldListOnlyTheActiveStudentsOfTheGroup() {
        fixture.studentGroup.getStudent().setName("Ana Perez");

        final StudentEntity inactive = new StudentEntity();
        inactive.setId(UUID.randomUUID());
        inactive.setName("Luis Gomez");
        inactive.setActive(false);
        final StudentGroupEntity inactiveStudentEnrollment = new StudentGroupEntity();
        inactiveStudentEnrollment.setStudent(inactive);
        inactiveStudentEnrollment.setActive(true);

        when(fixture.studentGroupRepository.findActiveByGroupId(GROUP_ID))
                .thenReturn(List.of(fixture.studentGroup, inactiveStudentEnrollment));

        assertThat(useCase.execute(GROUP_ID, TEACHER_ID))
                .containsExactly(new StudentResponse(STUDENT_ID, "Ana Perez"));
    }

    @Test
    @DisplayName("Should fail when the group does not exist")
    void shouldFailWhenTheGroupDoesNotExist() {
        final UUID unknownGroup = UUID.randomUUID();

        assertThatThrownBy(() -> useCase.execute(unknownGroup, TEACHER_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(MessageCatalog.GROUP_NOT_FOUND.name());
    }

    @Test
    @DisplayName("Should fail when the group belongs to another teacher")
    void shouldFailWhenTheGroupBelongsToAnotherTeacher() {
        assertThatThrownBy(() -> useCase.execute(GROUP_ID, ANOTHER_TEACHER_ID))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining(MessageCatalog.GROUP_TEACHER_NOT_ASSIGNED.name());
    }
}
