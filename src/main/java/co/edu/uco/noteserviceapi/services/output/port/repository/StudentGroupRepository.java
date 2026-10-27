package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentGroupRepository extends BaseRepository<UUID, StudentGroupEntity> {

    Optional<StudentGroupEntity> findByStudentIdAndGroupId(UUID studentId, UUID groupId);

    List<StudentGroupEntity> findActiveByGroupId(UUID groupId);

}
