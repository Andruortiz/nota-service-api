package co.edu.uco.noteserviceapi.infrastructure.input.controller;

import co.edu.uco.noteserviceapi.services.input.dto.response.RegistrationPeriodResponse;
import co.edu.uco.noteserviceapi.services.input.port.ListRegistrationPeriodsPort;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/periodos-registro")
public class RegistrationPeriodController {

    private final ListRegistrationPeriodsPort listRegistrationPeriodsPort;

    public RegistrationPeriodController(final ListRegistrationPeriodsPort listRegistrationPeriodsPort) {
        this.listRegistrationPeriodsPort = listRegistrationPeriodsPort;
    }

    @GetMapping
    public List<RegistrationPeriodResponse> list() {
        return listRegistrationPeriodsPort.execute();
    }
}
