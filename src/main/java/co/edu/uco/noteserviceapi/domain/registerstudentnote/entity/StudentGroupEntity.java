package co.edu.uco.noteserviceapi.domain.registerstudentnote.entity;

import java.util.UUID;


public class StudentGroupEntity {
    UUID id;
    StudentEntity student;
    GroupEntity group;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }
}
