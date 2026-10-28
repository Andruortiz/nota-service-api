package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.StudentGradeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentGradeJpaRepository extends JpaRepository<StudentGradeJpaEntity, UUID>, JpaSpecificationExecutor<StudentGradeJpaEntity> {

    boolean existsByStudentGroupIdAndType(UUID studentGroupId, GradeType type);

    Optional<StudentGradeJpaEntity> findByStudentGroupIdAndType(UUID studentGroupId, GradeType type);

    List<StudentGradeJpaEntity> findByStudentGroupId(UUID studentGroupId);
}
