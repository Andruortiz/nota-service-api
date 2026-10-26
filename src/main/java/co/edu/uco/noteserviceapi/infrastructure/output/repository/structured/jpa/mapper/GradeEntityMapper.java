package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper;


import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GradeEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.GradeJpaEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "Spring")
public interface GradeEntityMapper {

    GradeJpaEntity toJpa(GradeEntity entity);

    GradeEntity toDomain(GradeJpaEntity jpaEntity);
}
