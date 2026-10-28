package co.edu.uco.noteserviceapi;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureMockMvc
@Testcontainers
class ApiRoutesSecurityTest {

    private static final String GROUP = "/api/v1/groups/" + UUID.randomUUID();
    private static final String STUDENT_GRADES = GROUP + "/students/" + UUID.randomUUID() + "/grades";

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17-alpine");

    @Autowired
    private MockMvc mockMvc;

    private JwtRequestPostProcessor teacher() {
        return jwt().authorities(new SimpleGrantedAuthority("ROLE_DOCENTE"))
                .jwt(token -> token.claim("teacherId", UUID.randomUUID().toString()));
    }

    private JwtRequestPostProcessor withoutTeacherRole() {
        return jwt().authorities(new SimpleGrantedAuthority("ROLE_OTHER"))
                .jwt(token -> token.claim("teacherId", UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("Should reject anonymous requests with 401")
    void shouldRejectAnonymousRequestsWith401() throws Exception {
        mockMvc.perform(get("/api/v1/groups")).andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/registration-periods")).andExpect(status().isUnauthorized());
        mockMvc.perform(delete(STUDENT_GRADES + "/FOLLOW_UP")).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should reject authenticated users without the teacher role with 403")
    void shouldRejectAuthenticatedUsersWithoutTheTeacherRoleWith403() throws Exception {
        mockMvc.perform(get("/api/v1/groups").with(withoutTeacherRole())).andExpect(status().isForbidden());
        mockMvc.perform(get(GROUP + "/students").with(withoutTeacherRole())).andExpect(status().isForbidden());
        mockMvc.perform(get(STUDENT_GRADES).with(withoutTeacherRole())).andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/registration-periods").with(withoutTeacherRole()))
                .andExpect(status().isForbidden());
        mockMvc.perform(post("/api/v1/student-grades").with(withoutTeacherRole())
                        .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isForbidden());
        mockMvc.perform(put(STUDENT_GRADES + "/FOLLOW_UP").with(withoutTeacherRole())
                        .contentType(MediaType.APPLICATION_JSON).content("{\"score\":4.5}"))
                .andExpect(status().isForbidden());
        mockMvc.perform(delete(STUDENT_GRADES + "/FOLLOW_UP").with(withoutTeacherRole()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should answer 403 when the token has no valid teacherId claim")
    void shouldAnswer403WhenTheTokenHasNoValidTeacherIdClaim() throws Exception {
        final JwtRequestPostProcessor withoutClaim = jwt().authorities(new SimpleGrantedAuthority("ROLE_DOCENTE"));

        mockMvc.perform(get("/api/v1/groups").with(withoutClaim))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value("ACCESS_DENIED"));
    }

    @Test
    @DisplayName("Should serve the english routes to a teacher")
    void shouldServeTheEnglishRoutesToATeacher() throws Exception {
        mockMvc.perform(get("/api/v1/groups").with(teacher()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        mockMvc.perform(get("/api/v1/registration-periods").with(teacher()))
                .andExpect(status().isOk());
        mockMvc.perform(get(GROUP + "/students").with(teacher()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("GROUP_NOT_FOUND"));
        mockMvc.perform(get(STUDENT_GRADES).with(teacher()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should answer 400 when the grade type in the path is invalid")
    void shouldAnswer400WhenTheGradeTypeInThePathIsInvalid() throws Exception {
        mockMvc.perform(put(STUDENT_GRADES + "/XYZ").with(teacher())
                        .contentType(MediaType.APPLICATION_JSON).content("{\"score\":4.5}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
        mockMvc.perform(delete(STUDENT_GRADES + "/XYZ").with(teacher()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should answer 422 when registering a grade without a type")
    void shouldAnswer422WhenRegisteringAGradeWithoutAType() throws Exception {
        mockMvc.perform(post("/api/v1/student-grades").with(teacher())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"groupId\":\"" + UUID.randomUUID() + "\",\"studentId\":\"" + UUID.randomUUID()
                                + "\",\"score\":4.5}"))
                .andExpect(status().isUnprocessableEntity());
    }
}
