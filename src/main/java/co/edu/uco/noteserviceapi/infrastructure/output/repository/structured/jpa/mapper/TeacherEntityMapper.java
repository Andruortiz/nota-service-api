package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper;


import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.TeacherEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.TeacherJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherEntityMapper {

    TeacherJpaEntity toJpa(TeacherEntity entity);

    TeacherEntity toDomain(TeacherJpaEntity jpaEntity);
}
