package co.edu.uco.noteserviceapi.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.edu.uco.noteserviceapi.domain.registerstudentnote.GradeType;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.GroupDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.RegistrationPeriodDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentGradeDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.StudentGroupDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.TeacherDomain;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.GroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.RegistrationPeriodEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGradeHistoryEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.StudentGroupEntity;
import co.edu.uco.noteserviceapi.domain.registerstudentnote.entity.TeacherEntity;
import co.edu.uco.noteserviceapi.services.output.port.repository.GroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.RegistrationPeriodRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeHistoryRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGradeRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentGroupRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.StudentRepository;
import co.edu.uco.noteserviceapi.services.output.port.repository.TeacherRepository;
import co.edu.uco.noteserviceapi.services.usecase.StudentGradeContextResolver;
import co.edu.uco.noteserviceapi.services.usecase.StudentGradeHistoryRecorder;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;
import org.mockito.ArgumentCaptor;

final class StudentGradeFixture {

    static final UUID TEACHER_ID = UUID.fromString("019a0000-0000-7000-8000-000000000001");
    static final UUID GROUP_ID = UUID.fromString("019a0000-0000-7000-8000-000000000002");
    static final UUID STUDENT_ID = UUID.fromString("019a0000-0000-7000-8000-000000000003");
    static final UUID STUDENT_GROUP_ID = UUID.fromString("019a0000-0000-7000-8000-000000000006");
    static final UUID ANOTHER_TEACHER_ID = UUID.fromString("019a0000-0000-7000-8000-000000000009");
    static final Instant NOW = Instant.parse("2026-11-15T12:00:00Z");

    final TeacherRepository teacherRepository = mock(TeacherRepository.class);
    final GroupRepository groupRepository = mock(GroupRepository.class);
    final StudentRepository studentRepository = mock(StudentRepository.class);
    final StudentGroupRepository studentGroupRepository = mock(StudentGroupRepository.class);
    final RegistrationPeriodRepository registrationPeriodRepository = mock(RegistrationPeriodRepository.class);
    final StudentGradeRepository studentGradeRepository = mock(StudentGradeRepository.class);
    final StudentGradeHistoryRepository historyRepository = mock(StudentGradeHistoryRepository.class);

    final Clock clock = Clock.fixed(NOW, ZoneOffset.UTC);
    final StudentGradeDomain studentGradeDomain = new StudentGradeDomain();
    final StudentGradeContextResolver contextResolver;
    final StudentGradeHistoryRecorder historyRecorder;

    final StudentGroupEntity studentGroup = new StudentGroupEntity();

    StudentGradeFixture() {
        final TeacherEntity teacher = activeTeacher(TEACHER_ID);
        final GroupEntity group = new GroupEntity();
        group.setId(GROUP_ID);
        group.setActive(true);
        group.setTeacher(teacher);

        final StudentEntity student = new StudentEntity();
        student.setId(STUDENT_ID);
        student.setActive(true);

        studentGroup.setId(STUDENT_GROUP_ID);
        studentGroup.setStudent(student);
        studentGroup.setGroup(group);
        studentGroup.setActive(true);

        when(teacherRepository.findById(TEACHER_ID)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findById(ANOTHER_TEACHER_ID)).thenReturn(Optional.of(activeTeacher(ANOTHER_TEACHER_ID)));
        when(groupRepository.findById(GROUP_ID)).thenReturn(Optional.of(group));
        when(studentRepository.findById(STUDENT_ID)).thenReturn(Optional.of(student));
        when(studentGroupRepository.findByStudentIdAndGroupId(STUDENT_ID, GROUP_ID))
                .thenReturn(Optional.of(studentGroup));
        openPeriod(GradeType.FOLLOW_UP);

        contextResolver = new StudentGradeContextResolver(
                teacherRepository, groupRepository, studentRepository,
                studentGroupRepository, registrationPeriodRepository,
                new TeacherDomain(), new GroupDomain(), new StudentDomain(),
                new StudentGroupDomain(), new RegistrationPeriodDomain(), clock);
        historyRecorder = new StudentGradeHistoryRecorder(historyRepository, clock);
    }

    void openPeriod(final GradeType type) {
        definePeriod(type, NOW.minusSeconds(3600), NOW.plusSeconds(3600));
    }

    void closedPeriod(final GradeType type) {
        definePeriod(type, NOW.minusSeconds(7200), NOW.minusSeconds(3600));
    }

    StudentGradeEntity existingGrade(final GradeType type, final int value) {
        final StudentGradeEntity grade = new StudentGradeEntity();
        grade.setId(UUID.randomUUID());
        grade.setType(type);
        grade.setStudentGroup(studentGroup);
        grade.setValue(value);
        when(studentGradeRepository.findByStudentGroupIdAndType(STUDENT_GROUP_ID, type))
                .thenReturn(Optional.of(grade));
        return grade;
    }

    StudentGradeHistoryEntity capturedHistory() {
        final ArgumentCaptor<StudentGradeHistoryEntity> captor =
                ArgumentCaptor.forClass(StudentGradeHistoryEntity.class);
        verify(historyRepository).create(captor.capture());
        return captor.getValue();
    }

    private TeacherEntity activeTeacher(final UUID id) {
        final TeacherEntity teacher = new TeacherEntity();
        teacher.setId(id);
        teacher.setActive(true);
        return teacher;
    }

    private void definePeriod(final GradeType type, final Instant from, final Instant to) {
        final RegistrationPeriodEntity period = new RegistrationPeriodEntity();
        period.setType(type);
        period.setRegistrationDateFrom(from);
        period.setRegistrationDateTo(to);
        when(registrationPeriodRepository.findById(type)).thenReturn(Optional.of(period));
    }
}
