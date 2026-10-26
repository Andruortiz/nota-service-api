package co.edu.uco.noteserviceapi.services.output.port.repository;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;

import java.util.UUID;

public interface StudentGradeRepository extends BaseRepository<UUID, StudentGradeEntity> {

    boolean existsByGradeIdAndStudentGroupId(UUID gradeId, UUID studentGroupId);
}
