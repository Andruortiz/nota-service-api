package co.edu.uco.noteserviceapi.domain.registerstudentnote.entity;

import java.util.UUID;

public class StudentEntity {
    UUID id;
    String name;
    String document;
    boolean active;
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
