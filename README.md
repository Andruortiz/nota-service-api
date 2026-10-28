# note-service-api

Se requiere asignar una nota evaluada en un curso a un estudiante que está cursando ese curso. Esta API permite a un
docente consultar sus grupos y estudiantes, y registrar, corregir o eliminar notas dentro del periodo de registro.

Java 27 · Spring Boot 4.1 · PostgreSQL 17 · Keycloak 26 (login y roles) · Vault 1.18 (secretos) · Docker Compose.

## Arquitectura

Hexagonal, con las capas `domain`, `services` (puertos y casos de uso), `infrastructure` (controladores, seguridad y
persistencia JPA), `crosscutting` y `bootstrap`. Una prueba de ArchUnit (`HexagonalArchitectureTest`) impide que las
capas se salten las dependencias.

## API

Base: `/api/v1`. Todas las rutas exigen un JWT de Keycloak, y las de grupos y notas exigen además el rol `DOCENTE`
(ver `SecurityConfiguration`). El docente sale del claim `teacherId` del token y debe estar asignado al grupo.

| Método y ruta                                                  | Qué hace                                  |
|----------------------------------------------------------------|-------------------------------------------|
| `GET /groups`                                                  | Grupos activos del docente                |
| `GET /groups/{groupId}/students`                               | Estudiantes del grupo                     |
| `GET /groups/{groupId}/students/{studentId}/grades`            | Notas del estudiante                      |
| `PUT /groups/{groupId}/students/{studentId}/grades/{type}`     | Corrige la nota de ese tipo               |
| `DELETE /groups/{groupId}/students/{studentId}/grades/{type}`  | Elimina la nota de ese tipo               |
| `POST /student-grades`                                         | Registra una nota                         |
| `GET /registration-periods`                                    | Periodos de registro                      |

Cada respuesta incluye el encabezado `X-Correlation-Id`, que también aparece en los logs.

## Ejecutar en local

Requisitos: JDK 27 y Docker.

```bash
cp .env.example .env
docker compose up -d
BD_USUARIO=notes BD_CLAVE=notes ./mvnw spring-boot:run
```

`docker compose up` levanta PostgreSQL (5432), Keycloak (8081, realm `notes` importado de `keycloak/`) y Vault en modo
desarrollo (8200). Sin perfiles, la API usa `http://localhost:8081/realms/notes` como emisor de tokens y no usa Vault.

## Configuración

| Variable                       | Para qué                                              | Dónde se usa |
|--------------------------------|-------------------------------------------------------|--------------|
| `BD_URL`, `BD_USUARIO`, `BD_CLAVE` | Conexión a PostgreSQL                             | siempre      |
| `JWT_ISSUER_URI`               | Emisor esperado en el token                           | siempre      |
| `JWT_JWK_SET_URI`              | Claves públicas para validar el token                 | perfil `prod`|
| `APP_CORS_ALLOWED_ORIGINS`     | Origen permitido del frontend                         | producción   |
| `VAULT_URI`, `VAULT_AUTH`, `VAULT_ROLE_ID`, `VAULT_SECRET_ID` | Acceso a Vault | perfil `vault` |

En producción se activan los perfiles `vault,prod`: las credenciales de la base de datos se leen de Vault y los logs
salen en formato estructurado (logstash).

## Pruebas y calidad

```bash
./mvnw spotless:check   # formato
./mvnw clean verify     # JUnit 5, Mockito, Testcontainers, ArchUnit y cobertura JaCoCo
```

El CI (`.github/workflows/ci.yml`) corre en cada PR y push a `master`: formato, pruebas, detección de secretos
(gitleaks), escaneo con Trivy y análisis de SonarCloud.

## Despliegue

El CD (`.github/workflows/cd.yml`) se dispara al publicar una etiqueta SemVer `vX.Y.Z` sobre `master`:

1. Valida la etiqueta y que el commit pertenezca a `master`.
2. Ejecuta el CI.
3. Construye la imagen, la escanea con Trivy y la publica en GHCR.
4. Sube `deploy/` a la VM por `rsync`, actualiza la API con Docker Compose y recarga Caddy.
5. Prueba que `/api/v1/groups` responda 401 sin token.
6. Publica el release en GitHub.

Una etiqueta con sufijo (`v1.2.0-rc.1`) despliega al entorno `staging` y se marca como prerelease.

En producción Caddy es el único servicio expuesto (80 y 443): reenvía `/api/*` a la API y `/auth/*` a Keycloak, y sirve el
frontend. Los pasos de preparación de la VM, el primer despliegue, los secretos de GitHub y qué hacer tras un reinicio
(Vault arranca sellado) están en [deploy/README.md](deploy/README.md).
