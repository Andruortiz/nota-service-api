package co.edu.uco.noteserviceapi.domain.registerstudentnote;

import co.edu.uco.noteserviceapi.crosscutting.exception.BusinessRuleException;
import co.edu.uco.noteserviceapi.crosscutting.message.MessageCatalog;
import co.edu.uco.noteserviceapi.crosscutting.util.helper.DateHelper;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.RegistrationPeriodEntity;

import java.time.Instant;

public class RegistrationPeriodDomain {

    public boolean isOpen(
            final RegistrationPeriodEntity period,
            final Instant currentTimestamp) {

        return DateHelper.isBetween(
                currentTimestamp,
                period.getRegistrationDateFrom(),
                period.getRegistrationDateTo());
    }

    public void validateOpen(
            final RegistrationPeriodEntity period,
            final Instant currentTimestamp) {

        if (!isOpen(period, currentTimestamp)) {
            throw new BusinessRuleException(
                    MessageCatalog.GRADE_OUTSIDE_REGISTRATION_PERIOD.name());
        }
    }
}
