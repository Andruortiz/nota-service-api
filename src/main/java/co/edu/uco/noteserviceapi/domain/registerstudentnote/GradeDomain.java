package co.edu.uco.noteserviceapi.domain.registerstudentnote;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.crosscutting.util.helper.DateHelper;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GradeEntity;

import java.time.Instant;
public class GradeDomain {

    public void validateRegistrationPeriod(
            final GradeEntity grade,
            final Instant currentTimestamp) {

        if (!DateHelper.isBetween(
                currentTimestamp,
                grade.getRegistrationDateFrom(),
                grade.getRegistrationDateTo())) {

            throw new BusinessRuleException(
                    MessageCatalog.GRADE_OUTSIDE_REGISTRATION_PERIOD.name());
        }
    }


}