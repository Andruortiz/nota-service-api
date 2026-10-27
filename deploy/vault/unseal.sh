#!/usr/bin/env bash
# Desbloquea Vault despues de un reinicio del servidor. Pide dos de las tres llaves.
set -euo pipefail
cd "$(dirname "$0")/.."

for n in 1 2; do
    read -r -s -p "Llave de desbloqueo $n: " key
    echo
    docker compose -f docker-compose.prod.yml exec -T -e VAULT_ADDR=http://127.0.0.1:8200 vault vault operator unseal "$key" > /dev/null
done
echo "Vault desbloqueado."
