package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.services.output.port.repository.entity.StudentGradeEntity;

import java.util.UUID;

public interface StudentGradeRepository extends CrudRepository<UUID, StudentGradeEntity> {
}
