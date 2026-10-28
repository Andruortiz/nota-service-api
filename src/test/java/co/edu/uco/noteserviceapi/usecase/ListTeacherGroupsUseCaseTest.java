package co.edu.uco.noteserviceapi.usecase;

import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.GROUP_ID;
import static co.edu.uco.noteserviceapi.usecase.StudentGradeFixture.TEACHER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.services.input.dto.response.GroupResponse;
import co.edu.uco.noteserviceapi.services.usecase.ListTeacherGroupsUseCase;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ListTeacherGroupsUseCaseTest {

    private final StudentGradeFixture fixture = new StudentGradeFixture();

    private final ListTeacherGroupsUseCase useCase = new ListTeacherGroupsUseCase(fixture.groupRepository);

    @Test
    @DisplayName("Should list the active groups of the teacher")
    void shouldListTheActiveGroupsOfTheTeacher() {
        final GroupEntity group = fixture.studentGroup.getGroup();
        group.setName("Calculo 101");
        when(fixture.groupRepository.findActiveByTeacherId(TEACHER_ID)).thenReturn(List.of(group));

        assertThat(useCase.execute(TEACHER_ID)).containsExactly(new GroupResponse(GROUP_ID, "Calculo 101"));
    }

    @Test
    @DisplayName("Should return an empty list when the teacher has no active groups")
    void shouldReturnAnEmptyListWhenTheTeacherHasNoActiveGroups() {
        assertThat(useCase.execute(TEACHER_ID)).isEmpty();
    }
}
