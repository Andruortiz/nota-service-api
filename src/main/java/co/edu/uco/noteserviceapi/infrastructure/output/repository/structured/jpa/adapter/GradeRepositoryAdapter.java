package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.adapter;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GradeEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper.GradeEntityMapper;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository.GradeJpaRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.GradeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GradeRepositoryAdapter implements GradeRepository {

    private final GradeJpaRepository jpaRepository;
    private final GradeEntityMapper mapper;

    public GradeRepositoryAdapter(
            final GradeJpaRepository jpaRepository,
            final GradeEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(final GradeEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void update(final GradeEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void delete(final UUID uuid) {
        jpaRepository.deleteById(uuid);
    }

    @Override
    public Optional<GradeEntity> findById(final UUID uuid) {
        return jpaRepository.findById(uuid).map(mapper::toDomain);
    }

    @Override
    public List<GradeEntity> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
