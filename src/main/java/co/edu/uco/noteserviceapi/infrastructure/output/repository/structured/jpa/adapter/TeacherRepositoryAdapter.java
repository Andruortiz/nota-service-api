package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.adapter;


import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.TeacherEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper.TeacherEntityMapper;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository.TeacherJpaRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.TeacherRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TeacherRepositoryAdapter implements TeacherRepository {

    private final TeacherJpaRepository jpaRepository;
    private final TeacherEntityMapper mapper;


    public TeacherRepositoryAdapter(
            final TeacherJpaRepository jpaRepository,
            final TeacherEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(final TeacherEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));

    }

    @Override
    public void delete(final UUID uuid) {
        jpaRepository.deleteById(uuid);

    }

    @Override
    public Optional<TeacherEntity> findById(final UUID uuid) {
        return jpaRepository.findById(uuid).map(mapper::toDomain);
    }

    @Override
    public List<TeacherEntity> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }


    @Override
    public void update(TeacherEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));

    }
}
