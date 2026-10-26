package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.StudentGradeJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {GradeEntityMapper.class, StudentGroupEntityMapper.class})
public interface StudentGradeEntityMapper {

    StudentGradeJpaEntity toJpa(StudentGradeEntity entity);

    StudentGradeEntity toDomain(StudentGradeJpaEntity jpaEntity);
}
