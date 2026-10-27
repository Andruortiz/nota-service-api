#!/usr/bin/env bash
# Inicializa Vault una sola vez: llaves de desbloqueo, KV, politica de solo lectura y AppRole.
set -euo pipefail
cd "$(dirname "$0")/.."
set -a
. ./.env
set +a

COMPOSE="docker compose -f docker-compose.prod.yml"
ROOT_TOKEN=""

vault_cmd() {
    $COMPOSE exec -T -e VAULT_ADDR=http://127.0.0.1:8200 -e VAULT_TOKEN="$ROOT_TOKEN" vault vault "$@"
}

if vault_cmd status 2>/dev/null | grep -q 'Initialized *true'; then
    echo "Vault ya esta inicializado." >&2
    exit 1
fi

vault_cmd operator init -key-shares=3 -key-threshold=2 > vault-init.txt
chmod 600 vault-init.txt

for n in 1 2; do
    vault_cmd operator unseal "$(grep "Unseal Key $n:" vault-init.txt | awk '{print $NF}')" > /dev/null
done
ROOT_TOKEN="$(grep 'Initial Root Token' vault-init.txt | awk '{print $NF}')"

vault_cmd secrets enable -path=secret kv-v2
vault_cmd policy write note-service-api - <<'EOF'
path "secret/data/note-service-api*" {
  capabilities = ["read"]
}
EOF
vault_cmd auth enable approle
vault_cmd write auth/approle/role/note-service-api token_policies=note-service-api token_ttl=1h token_max_ttl=24h
vault_cmd kv put secret/note-service-api \
    spring.datasource.username="$BD_USUARIO" \
    spring.datasource.password="$BD_CLAVE"

ROLE_ID="$(vault_cmd read -field=role_id auth/approle/role/note-service-api/role-id)"
SECRET_ID="$(vault_cmd write -f -field=secret_id auth/approle/role/note-service-api/secret-id)"

echo
echo "Agregue estas lineas al .env y reinicie la API:"
echo "VAULT_ROLE_ID=$ROLE_ID"
echo "VAULT_SECRET_ID=$SECRET_ID"
echo
echo "Mueva vault-init.txt (llaves y token raiz) fuera del servidor y borrelo."
