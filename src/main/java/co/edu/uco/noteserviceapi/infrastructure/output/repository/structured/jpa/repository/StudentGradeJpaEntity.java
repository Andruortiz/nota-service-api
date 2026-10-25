package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface StudentGradeJpaEntity extends JpaRepository<StudentGradeJpaEntity, UUID>, JpaSpecificationExecutor<StudentGradeJpaEntity> {
}
