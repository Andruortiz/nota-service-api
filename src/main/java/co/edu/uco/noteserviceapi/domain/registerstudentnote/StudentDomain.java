package co.edu.uco.noteserviceapi.domain.registerstudentnote;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.services.output.port.repository.entity.StudentEntity;

public class StudentDomain {

    public void validateActive(final StudentEntity student) {

        if (!student.isActive()) {
            throw new BusinessRuleException(
                    MessageCatalog.STUDENT_INACTIVE.name());
        }
}
}
