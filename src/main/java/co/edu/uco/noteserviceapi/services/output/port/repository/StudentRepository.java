package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.services.output.port.repository.entity.StudentEntity;

import java.util.UUID;

public interface StudentRepository extends CrudRepository<UUID, StudentEntity>{
}
