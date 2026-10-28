package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.adapter;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeHistoryEntity;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.mapper.StudentGradeHistoryEntityMapper;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository.StudentGradeHistoryJpaRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeHistoryRepository;
import org.springframework.stereotype.Component;

@Component
public class StudentGradeHistoryRepositoryAdapter implements StudentGradeHistoryRepository {

    private final StudentGradeHistoryJpaRepository jpaRepository;
    private final StudentGradeHistoryEntityMapper mapper;

    public StudentGradeHistoryRepositoryAdapter(
            final StudentGradeHistoryJpaRepository jpaRepository,
            final StudentGradeHistoryEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void create(final StudentGradeHistoryEntity entity) {
        jpaRepository.save(mapper.toJpa(entity));
    }
}
