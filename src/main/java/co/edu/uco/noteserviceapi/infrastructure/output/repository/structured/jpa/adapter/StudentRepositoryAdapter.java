package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.adapter;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper.StudentEntityMapper;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository.StudentJpaRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class StudentRepositoryAdapter implements StudentRepository {

    private final StudentJpaRepository jpaRepository;
    private final StudentEntityMapper mapper;

    public StudentRepositoryAdapter(
            final StudentJpaRepository jpaRepository,
            final StudentEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(final StudentEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void update(final StudentEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void delete(final UUID uuid) {
        jpaRepository.deleteById(uuid);
    }

    @Override
    public Optional<StudentEntity> findById(final UUID uuid) {
        return jpaRepository.findById(uuid).map(mapper::toDomain);
    }

    @Override
    public List<StudentEntity> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
