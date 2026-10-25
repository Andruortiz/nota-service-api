package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.services.output.port.repository.entity.GradeEntity;

import java.util.UUID;

public interface GradeRepository extends CrudRepository<UUID, GradeEntity> {
}
