# JWT Employees API

API REST desarrollada en **Spring Boot** para la gestión de empleados con autenticación mediante **JWT (JSON Web Token)**.

---
## Demo en Producción

### La API está desplegada en Render:

[ Ver API en vivo](https://jwt-employees-api.onrender.com)

## Documentación Swagger

[ Ver Swagger](https://jwt-employees-api.onrender.com/swagger-ui/index.html)


---
##  Autenticación

### Login

**POST** `/auth/login`

```json
{
  "username": "admin",
  "password": "admin123*"
}
```

### Respuesta
```json
{
  "status": 200,
  "message": "Autenticación exitosa",
  "data": {
    "token": "JWT_TOKEN"
  }
}
```

---
##  Endpoints de Empleados

### Crear empleado

**POST** `/employees`

### Obtener por ID

**GET** `/employees/id`

### Listar empleados

**GET** `/employees`

### Actualizar empleado

**PUT** `/employees/id`

### Eliminar empleado

**DELETE** `/employees/id`

----
## Características

- Autenticación con JWT
- CRUD de empleados
- Validaciones con Jakarta Validation
- Manejo global de excepciones
- Respuestas estandarizadas (`ApiResponse`)
- Documentación con Swagger (OpenAPI)
- Logs con SLF4J
- Seguridad con Spring Security

---

## Tecnologías

- Java 17+
- Spring Boot
- Spring Security
- JWT
- Maven
- Swagger / OpenAPI
- JPA / Hibernate

---

## Estructura del proyecto

src/main/java/com/prueba/jwt/employees

├── config          # Configuración (Security, JWT, OpenAPI)  
├── controller      # Controladores REST  
├── dto             # Objetos de transferencia (DTOs)  
├── exception       # Manejo de errores y excepciones  
├── model           # Entidades  
├── repository      # Acceso a datos  
├── response        # Wrapper de respuestas API  
├── service         # Lógica de negocio  
└── security        # Filtros y handlers de seguridad  

---

## Ejecución del proyecto

### 1. Clonar Repositorio

- git clone https://github.com/yil12/jwt-employees-api.git
- cd jwt-employees-api

### 2. Ejecutar

- ./mvnw spring-boot:run

## Swagger

- http://localhost:8080/swagger-ui.html

###  Nota

- Se incluye colección de Postman para pruebas de la API con autenticación JWT.

## Seguridad de Contraseñas

- En relación con el requerimiento de “descifrar contraseñas”, se decidió no implementar dicha funcionalidad, ya que las contraseñas están encriptadas mediante BCrypt (Spring Security) y no son reversibles por seguridad. En su lugar, se valida mediante comparación segura de hashes.