package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper;


import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.GroupJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TeacherEntityMapper.class)
public interface GroupEntityMapper {


    GroupJpaEntity toJpa(GroupEntity entity);

    GroupEntity toDomain(GroupJpaEntity jpaEntity);
}
