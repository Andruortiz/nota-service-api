package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.adapter;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper.StudentGroupEntityMapper;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository.StudentGroupJpaRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class StudentGroupRepositoryAdapter implements StudentGroupRepository {

    private final StudentGroupJpaRepository jpaRepository;
    private final StudentGroupEntityMapper mapper;

    public StudentGroupRepositoryAdapter(
            final StudentGroupJpaRepository jpaRepository,
            final StudentGroupEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(final StudentGroupEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void update(final StudentGroupEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void delete(final UUID uuid) {
        jpaRepository.deleteById(uuid);
    }

    @Override
    public Optional<StudentGroupEntity> findById(final UUID uuid) {
        return jpaRepository.findById(uuid).map(mapper::toDomain);
    }

    @Override
    public List<StudentGroupEntity> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<StudentGroupEntity> findByStudentIdAndGroupId(final UUID studentId, final UUID groupId) {
        return jpaRepository.findByStudentIdAndGroupId(studentId, groupId).map(mapper::toDomain);
    }

    @Override
    public List<StudentGroupEntity> findActiveByGroupId(final UUID groupId) {
        return jpaRepository.findByGroupIdAndActiveTrue(groupId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
