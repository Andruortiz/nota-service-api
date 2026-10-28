package co.edu.uco.noteserviceapi.infrastructure.input.controller;

import co.edu.uco.noteserviceapi.infrastructure.input.security.TeacherIdClaim;
import co.edu.uco.noteserviceapi.services.input.dto.request.RegisterStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.RegisterStudentGradeResponse;
import co.edu.uco.noteserviceapi.services.input.port.RegisterStudentGradePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student-grades")
public class StudentGradeController {

    private final RegisterStudentGradePort registerStudentGradePort;

    public StudentGradeController(final RegisterStudentGradePort registerStudentGradePort) {
        this.registerStudentGradePort = registerStudentGradePort;
    }

    @PostMapping
    public ResponseEntity<RegisterStudentGradeResponse> register(
            @Valid @RequestBody final RegisterStudentGradeRequest request,
            @AuthenticationPrincipal final Jwt jwt) {

        request.setTeacherId(TeacherIdClaim.from(jwt));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerStudentGradePort.execute(request));
    }
}
