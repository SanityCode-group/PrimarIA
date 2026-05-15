#!/bin/bash
# setup-servidor.sh
# Ejecutar UNA SOLA VEZ en el servidor para preparar todo
# Uso: bash setup-servidor.sh
set -e

echo "=== Setup inicial PrimarIA ==="

# 1. Instalar git si no está
if ! command -v git &> /dev/null; then
    apt update && apt install git -y
fi

# 2. Instalar docker compose v2 si falta
if ! docker compose version &> /dev/null; then
    echo "Instalando Docker Compose v2..."
    mkdir -p /usr/local/lib/docker/cli-plugins
    curl -SL https://github.com/docker/compose/releases/download/v2.27.0/docker-compose-linux-x86_64 \
        -o /usr/local/lib/docker/cli-plugins/docker-compose
    chmod +x /usr/local/lib/docker/cli-plugins/docker-compose
    echo "✅ Docker Compose $(docker compose version) instalado"
fi

# 3. Clonar el repositorio
mkdir -p /opt/primaria
cd /opt/primaria

if [ -d ".git" ]; then
    echo "Repositorio ya clonado, actualizando..."
    git pull origin main
else
    git clone https://github.com/SanityCode-group/PrimarIA.git .
fi

# 4. Crear el fichero .env con los secretos
if [ ! -f ".env" ]; then
    cat > .env << 'EOF'
DB_USERNAME=sanitycode
DB_PASSWORD=SanityCodePass123!
GOOGLE_CLIENTE_ID=TU_GOOGLE_CLIENTE_ID
GOOGLE_CLIENTE_SECRETO=TU_GOOGLE_CLIENTE_SECRETO
EOF
    echo ".env creado. ⚠️  Edita /opt/primaria/.env con los valores reales antes de continuar."
    echo "Ejecuta: nano /opt/primaria/.env"
    exit 0
fi

# 5. Verificar contenedor mariadb
echo "Verificando contenedor mariadb..."
docker inspect mariadb > /dev/null 2>&1 && echo "✅ Contenedor mariadb encontrado" || echo "⚠️  Contenedor mariadb no encontrado"

# 6. Primer build y arranque
echo "Construyendo y arrancando contenedores..."
docker compose up --build -d

echo ""
echo "=== ✅ Setup completado ==="
echo "Frontend accesible en: http://$(hostname -I | awk '{print $1}')"
echo "Logs: docker compose logs -f"