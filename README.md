# Sistema de Registro de Personal e Ingresos — Colegio (Backend)

API REST para gestionar el registro de personas de un colegio (trabajadores, alumnas, padres de familia y visitantes externos), sus ingresos y salidas, con autenticación JWT y generación de reportes.

## Stack

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.1 |
| Spring Security + JWT (jjwt) | 0.12.3 |
| Spring Data JPA / Hibernate | — |
| MySQL | 8 |
| JasperReports | 6.20.1 |
| Apache POI | 5.2.3 |
| Maven | wrapper incluido |

## Arquitectura

- **Capas**: `Controller` → `Service` → `Repository`, con DTOs para el transporte de datos.
- **Modelo de datos con herencia JOINED**: entidad base `Persona` con subentidades `Trabajador`, `Alumna` y `PadreApoderado`. Las personas externas (visitas) son registros de `Persona` sin fila en ninguna subtabla.
- **Ingresos unificados**: una sola tabla `ingreso_personal` con FK a `Persona`; los reportes por tipo se filtran con consultas `EXISTS`.
- **Relación N:M** entre padres y alumnas mediante la tabla intermedia `padre_alumna`.
- **Seguridad**: JWT con access token (1 hora) y refresh token (7 días), contraseñas con BCrypt.

Más detalle de las decisiones de diseño en [`docs/DECISIONES.md`](docs/DECISIONES.md).

## Requisitos

- JDK 17 o superior
- MySQL 8
- Maven (no hace falta instalarlo: se usa el wrapper `mvnw`)

## Cómo correrlo

1. Crear la base de datos:
   ```sql
   CREATE DATABASE registroC;
   ```
2. Configurar las credenciales de MySQL en `src/main/resources/application.properties`.
3. Levantar la aplicación:
   ```bash
   # Windows
   .\mvnw.cmd spring-boot:run
   # Linux / Mac
   ./mvnw spring-boot:run
   ```
   Al arrancar, Hibernate crea/actualiza las tablas (`ddl-auto=update`).
4. (Opcional) Cargar datos de prueba:
   ```bash
   mysql -u root -p registroC < src/main/resources/seed_data.sql
   ```
5. La API queda escuchando en `http://localhost:8080`.

### Usuarios demo

Tras cargar el seed:

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `admin123` | ADMIN |
| `jperez` | `trabajador123` | USER |

## Endpoints principales

**Autenticación** — `/api/usuario`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/login` | Login; devuelve access + refresh token |
| POST | `/refresh` | Renueva los tokens |
| POST | `/save` | Crear usuario |
| GET | `/listarUsuarios` | Listar usuarios |

**Personas** — `/ControladorPersona`

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/listarTrabajadores` · `/listarAlumnas` · `/listarPadres` · `/listarExternos` | Listados paginados con filtros |
| POST | `/savePersona` | Crear (dispatch por campo `tipo`) |
| PUT | `/updatePersona` | Actualizar |
| GET | `/editarPersona/{id}` | Obtener una persona |
| DELETE | `/eliminarPersona/{id}` | Eliminar |
| GET | `/buscarPorDni/{dni}` | Buscar por DNI |

**Ingresos** — `/ControladorIngreso`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/listarIngreso?fecha=&idPersona=&tipoPersona=` | Listar filtrando por tipo |
| POST / PUT / DELETE | `/saveIngreso` · `/updateIngreso` · `/eliminarIngreso/{id}` | CRUD |
| GET | `/export-pdf/{id}` · `/export-excel/{id}` | Reportes de horas |
| GET | `/consultarHorasDetalle` | Detalle de horas por persona |

**Padre–Alumna** — `/ControladorPadreAlumna`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/listarPorPadre/{id}` · `/listarPorAlumna/{id}` | Relaciones por lado |
| POST | `/asignarAlumna` | Vincular |
| DELETE | `/removerAlumna/{idPadre}/{idAlumna}` | Desvincular |

**Salidas** — `/ControladorSA` · **Encargos** — `/ControladorEncargo` (CRUD análogo).

## Documentación del proyecto

- [`docs/DECISIONES.md`](docs/DECISIONES.md) — decisiones de diseño y sus tradeoffs.
- [`docs/Diagrama_BD.png`](docs/Diagrama_BD.png) — diagrama de la base de datos.

## Frontend

Repositorio: [Registro-Colegio-Angular-Azure](https://github.com/LuisT2203/Registro-Colegio-Angular-Azure)

## Autor

**Luis Terán** — Desarrollador Backend Java · Spring Boot + Angular
[GitHub](https://github.com/LuisT2203) · [LinkedIn](https://www.linkedin.com/in/luis-teran-dev/)
