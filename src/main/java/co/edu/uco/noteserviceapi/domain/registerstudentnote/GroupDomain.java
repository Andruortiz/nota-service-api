package co.edu.uco.noteserviceapi.domain.registerstudentnote;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.services.output.port.repository.entity.GroupEntity;

import java.util.UUID;

public class GroupDomain {


    public void validateActive(final GroupEntity group) {

        if (!group.isActive()) {
            throw new BusinessRuleException(
                    MessageCatalog.GROUP_INACTIVE.name());
        }
    }

    public void validateAssignedTeacher(
            final GroupEntity group,
            final UUID teacherId) {

        if (!group.getTeacher()
                .getId()
                .equals(teacherId)) {

            throw new BusinessRuleException(
                    MessageCatalog.GROUP_TEACHER_NOT_ASSIGNED.name());
        }
    }
}
