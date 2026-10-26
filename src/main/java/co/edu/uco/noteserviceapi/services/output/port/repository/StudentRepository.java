package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;

import java.util.UUID;

public interface StudentRepository extends BaseRepository<UUID, StudentEntity> {
}
