package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity;

import jakarta.persistence.*;


import java.util.UUID;

@Entity
@Table(name = "student_grade",
        uniqueConstraints = @UniqueConstraint(columnNames = {"grade_id", "student_group_id"}))
public class StudentGradeJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "grade_id", nullable = false)
    private GradeJpaEntity grade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_group_id", nullable = false)
    private StudentGroupJpaEntity studentGroup;

    @Column(name = "value", nullable = false)   // 0 a 500 (centésimas)
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public StudentGroupJpaEntity getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroupJpaEntity studentGroup) {
        this.studentGroup = studentGroup;
    }

    public GradeJpaEntity getGrade() {
        return grade;
    }

    public void setGrade(GradeJpaEntity grade) {
        this.grade = grade;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}