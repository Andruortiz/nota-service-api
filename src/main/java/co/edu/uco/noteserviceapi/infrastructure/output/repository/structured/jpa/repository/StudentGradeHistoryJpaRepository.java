package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository;

import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.StudentGradeHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentGradeHistoryJpaRepository extends JpaRepository<StudentGradeHistoryJpaEntity, UUID> {
}
