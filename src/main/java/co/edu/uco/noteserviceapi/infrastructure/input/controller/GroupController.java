package co.edu.uco.noteserviceapi.infrastructure.input.controller;

import co.edu.uco.noteserviceapi.infrastructure.input.security.TeacherIdClaim;
import co.edu.uco.noteserviceapi.services.input.dto.response.GroupResponse;
import co.edu.uco.noteserviceapi.services.input.port.ListTeacherGroupsPort;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/grupos")
public class GroupController {

    private final ListTeacherGroupsPort listTeacherGroupsPort;

    public GroupController(final ListTeacherGroupsPort listTeacherGroupsPort) {
        this.listTeacherGroupsPort = listTeacherGroupsPort;
    }

    @GetMapping
    public List<GroupResponse> list(@AuthenticationPrincipal final Jwt jwt) {
        return listTeacherGroupsPort.execute(TeacherIdClaim.from(jwt));
    }
}
