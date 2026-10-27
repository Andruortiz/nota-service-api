package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.adapter;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper.StudentGradeEntityMapper;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository.StudentGradeJpaRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class StudentGradeRepositoryAdapter implements StudentGradeRepository {

    private final StudentGradeJpaRepository jpaRepository;
    private final StudentGradeEntityMapper mapper;

    public StudentGradeRepositoryAdapter(
            final StudentGradeJpaRepository jpaRepository,
            final StudentGradeEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(final StudentGradeEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void update(final StudentGradeEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }

    @Override
    public void delete(final UUID uuid) {
        jpaRepository.deleteById(uuid);
    }

    @Override
    public Optional<StudentGradeEntity> findById(final UUID uuid) {
        return jpaRepository.findById(uuid).map(mapper::toDomain);
    }

    @Override
    public List<StudentGradeEntity> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByGradeIdAndStudentGroupId(final UUID gradeId, final UUID studentGroupId) {
        return jpaRepository.existsByGradeIdAndStudentGroupId(gradeId, studentGroupId);
    }

    @Override
    public List<StudentGradeEntity> findByStudentGroupId(final UUID studentGroupId) {
        return jpaRepository.findByStudentGroupId(studentGroupId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
