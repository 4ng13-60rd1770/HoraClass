#!/bin/bash
echo "🛑 Parando HoraClass..."
docker compose down --rmi all --volumes --remove-orphans
docker system prune -f
echo "✅ Proyecto detenido y limpio"