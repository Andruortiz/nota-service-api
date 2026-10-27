package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository;

import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.GroupJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface GroupJpaRepository extends JpaRepository<GroupJpaEntity, UUID>, JpaSpecificationExecutor<GroupJpaEntity> {

    List<GroupJpaEntity> findByTeacherIdAndActiveTrue(UUID teacherId);
}
