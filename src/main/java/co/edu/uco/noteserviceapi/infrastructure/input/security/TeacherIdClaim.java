package co.edu.uco.noteserviceapi.infrastructure.input.security;

import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;

public final class TeacherIdClaim {

    private static final String CLAIM = "teacherId";

    private TeacherIdClaim() {
    }

    public static UUID from(final Jwt jwt) {
        final String value = jwt.getClaimAsString(CLAIM);
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException | NullPointerException invalidClaim) {
            throw new AccessDeniedException("Missing or invalid " + CLAIM + " claim");
        }
    }
}
