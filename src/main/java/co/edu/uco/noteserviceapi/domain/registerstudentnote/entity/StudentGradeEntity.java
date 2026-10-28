package co.edu.uco.noteserviceapi.domain.registerstudentnote.entity;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;

import java.util.UUID;

public class StudentGradeEntity{

    private UUID id;
    private GradeType type;
    private StudentGroupEntity studentGroup;
    private int value;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public StudentGroupEntity getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroupEntity studentGroup) {
        this.studentGroup = studentGroup;
    }

    public GradeType getType() {
        return type;
    }

    public void setType(GradeType type) {
        this.type = type;
    }
}
