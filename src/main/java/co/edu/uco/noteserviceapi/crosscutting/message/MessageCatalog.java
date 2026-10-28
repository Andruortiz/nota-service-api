package co.edu.uco.noteserviceapi.crosscutting.message;

public enum MessageCatalog {

    TEACHER_NOT_FOUND(
            "El docente no existe.",
            "Teacher was not found."
    ),

    TEACHER_INACTIVE(
            "El docente se encuentra inactivo.",
            "Teacher is inactive."
    ),

    STUDENT_NOT_FOUND(
            "El estudiante no existe.",
            "Student was not found."
    ),

    STUDENT_INACTIVE(
            "El estudiante se encuentra inactivo.",
            "Student is inactive."
    ),

    GROUP_NOT_FOUND(
            "El grupo no existe.",
            "Group was not found."
    ),

    GROUP_INACTIVE(
            "El grupo se encuentra inactivo.",
            "Group is inactive."
    ),

    GROUP_TEACHER_NOT_ASSIGNED(
            "El docente no está asignado al grupo.",
            "Teacher is not assigned to the group."
    ),

    GRADE_NOT_FOUND(
            "La actividad de evaluación no existe.",
            "Grade activity was not found."
    ),

    GRADE_OUTSIDE_REGISTRATION_PERIOD(
            "El plazo para registrar este tipo de nota no se encuentra abierto.",
            "Current timestamp is outside registration period."
    ),

    REGISTRATION_PERIOD_NOT_FOUND(
            "No hay un plazo de registro definido para este tipo de nota.",
            "Registration period was not found for the grade type."
    ),

    STUDENT_GROUP_NOT_FOUND(
            "El estudiante no se encuentra inscrito en el grupo.",
            "Student is not enrolled in the group."
    ),

    STUDENT_GROUP_INACTIVE(
            "El estudiante se encuentra inactivo en el grupo.",
            "Student is inactive in the group."
    ),

    STUDENT_GRADE_ALREADY_REGISTERED(
            "La calificación ya fue registrada para el estudiante.",
            "Student already has a grade registered for this activity."
    ),

    STUDENT_GRADE_NOT_FOUND(
            "El estudiante no tiene una calificación de este tipo.",
            "Student grade was not found for the grade type."
    ),

    GRADE_VALUE_OUT_OF_RANGE(
            "La calificación debe estar entre 0.0 y 5.0.",
            "Grade value is outside allowed range."
    ),

    GRADE_INVALID_DECIMALS(
            "La calificación admite un solo decimal.",
            "Grade contains more decimal places than allowed."
    ),

    INVALID_REQUEST(
            "La solicitud no es válida.",
            "Request validation failed."
    ),

    ACCESS_DENIED(
            "No tiene permisos para realizar esta operación.",
            "Access denied."
    ),

    UNEXPECTED_ERROR(
            "Se presentó un error inesperado.",
            "Unhandled exception occurred."
    );

    private final String userMessage;
    private final String technicalMessage;

    MessageCatalog(
            final String userMessage,
            final String technicalMessage) {

        this.userMessage = userMessage;
        this.technicalMessage = technicalMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getTechnicalMessage() {
        return technicalMessage;
    }

}
