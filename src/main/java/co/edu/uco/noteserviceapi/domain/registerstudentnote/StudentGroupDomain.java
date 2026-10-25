package co.edu.uco.noteserviceapi.domain.registerstudentnote;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.services.output.port.repository.entity.StudentGroupEntity;

public class StudentGroupDomain {

    public void validateEnrollment(
            final StudentGroupEntity studentGroup) {

        if (studentGroup == null) {
            throw new BusinessRuleException(
                    MessageCatalog.STUDENT_GROUP_NOT_FOUND.name());
        }
    }

}