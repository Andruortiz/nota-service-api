INSERT INTO teacher (id, name, document, active)
VALUES ('019a0000-0000-7000-8000-000000000001', 'Docente Dev', '1000000001', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO student (id, name, document, active)
VALUES ('019a0000-0000-7000-8000-000000000003', 'Estudiante Uno', '2000000001', true),
       ('019a0000-0000-7000-8000-000000000004', 'Estudiante Dos', '2000000002', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO class_group (id, name, teacher_id, active)
VALUES ('019a0000-0000-7000-8000-000000000002', 'Grupo A', '019a0000-0000-7000-8000-000000000001', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO registration_period (type, registration_date_from, registration_date_to)
VALUES ('FOLLOW_UP', now() - interval '1 day', now() + interval '365 days'),
       ('MIDTERM', now() - interval '1 day', now() + interval '365 days')
ON CONFLICT (type) DO NOTHING;

INSERT INTO student_group (id, student_id, group_id, active)
VALUES ('019a0000-0000-7000-8000-000000000006', '019a0000-0000-7000-8000-000000000003', '019a0000-0000-7000-8000-000000000002', true),
       ('019a0000-0000-7000-8000-000000000007', '019a0000-0000-7000-8000-000000000004', '019a0000-0000-7000-8000-000000000002', true)
ON CONFLICT (id) DO NOTHING;
