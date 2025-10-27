# Variables
COMPOSE_FILE=docker-compose.yaml
BACKEND_IMAGE=papemomath/quoiregarder_backend:latest
FRONTEND_IMAGE=papemomath/quoiregarder_frontend:latest

# ============================
# Commandes Docker automatisées
# ============================

# Build les images Docker
build:
	docker compose -f $(COMPOSE_FILE) build

# Lancer les containers en arrière-plan
run:
	docker compose -f $(COMPOSE_FILE) up -d

# Arrêter les containers sans supprimer les images/volumes
stop:
	docker compose -f $(COMPOSE_FILE) down

# Supprimer containers, images et volumes
clean:
	docker compose -f $(COMPOSE_FILE) down --volumes --rmi all

# Redémarrer les containers (stop puis run)
restart: stop build run

# Ouvrir un shell dans le container backend
exec-backend:
	docker exec -it backend sh

# Ouvrir un shell dans le container frontend
exec-frontend:
	docker exec -it frontend sh

# Lancer les tests unitaires du backend
test:
	docker exec -it backend ./mvnw test