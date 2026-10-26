package co.edu.uco.noteserviceapi.services.output.port.repository;

public interface BaseRepository<ID, E>
        extends CreateRepository<E>,
        UpdateRepository<E>,
        DeleteRepository<ID>,
        ReadRepository<ID, E> {
}
