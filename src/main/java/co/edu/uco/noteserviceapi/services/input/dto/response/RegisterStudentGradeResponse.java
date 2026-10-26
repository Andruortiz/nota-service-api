package co.edu.uco.noteserviceapi.services.input.dto.response;

import java.util.UUID;

public class RegisterStudentGradeResponse {

    private UUID id;

    public RegisterStudentGradeResponse(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
