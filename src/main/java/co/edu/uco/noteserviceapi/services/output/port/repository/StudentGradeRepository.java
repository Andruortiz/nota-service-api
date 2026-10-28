package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;

import java.util.List;
import java.util.UUID;

public interface StudentGradeRepository extends BaseRepository<UUID, StudentGradeEntity> {

    boolean existsByStudentGroupIdAndType(UUID studentGroupId, GradeType type);

    List<StudentGradeEntity> findByStudentGroupId(UUID studentGroupId);
}
