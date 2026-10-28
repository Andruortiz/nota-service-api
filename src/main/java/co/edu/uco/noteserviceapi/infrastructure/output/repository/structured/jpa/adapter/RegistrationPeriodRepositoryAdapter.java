package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.adapter;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.RegistrationPeriodEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper.RegistrationPeriodEntityMapper;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository.RegistrationPeriodJpaRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.RegistrationPeriodRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RegistrationPeriodRepositoryAdapter implements RegistrationPeriodRepository {

    private final RegistrationPeriodJpaRepository jpaRepository;
    private final RegistrationPeriodEntityMapper mapper;

    public RegistrationPeriodRepositoryAdapter(
            final RegistrationPeriodJpaRepository jpaRepository,
            final RegistrationPeriodEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(final RegistrationPeriodEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void update(final RegistrationPeriodEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void delete(final GradeType type) {
        jpaRepository.deleteById(type);
    }

    @Override
    public Optional<RegistrationPeriodEntity> findById(final GradeType type) {
        return jpaRepository.findById(type).map(mapper::toDomain);
    }

    @Override
    public List<RegistrationPeriodEntity> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
