# Sistema de gestión de tickets de soporte (proyecto1D)

Spring Boot 4.1.1 · Java 21 · Maven · Spring Data JPA · Validation · MySQL (H2 opcional) · Lombok · springdoc (Swagger).

## Estructura

```
proyecto1D
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/mx/edu/utez/proyecto1D
    │   │   ├── Proyecto1DApplication.java
    │   │   ├── config/        DataLoader.java              (3 usuarios de prueba)
    │   │   ├── controller/    TicketController, UsuarioController
    │   │   ├── service/       TicketService, UsuarioService
    │   │   ├── repository/    TicketRepository, UsuarioRepository
    │   │   ├── model/         Usuario, Ticket, EstadoTicket, Prioridad
    │   │   ├── dto/           CrearTicketRequest, ActualizarPrioridadRequest,
    │   │   │                  TicketResponse, UsuarioResponse, ErrorResponse
    │   │   └── exception/     4 excepciones de negocio, CustomBadRequestException,
    │   │                      GlobalExceptionHandler
    │   └── resources
    │       ├── application.properties       (MySQL)
    │       └── application-h2.properties    (H2 en memoria)
    └── test/java/.../Proyecto1DApplicationTests.java
```

## Ejecutar

**Con MySQL** (la base `tickets_db` se crea sola):

```bash
# Linux/Mac
export DB_USER=root DB_PASSWORD=tu_password
./mvnw spring-boot:run

# Windows (PowerShell)
$env:DB_USER="root"; $env:DB_PASSWORD="tu_password"
.\mvnw.cmd spring-boot:run
```

(O edita `spring.datasource.username/password` en `application.properties`.)

**Con H2 en memoria** (sin instalar MySQL):

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

Los 3 usuarios (cada uno con su Persona) se crean al arrancar: `1 ana.garcia (activo)`, `2 luis.hernandez (activo)`, `3 carlos.ramirez (inactivo)`.

> **Si ya tenías la BD MySQL creada con la versión anterior**, bórrala (`DROP DATABASE tickets_db;`) antes de arrancar: cambió el esquema (`nombre` -> `username`, nueva tabla `personas`).
Swagger UI: http://localhost:8080/swagger-ui.html

## Pruebas con curl

```bash
# Listar usuarios
curl -i http://localhost:8080/api/usuarios

# 1. Crear ticket (201)
curl -i -X POST http://localhost:8080/api/tickets -H "Content-Type: application/json" \
  -d '{"usuarioId":1,"descripcion":"No puedo iniciar sesión","prioridad":"ALTA"}'

# 2. Avanzar estado: ABIERTO -> EN_PROCESO (200)
curl -i -X PATCH http://localhost:8080/api/tickets/1/estado
# Repetir: EN_PROCESO -> CERRADO (200)
curl -i -X PATCH http://localhost:8080/api/tickets/1/estado

# 3. Reasignar prioridad (200)  -- hazlo ANTES de cerrar el ticket
curl -i -X PATCH http://localhost:8080/api/tickets/1/prioridad -H "Content-Type: application/json" \
  -d '{"prioridad":"BAJA"}'

# 5. Tickets de un usuario (200)
curl -i http://localhost:8080/api/usuarios/1/tickets

# 4. Desactivar usuario (200)
curl -i -X PATCH http://localhost:8080/api/usuarios/2/desactivar
```

### Casos de error

```bash
# Usuario inactivo -> 400
curl -i -X POST http://localhost:8080/api/tickets -H "Content-Type: application/json" \
  -d '{"usuarioId":3,"descripcion":"Prueba","prioridad":"MEDIA"}'

# Usuario inexistente -> 404
curl -i -X POST http://localhost:8080/api/tickets -H "Content-Type: application/json" \
  -d '{"usuarioId":999,"descripcion":"Prueba","prioridad":"MEDIA"}'
curl -i http://localhost:8080/api/usuarios/999/tickets
curl -i -X PATCH http://localhost:8080/api/usuarios/999/desactivar

# Descripción vacía y prioridad faltante -> 400 con mapa campo -> mensaje
curl -i -X POST http://localhost:8080/api/tickets -H "Content-Type: application/json" \
  -d '{"usuarioId":1,"descripcion":"   "}'

# Enum inválido -> 400
curl -i -X POST http://localhost:8080/api/tickets -H "Content-Type: application/json" \
  -d '{"usuarioId":1,"descripcion":"Prueba","prioridad":"URGENTE"}'

# JSON mal formado -> 400
curl -i -X POST http://localhost:8080/api/tickets -H "Content-Type: application/json" -d '{"usuarioId":'

# Ticket inexistente -> 404
curl -i -X PATCH http://localhost:8080/api/tickets/999/estado

# Ticket cerrado -> 409 (tras cerrar el ticket 1 con dos PATCH a /estado)
curl -i -X PATCH http://localhost:8080/api/tickets/1/estado
curl -i -X PATCH http://localhost:8080/api/tickets/1/prioridad -H "Content-Type: application/json" \
  -d '{"prioridad":"ALTA"}'
```

Ejemplo de respuesta de error de validación:

```json
{
  "timestamp": "2026-10-20T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "mensaje": "Error de validación en la petición",
  "path": "/api/tickets",
  "errores": {
    "descripcion": "La descripción no puede estar vacía",
    "prioridad": "La prioridad es obligatoria"
  }
}
```


## Recurso Usuario / Persona

```bash
# Consultar usuario por id (incluye su persona) -> 200 | 404
curl -i http://localhost:8080/api/usuarios/1

# Activar / desactivar -> 200 | 404
curl -i -X PATCH http://localhost:8080/api/usuarios/3/activar
curl -i -X PATCH http://localhost:8080/api/usuarios/3/desactivar

# Actualizar username y descripcion (conserva persona y estado) -> 200 | 400 | 404 | 409 (username repetido)
curl -i -X PUT http://localhost:8080/api/usuarios/1 -H "Content-Type: application/json" \
  -d '{"username":"ana.g","descripcion":"Soporte nivel 2"}'

# Consultar persona -> 200 | 404
curl -i http://localhost:8080/api/personas/1

# Actualizar persona -> 200 | 400 | 404 | 409 (correo repetido)
curl -i -X PUT http://localhost:8080/api/personas/1 -H "Content-Type: application/json" \
  -d '{"nombre":"Ana","apellidos":"García Pérez","correo":"ana@utez.edu.mx","telefono":"7771112233","fechaNacimiento":"2000-05-14"}'
```
