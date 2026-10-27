package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;

import java.util.List;
import java.util.UUID;

public interface GroupRepository extends BaseRepository<UUID, GroupEntity> {

    List<GroupEntity> findActiveByTeacherId(UUID teacherId);
}
