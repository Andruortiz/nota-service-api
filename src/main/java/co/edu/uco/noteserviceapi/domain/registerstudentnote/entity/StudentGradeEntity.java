package co.edu.uco.noteserviceapi.domain.registerstudentnote.entity;

import java.util.UUID;

public class StudentGradeEntity{

    private UUID id;
    private GradeEntity grade;
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

    public GradeEntity getGrade() {
        return grade;
    }

    public void setGrade(GradeEntity grade) {
        this.grade = grade;
    }
}
