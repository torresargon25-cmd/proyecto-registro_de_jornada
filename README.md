# Registro de jornada

API REST con Spring Boot para gestionar departamentos, empleados y fichajes de entrada y salida.

## Tecnologías

Java 21, Spring Boot 4.1.1, Spring Data JPA, H2, Lombok, Jakarta Validation, springdoc-openapi (Swagger) y Maven.

## Ejecución

1. Clonar el repositorio y abrirlo en IntelliJ IDEA.
2. Ejecutar la clase `RegistrodejornadaApplication`.
3. La API queda en `http://localhost:8080`.

La base de datos H2 se crea en cada arranque, por lo que empieza vacía. Los datos de prueba se cargan con la colección de Postman.

## Documentación

Swagger UI: `http://localhost:8080/swagger-ui.html`

## Endpoints

**Departamentos** (`/api/v1/departamentos`)

- `GET /`: listar departamentos
- `GET /{id}`: detalle con sus empleados
- `POST /`: crear
- `PUT /{id}`: editar
- `DELETE /{id}`: borrar

**Empleados** (`/api/v1/empleados`)

- `GET /`: listar empleados
- `GET /{id}`: detalle
- `POST /`: crear
- `PUT /{id}`: editar
- `DELETE /{id}`: borrar
- `PUT /{empleadoId}/departamento/{deptoId}`: asignar a un departamento

**Fichajes** (`/api/v1/empleados/{empleadoId}`)

- `POST /fichar`: registrar entrada o salida
- `GET /fichajes`: historial del empleado

## Reglas de negocio

- Al fichar sin body, se alterna entre ENTRADA y SALIDA. El primer fichaje es siempre ENTRADA.
- Si se envía `{"tipo": "ENTRADA"}` y no es el tipo que toca, la API responde 409.
- La suma de salarios de un departamento no puede superar su presupuesto (error 400).
- El departamento de un empleado es opcional.

## Errores

Los errores usan `ProblemDetail` (RFC 7807). Los de validación (400) incluyen la lista `invalid-params`. Los mensajes están en español e inglés (`messages/errors.properties` y `errors_en.properties`); para el inglés se envía la cabecera `Accept-Language: en`.

Códigos usados: 400 (datos no válidos o presupuesto excedido), 404 (no existe) y 409 (fichaje duplicado).

## Pruebas

- Postman: `registro-jornada.postman_collection.json`
- IntelliJ: `test.http`

## Limitaciones conocidas

- Dos departamentos con el mismo nombre dan error 500.
- No se puede borrar un departamento con empleados ni un empleado con fichajes.
- Al editar un empleado, la comprobación del presupuesto cuenta también su salario anterior.
- Los mensajes de las excepciones 404, 409 y presupuesto excedido solo están en español.
