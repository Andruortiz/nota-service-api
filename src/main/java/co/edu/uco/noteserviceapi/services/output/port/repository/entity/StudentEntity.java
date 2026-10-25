package co.edu.uco.noteserviceapi.services.output.port.repository.entity;

import java.util.UUID;

public class StudentEntity {
    UUID id;
    String name;
    String document;
    boolean active;
    public boolean isActive() {
        return active;
    }
}
