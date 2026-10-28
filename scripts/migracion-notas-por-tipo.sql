-- Migra una base creada con el modelo anterior (evaluacion por grupo) al modelo de tipos
-- de nota con plazo institucional. Las notas existentes quedan como FOLLOW_UP.
-- Ejecutar ANTES de desplegar la version nueva de la API.

BEGIN;

ALTER TABLE student_grade ADD COLUMN IF NOT EXISTS type varchar(20);
UPDATE student_grade SET type = 'FOLLOW_UP' WHERE type IS NULL;
ALTER TABLE student_grade ALTER COLUMN type SET NOT NULL;

ALTER TABLE student_grade DROP COLUMN IF EXISTS grade_id;
DROP TABLE IF EXISTS grade;

ALTER TABLE student_grade
    ADD CONSTRAINT uk_student_grade_student_group_type UNIQUE (student_group_id, type);

CREATE TABLE IF NOT EXISTS registration_period (
    type varchar(20) PRIMARY KEY,
    registration_date_from timestamp with time zone NOT NULL,
    registration_date_to timestamp with time zone NOT NULL
);

INSERT INTO registration_period (type, registration_date_from, registration_date_to)
VALUES ('FOLLOW_UP', now() - interval '1 day', now() + interval '365 days'),
       ('MIDTERM', now() - interval '1 day', now() + interval '365 days')
ON CONFLICT (type) DO NOTHING;

COMMIT;
