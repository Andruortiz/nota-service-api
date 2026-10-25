package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.services.output.port.repository.entity.GroupEntity;

import java.util.UUID;

public interface GroupRepository extends CrudRepository<UUID, GroupEntity>{
}
