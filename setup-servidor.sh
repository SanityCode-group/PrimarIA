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

# 2. Instalar docker compose plugin si falta
if ! docker compose version &> /dev/null; then
    apt update
    apt install docker-compose-plugin -y
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
    echo ".env creado."
fi

# 5. Conectar el contenedor mariadb existente a la red bridge por defecto
echo "Verificando contenedor mariadb..."
docker inspect mariadb > /dev/null 2>&1 && echo "✅ Contenedor mariadb encontrado" || echo "⚠️  Contenedor mariadb no encontrado"

# 6. Primer build y arranque
echo "Construyendo y arrancando contenedores..."
docker compose up --build -d

echo ""
echo "=== ✅ Setup completado ==="
echo "Frontend accesible en: http://$(hostname -I | awk '{print $1}')"
echo "Logs: docker compose logs -f"