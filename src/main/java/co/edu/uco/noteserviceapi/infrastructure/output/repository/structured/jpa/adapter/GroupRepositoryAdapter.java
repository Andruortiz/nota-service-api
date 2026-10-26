package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.adapter;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper.GroupEntityMapper;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository.GroupJpaRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GroupRepositoryAdapter implements GroupRepository {

    private final GroupJpaRepository jpaRepository;
    private final GroupEntityMapper mapper;

    public GroupRepositoryAdapter(
            final GroupJpaRepository jpaRepository,
            final GroupEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(final GroupEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void update(final GroupEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void delete(final UUID uuid) {
        jpaRepository.deleteById(uuid);
    }

    @Override
    public Optional<GroupEntity> findById(final UUID uuid) {
        return jpaRepository.findById(uuid).map(mapper::toDomain);
    }

    @Override
    public List<GroupEntity> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
