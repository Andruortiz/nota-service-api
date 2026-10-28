package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.RegistrationPeriodEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.RegistrationPeriodJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationPeriodEntityMapper {

    RegistrationPeriodJpaEntity toJpa(RegistrationPeriodEntity entity);

    RegistrationPeriodEntity toDomain(RegistrationPeriodJpaEntity jpaEntity);
}
