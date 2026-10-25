package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.services.output.port.repository.entity.TeacherEntity;

import java.util.UUID;

public interface TeacherRepository extends CrudRepository<UUID, TeacherEntity>{
}
