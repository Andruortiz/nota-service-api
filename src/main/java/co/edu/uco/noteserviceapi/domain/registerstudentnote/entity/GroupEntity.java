package co.edu.uco.noteserviceapi.domain.registerstudentnote.entity;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
