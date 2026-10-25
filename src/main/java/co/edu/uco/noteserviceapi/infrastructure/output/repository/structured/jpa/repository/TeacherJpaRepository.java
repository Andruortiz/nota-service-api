package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.repository;

import co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity.TeacherJpaEntity;
import co.edu.uco.noteserviceapi.services.output.port.repository.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TeacherJpaRepository extends JpaRepository<TeacherJpaEntity, UUID>, JpaSpecificationExecutor<TeacherJpaEntity> {
}
