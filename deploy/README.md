# Despliegue

Todo corre en una VM con Docker Compose, en `/opt/notas`. Caddy es el unico servicio expuesto (80 y 443).

## Secretos y variables de GitHub

En los repositorios `nota-service-api` y `front-note-api`, entorno `production`:

| Tipo | Nombre | Valor |
|---|---|---|
| Secreto | `SSH_HOST` | IP o nombre de la VM |
| Secreto | `SSH_USER` | Usuario con acceso a Docker |
| Secreto | `SSH_KEY` | Clave privada SSH dedicada al despliegue |
| Secreto | `SSH_KNOWN_HOSTS` | Salida de `ssh-keyscan -t ed25519 <host>`, verificada |
| Variable | `PUBLIC_HOST` | Nombre publico, por ejemplo `notas.duckdns.org` |

## Preparar la VM (una vez)

1. Instalar Docker y el plugin de Compose; instalar `rsync`; crear 2 GB de swap.
2. `sudo mkdir -p /opt/notas && sudo chown <usuario> /opt/notas`.
3. Agregar la clave publica de despliegue a `~/.ssh/authorized_keys`.
4. Abrir en el firewall solo 22, 80 y 443; el nombre publico debe apuntar a la IP de la VM.
5. Iniciar sesion en el registro de imagenes (si el paquete de GHCR es privado):
   `docker login ghcr.io -u <usuario-github>` con un token de solo lectura de paquetes.
6. Crear `/opt/notas/.env` con permisos `600`, a partir de `.env.prod.example`.

## Primer despliegue

El CD no puede hacer el primer arranque, porque Vault necesita inicializarse a mano antes de que la API tenga credenciales.

1. Copiar `deploy/` a la VM: `rsync -az --exclude '.env' deploy/ <usuario>@<host>:/opt/notas/`.
2. En la VM, desde `/opt/notas`, con `VAULT_ROLE_ID` y `VAULT_SECRET_ID` temporalmente en `placeholder` en el `.env`: levantar `postgres` y `vault`, y ejecutar `./vault/init.sh`.
3. Reemplazar en el `.env` los dos valores por los que imprime el script. Mover `vault-init.txt` fuera de la VM y borrarlo.
4. Publicar una etiqueta `vX.Y.Z` en `master` del backend: el CD actualiza la API y levanta el resto.
5. En Keycloak (`https://<host>/auth`, usuario administrador del `.env`), crear los usuarios con su rol y el atributo `teacherId`.
6. Cargar los datos iniciales en PostgreSQL con `scripts/seed-dev.sql` adaptado.
7. Publicar una etiqueta `vX.Y.Z` en `master` del frontend.

## Despues de un reinicio de la VM

Vault arranca sellado y la API no inicia hasta desbloquearlo: `./vault/unseal.sh` (pide dos de las tres llaves) y luego `docker compose -f docker-compose.prod.yml restart api`.

## Cambios de configuracion

Editar `/opt/notas/.env` y ejecutar `docker compose -f docker-compose.prod.yml up -d`. El realm de Keycloak solo se importa la primera vez; los cambios posteriores se hacen en su consola.
