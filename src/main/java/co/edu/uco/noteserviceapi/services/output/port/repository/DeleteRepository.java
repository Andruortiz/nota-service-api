package co.edu.uco.noteserviceapi.services.output.port.repository;

public interface DeleteRepository<ID> {

    void delete(ID id);

}