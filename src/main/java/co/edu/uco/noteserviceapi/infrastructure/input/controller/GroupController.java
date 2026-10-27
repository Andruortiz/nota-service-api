package co.edu.uco.noteserviceapi.infrastructure.input.controller;

import co.edu.uco.noteserviceapi.infrastructure.input.security.TeacherIdClaim;
import co.edu.uco.noteserviceapi.services.input.dto.response.GroupResponse;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentGradeResponse;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentResponse;
import co.edu.uco.noteserviceapi.services.input.port.ListGroupStudentsPort;
import co.edu.uco.noteserviceapi.services.input.port.ListStudentGradesPort;
import co.edu.uco.noteserviceapi.services.input.port.ListTeacherGroupsPort;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/grupos")
public class GroupController {

    private final ListTeacherGroupsPort listTeacherGroupsPort;
    private final ListGroupStudentsPort listGroupStudentsPort;
    private final ListStudentGradesPort listStudentGradesPort;

    public GroupController(
            final ListTeacherGroupsPort listTeacherGroupsPort,
            final ListGroupStudentsPort listGroupStudentsPort,
            final ListStudentGradesPort listStudentGradesPort) {
        this.listTeacherGroupsPort = listTeacherGroupsPort;
        this.listGroupStudentsPort = listGroupStudentsPort;
        this.listStudentGradesPort = listStudentGradesPort;
    }

    @GetMapping
    public List<GroupResponse> list(@AuthenticationPrincipal final Jwt jwt) {
        return listTeacherGroupsPort.execute(TeacherIdClaim.from(jwt));
    }

    @GetMapping("/{groupId}/estudiantes")
    public List<StudentResponse> listStudents(
            @PathVariable final UUID groupId,
            @AuthenticationPrincipal final Jwt jwt) {
        return listGroupStudentsPort.execute(groupId, TeacherIdClaim.from(jwt));
    }

    @GetMapping("/{groupId}/estudiantes/{studentId}/notas")
    public List<StudentGradeResponse> listStudentGrades(
            @PathVariable final UUID groupId,
            @PathVariable final UUID studentId,
            @AuthenticationPrincipal final Jwt jwt) {
        return listStudentGradesPort.execute(groupId, studentId, TeacherIdClaim.from(jwt));
    }
}
