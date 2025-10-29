# Website Simple Backend

Backend simple pour la gestion de séries, utilisateurs et recommandations.  
Ce projet fournit des endpoints pour gérer les utilisateurs, séries, historiques, ratings et recommandations.

![Backend Overview](img.png)  
![Endpoints Overview](img_1.png)
---
### epic 4
![img_3.png](./images/img_3.png)
![img_2.png](./images/img_2.png)

---
## 🗂️ Structure du projet
 - ### models/ : Contient toutes les entités (User, Serie, Episode, Ratings)
 - #### repository/ : Interfaces pour accéder à la base de données
 - #### service/ : Logique métier (gestion utilisateurs, séries, recommandations, ratings)
 - #### controller/ : Endpoints REST pour interagir avec le frontend
 - #### exception/ : Gestion des erreurs personnalisées
 - #### security/ : JWT pour authentification

---

## 🔑 Authentification
Le backend utilise JWT pour sécuriser les endpoints.
Pour accéder aux endpoints protégés, envoyez l’Authorization header avec le token :

    Authorization: Bearer <votre_token>
---

## ⚡ Notes
 -  le backend utilisa jwt. pour les test nous avons les password(nom) de 2 users(les 2 première).

---


## 🚀 Installation et configuration

### Cloner le projet
```bash
git clone --branch <branche> https://github.com/Elverovski/QuoiRegarder_backend.git
cd QuoiRegarder_backend
```

### Pré-requis

- Java JDK 17
- Maven 
- Docker 
- Jenkins

  ---

## 🐳 Execution aved docker compose
- Build image
```bash
docker compose build
```

- Lancement du stack
```bash
docker compose up -d
```

- Vérifier les services
```bash
docker compose ps
```

- Logs en direct du backend
```bash
docker compose logs -f backend
```

- Arrêt et nettoyage
```bash
docker compose down -v
```

---

## ⚙️ Pipeline Jenkins
Le projet inclut un Jenkinsfile permettant de builder, tester et notifier par courriel automatiquement le team dev.

---

## 🧰 Commandes Makefile

- `make build` → construit l’image Docker  
- `make run` → démarre un container fonctionnel  
- `make exec` → ouvre un shell dans le container  
- `make stop` → stoppe et supprime le container  
- `make test` → exécute les tests unitaires  
- `make restart` → fait *stop*, *build* et *run* pour réinitialiser le container

---


## 🧱 Stack technique

| Composant | Version | Description |
|------------|----------|-------------|
| **Java (JDK)** | 17 | Langage principal utilisé pour le backend |
| **Spring Boot** | 3.5.5 | Framework backend Java pour les API REST |
| **Maven** | 3.9.8 | Gestionnaire de dépendances et de build |
| **H2 Database** | — | Base de donnes principale (en memoire) |
| **JWT (JJWT)** | 0.12.3 | Gestion des tokens d’authentification |
| **Spring Security** | 3.5.5 | Sécurisation des endpoints |
| **SpringDoc OpenAPI** | 2.5.0 | Génération automatique de la documentation Swagger |
| **OpenAPI Generator Plugin** | 7.8.0 | Generation de documentation HTML dans `/target/site/apidocs` |
| **JaCoCo** | 0.8.11 | Rapport de couverture de tests |
| **Docker** | 25.0+ | Conteneurisation des services |
| **Docker Compose** | 2.24+ | Orchestration des containers |
| **Jenkins** | 2.462+ | Intégration continue (CI/CD) |
| **Makefile** | — | Automatisation des commandes (build, test, run, etc.) |



