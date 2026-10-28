package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.RegistrationPeriodJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationPeriodJpaRepository extends JpaRepository<RegistrationPeriodJpaEntity, GradeType> {
}
