package co.edu.uco.noteserviceapi.infrastructure.output.repository.structured.jpa.entity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "class_group")
public class GroupJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private TeacherJpaEntity teacher;

    @Column(name = "active", nullable = false)
    private boolean active;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeacherJpaEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherJpaEntity teacher) {
        this.teacher = teacher;
    }
}