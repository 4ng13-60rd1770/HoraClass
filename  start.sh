echo "🚀 Iniciando HoraClass Monorepo..."
cd "$(dirname "$0")" || exit
docker compose down --rmi all --volumes 2>/dev/null || true
docker compose up --build -d
echo "✅ Proyecto levantado!"
echo "🌐 Frontend: http://localhost:3000"
echo "🔧 Backend: http://localhost:8080"
echo "📊 Status: docker compose ps"
echo "📋 Logs: docker compose logs -f"
echo "🛑 Parar: docker compose down"