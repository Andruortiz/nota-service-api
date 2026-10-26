package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository;

import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.StudentGradeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface StudentGradeJpaRepository extends JpaRepository<StudentGradeJpaEntity, UUID>, JpaSpecificationExecutor<StudentGradeJpaEntity> {

    boolean existsByGradeIdAndStudentGroupId(UUID gradeId, UUID studentGroupId);
}
