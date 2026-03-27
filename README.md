# Ejecutar todo el proyecto
./start.sh    
# Parar proyecto 
./stop.sh    
# Conectar BD
docker compose -f docker-compose.yaml exec db psql -U postgres -d horaclass


# Solo frontend
docker compose -f docker-compose.yaml up frontend --build -d

# Solo backend
docker compose -f docker-compose.yaml up backend --build -d

# Reiniciar frontend
docker compose -f docker-compose.yaml restart frontend

# Logs frontend
docker compose -f docker-compose.yaml logs -f frontend

# Entrar contenedor
docker compose -f docker-compose.yaml exec frontend sh

# Test BD
docker compose -f docker-compose.yaml exec db psql -U postgres -d horaclass -c "\dt"


