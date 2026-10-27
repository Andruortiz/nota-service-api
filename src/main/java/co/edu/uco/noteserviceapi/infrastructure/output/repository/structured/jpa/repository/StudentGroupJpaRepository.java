package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository;

import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.StudentGroupJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentGroupJpaRepository extends JpaRepository<StudentGroupJpaEntity, UUID>, JpaSpecificationExecutor<StudentGroupJpaEntity> {

    Optional<StudentGroupJpaEntity> findByStudentIdAndGroupId(UUID studentId, UUID groupId);

    List<StudentGroupJpaEntity> findByGroupIdAndActiveTrue(UUID groupId);
}
