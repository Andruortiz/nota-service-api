package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeHistoryEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.StudentGradeHistoryJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {StudentGroupEntityMapper.class})
public interface StudentGradeHistoryEntityMapper {

    StudentGradeHistoryJpaEntity toJpa(StudentGradeHistoryEntity entity);

    StudentGradeHistoryEntity toDomain(StudentGradeHistoryJpaEntity jpaEntity);
}
