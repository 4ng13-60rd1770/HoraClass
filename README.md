# 🚀 HoraClass - Monorepo Dockerizado


🎉 ¡Listo en 1 comando!
```bash

git clone https://github.com/4ng13-60rd1770/HoraClass
cd HoraClass
chmod +x *.sh
./start.sh
```

## 🎬 **Comandos Rápidos**

### 🚀 **Iniciar (1 comando)**
```bash
./start.sh
# o
docker compose up --build -d
```

**Detener:**
```bash
./stop.sh
# o
docker compose down
```
### URL´s
```bash

🌐 Frontend: http://localhost:3000
🔧 Backend: http://localhost:8080
```

### 📋 Status & Logs
```bash

./status.sh          # Status
docker compose logs -f  # Logs live
```

### 🧪 Comandos Docker Compose V2
```bash

docker compose up --build -d     # 🚀 Start
docker compose ps                # 📊 Status
docker compose logs -f           # 📋 Logs
docker compose down              # 🛑 Stop
docker compose down --rmi all    # 💥 Clean
```


### 👨‍💻 Desarrollo local
```bash

cd frontend
npm install
npm run dev      # http://localhost:5173 (Vite dev)
```

### 📈 Status esperado
```bash

NAME                  STATUS
horaclass-frontend    Up (healthy)
horaclass-backend     Up (healthy)
```
