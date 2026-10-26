package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper;


import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.StudentJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface StudentEntityMapper {

    StudentJpaEntity toJpa(StudentEntity entity);

    StudentEntity toDomain(StudentJpaEntity jpaEntity);

}
