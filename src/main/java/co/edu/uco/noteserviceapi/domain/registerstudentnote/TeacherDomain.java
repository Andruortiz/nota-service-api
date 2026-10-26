package co.edu.uco.noteserviceapi.domain.registerstudentnote;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.TeacherEntity;

public class TeacherDomain {

    public void validateActive(final TeacherEntity teacher) {
        if (!teacher.isActive()) {
            throw new BusinessRuleException(
                    MessageCatalog.TEACHER_INACTIVE.name());
        }
    }
}