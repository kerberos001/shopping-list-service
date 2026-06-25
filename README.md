# Shopping List Service

API REST para gestionar listas de compras y sus ítems. Permite crear listas, agregar productos, marcarlos como comprados y eliminarlos.

## Tecnologías

| Categoría | Tecnología |
|-----------|------------|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.2.2 |
| API REST | Spring Web |
| Persistencia | Spring Data JPA, Hibernate ORM |
| Base de datos | PostgreSQL |
| Utilidades | Lombok |
| Build | Maven (Maven Wrapper incluido) |
| Testing | Spring Boot Test, JUnit |
| Contenedores | Docker, Docker Compose |
| Runtime (Docker) | Eclipse Temurin 17 (JDK/JRE) |
| Dev tools | Spring Boot DevTools |

## Requisitos

- Java 17+
- Maven 3.9+ (o usar `./mvnw`)
- PostgreSQL (local, Supabase u otro proveedor)
- Docker y Docker Compose (opcional, para despliegue en contenedores)

## Configuración

La aplicación usa perfiles de Spring. Las variables de entorno principales son:

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DB_URL` | URL JDBC de PostgreSQL | `jdbc:postgresql://localhost:5432/shopping_db` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `PORT` | Puerto del servidor (perfil por defecto) | `8080` |
| `APP_CORS_ALLOWED_ORIGINS` | Orígenes permitidos para CORS | `http://localhost:5173` |

### Perfiles

| Perfil | Puerto | Context path | Uso |
|--------|--------|--------------|-----|
| *(default)* | 8080 | `/shopping` | Configuración genérica vía variables de entorno |
| `dev` | 9100 | `/shopping` | Desarrollo local (CORS: `http://localhost:5173`) |
| `docker` | 9100 | `/shopping` | Ejecución en contenedor (CORS: `http://localhost:9200`) |

Ejemplo para desarrollo local:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/shopping_db
export DB_USER=postgres
export DB_PASSWORD=tu_password
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

La API quedará disponible en `http://localhost:9100/shopping`.

## Ejecución con Docker

```bash
docker compose up --build
```

El servicio estará disponible en `http://localhost:9100`.

Para más detalles sobre la imagen Docker, consulta [README.Docker.md](README.Docker.md).

## API

Base URL: `http://localhost:{port}/shopping/api/v1`

### Listas de compras

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/shopping-lists` | Obtener todas las listas |
| `POST` | `/shopping-lists` | Crear una lista |
| `DELETE` | `/shopping-lists/{id}` | Eliminar una lista |

**Ejemplo — crear lista:**

```json
POST /shopping/api/v1/shopping-lists
{
  "name": "Supermercado"
}
```

### Ítems

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/items/{listId}` | Obtener ítems de una lista |
| `POST` | `/items/{listId}` | Agregar ítem a una lista |
| `PUT` | `/items/{listId}/{itemId}/status` | Alternar estado comprado/no comprado |
| `DELETE` | `/items/{listId}/{itemId}` | Eliminar un ítem |

**Ejemplo — agregar ítem:**

```json
POST /shopping/api/v1/items/1
{
  "name": "Leche",
  "quantity": 2
}
```

## Estructura del proyecto

```
src/main/java/com/shopping/shoppinglistservice/
├── ShoppingListServiceApplication.java
└── api/v1/
    ├── config/          # Configuración (CORS)
    ├── controllers/     # Controladores REST
    ├── dtos/            # Objetos de transferencia
    ├── models/          # Entidades JPA
    ├── repositories/    # Repositorios Spring Data
    └── services/        # Lógica de negocio
```

## Tests

```bash
./mvnw test
```

## Licencia

Proyecto privado — uso interno.
