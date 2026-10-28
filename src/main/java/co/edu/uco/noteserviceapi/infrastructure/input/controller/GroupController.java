package co.edu.uco.noteserviceapi.infrastructure.input.controller;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.infrastructure.input.security.TeacherIdClaim;
import co.edu.uco.noteserviceapi.services.input.dto.request.UpdateStudentGradeRequest;
import co.edu.uco.noteserviceapi.services.input.dto.response.GroupResponse;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentGradeResponse;
import co.edu.uco.noteserviceapi.services.input.dto.response.StudentResponse;
import co.edu.uco.noteserviceapi.services.input.port.DeleteStudentGradePort;
import co.edu.uco.noteserviceapi.services.input.port.ListGroupStudentsPort;
import co.edu.uco.noteserviceapi.services.input.port.ListStudentGradesPort;
import co.edu.uco.noteserviceapi.services.input.port.ListTeacherGroupsPort;
import co.edu.uco.noteserviceapi.services.input.port.UpdateStudentGradePort;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final ListTeacherGroupsPort listTeacherGroupsPort;
    private final ListGroupStudentsPort listGroupStudentsPort;
    private final ListStudentGradesPort listStudentGradesPort;
    private final UpdateStudentGradePort updateStudentGradePort;
    private final DeleteStudentGradePort deleteStudentGradePort;

    public GroupController(
            final ListTeacherGroupsPort listTeacherGroupsPort,
            final ListGroupStudentsPort listGroupStudentsPort,
            final ListStudentGradesPort listStudentGradesPort,
            final UpdateStudentGradePort updateStudentGradePort,
            final DeleteStudentGradePort deleteStudentGradePort) {
        this.listTeacherGroupsPort = listTeacherGroupsPort;
        this.listGroupStudentsPort = listGroupStudentsPort;
        this.listStudentGradesPort = listStudentGradesPort;
        this.updateStudentGradePort = updateStudentGradePort;
        this.deleteStudentGradePort = deleteStudentGradePort;
    }

    @GetMapping
    public List<GroupResponse> list(@AuthenticationPrincipal final Jwt jwt) {
        return listTeacherGroupsPort.execute(TeacherIdClaim.from(jwt));
    }

    @GetMapping("/{groupId}/students")
    public List<StudentResponse> listStudents(
            @PathVariable final UUID groupId,
            @AuthenticationPrincipal final Jwt jwt) {
        return listGroupStudentsPort.execute(groupId, TeacherIdClaim.from(jwt));
    }

    @GetMapping("/{groupId}/students/{studentId}/grades")
    public List<StudentGradeResponse> listStudentGrades(
            @PathVariable final UUID groupId,
            @PathVariable final UUID studentId,
            @AuthenticationPrincipal final Jwt jwt) {
        return listStudentGradesPort.execute(groupId, studentId, TeacherIdClaim.from(jwt));
    }

    @PutMapping("/{groupId}/students/{studentId}/grades/{type}")
    public StudentGradeResponse updateStudentGrade(
            @PathVariable final UUID groupId,
            @PathVariable final UUID studentId,
            @PathVariable final GradeType type,
            @RequestBody final UpdateStudentGradeRequest request,
            @AuthenticationPrincipal final Jwt jwt) {
        return updateStudentGradePort.execute(groupId, studentId, type, TeacherIdClaim.from(jwt), request);
    }

    @DeleteMapping("/{groupId}/students/{studentId}/grades/{type}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentGrade(
            @PathVariable final UUID groupId,
            @PathVariable final UUID studentId,
            @PathVariable final GradeType type,
            @AuthenticationPrincipal final Jwt jwt) {
        deleteStudentGradePort.execute(groupId, studentId, type, TeacherIdClaim.from(jwt));
    }
}
