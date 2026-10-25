package co.edu.uco.noteserviceapi.services.output.port.repository;

import java.util.List;
import java.util.Optional;

public interface ReadRepository <ID, E> {

    Optional<E> findById(ID id);

    List<E> findAll();

}