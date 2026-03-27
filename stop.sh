#!/bin/bash
docker compose -f docker-compose.yaml down --rmi all --volumes
echo "🛑 Detenido"