package co.edu.uco.noteserviceapi.services.output.port.repository.entity;

import java.util.UUID;

public class GroupEntity {
    UUID id;
    String name;
    TeacherEntity teacher;
    boolean active;

    public TeacherEntity getTeacher() {
        return teacher;
    }
    public boolean isActive() {
        return active;
    }
}
