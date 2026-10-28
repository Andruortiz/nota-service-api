package co.edu.uco.noteserviceapi.services.usecase;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.TeacherEntity;

public record StudentGradeContext(
        TeacherEntity teacher,
        GroupEntity group,
        StudentEntity student,
        StudentGroupEntity studentGroup) {
}
