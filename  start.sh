#!/bin/bash
echo "🚀 HoraClass Monorepo..."
docker compose -f docker-compose.yaml down --rmi all 2>/dev/null || true
docker compose -f docker-compose.yaml up --build -d
echo "✅ Frontend: localhost:3000"
echo "✅ Backend: localhost:8080"