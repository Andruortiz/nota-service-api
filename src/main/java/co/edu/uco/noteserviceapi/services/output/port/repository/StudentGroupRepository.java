package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.services.output.port.repository.entity.StudentGroupEntity;

import java.util.UUID;

public interface StudentGroupRepository extends CrudRepository<UUID, StudentGroupEntity> {
}
