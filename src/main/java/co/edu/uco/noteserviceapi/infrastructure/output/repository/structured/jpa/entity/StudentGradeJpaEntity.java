package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "student_grade",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_student_grade_student_group_type",
                columnNames = {"student_group_id", "type"}))
public class StudentGradeJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private GradeType type;

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

    public GradeType getType() {
        return type;
    }

    public void setType(GradeType type) {
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
