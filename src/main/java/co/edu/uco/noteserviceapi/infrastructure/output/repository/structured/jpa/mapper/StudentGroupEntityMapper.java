package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.StudentGroupJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
       uses = {StudentEntityMapper.class, GroupEntityMapper.class})
public interface StudentGroupEntityMapper {


    StudentGroupJpaEntity toJpa(StudentGroupEntity entity);

    StudentGroupEntity toDomain(StudentGroupJpaEntity jpaEntity);
}
