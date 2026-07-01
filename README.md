# Quadrant — Task Manager API

A RESTful Task Management API built with **Spring Boot**, featuring
**Eisenhower Matrix** prioritization and automatic parent task
completion when all subtasks are done.

## Why Quadrant?

Most task managers use simple HIGH/MEDIUM/LOW priority.  
Quadrant uses the **Eisenhower Matrix** — two boolean fields
(`isUrgent`, `isImportant`) that derive a productivity quadrant
automatically, never stored in the DB.

| Quadrant | Urgent | Important | Action |
|----------|--------|-----------|--------|
| Q1 | ✅ | ✅ | Do Now |
| Q2 | ❌ | ✅ | Schedule |
| Q3 | ✅ | ❌ | Delegate |
| Q4 | ❌ | ❌ | Eliminate |

## Tech Stack

- **Java 21** + **Spring Boot 3.5**
- **Spring Data JPA** + **Hibernate**
- **H2** (dev) / **PostgreSQL** (prod)
- **Flyway** — schema versioning
- **Lombok** + **Bean Validation**

## API Endpoints

### Tasks
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/tasks` | Create task |
| GET | `/api/v1/tasks` | Get all tasks (filter by `date`, `quadrant`, `completed`) |
| GET | `/api/v1/tasks/{id}` | Get task by ID |
| PUT | `/api/v1/tasks/{id}` | Update task |
| PATCH | `/api/v1/tasks/{id}/complete` | Toggle completion |
| DELETE | `/api/v1/tasks/{id}` | Delete task |

### Subtasks
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/tasks/{taskId}/subtasks` | Add subtask |
| GET | `/api/v1/tasks/{taskId}/subtasks` | Get all subtasks |
| PUT | `/api/v1/tasks/{taskId}/subtasks/{id}` | Update subtask |
| PATCH | `/api/v1/tasks/{taskId}/subtasks/{id}/complete` | Toggle — triggers parent auto-complete |
| DELETE | `/api/v1/tasks/{taskId}/subtasks/{id}` | Delete subtask |

## Running Locally

```bash
git clone https://github.com/Sam5131/quadrant.git
cd quadrant
./mvnw spring-boot:run
```

API runs on `http://localhost:8080`  
H2 Console at `http://localhost:8080/h2-console`

## Example Request

```json
POST /api/v1/tasks
{
  "name": "Design system architecture",
  "isUrgent": true,
  "isImportant": true,
  "dueDate": "2026-07-01T10:00:00",
  "reminderEnabled": false
}
```

Response includes derived quadrant:
```json
{
  "quadrant": "Q1",
  ...
}
```