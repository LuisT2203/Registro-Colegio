# Decisiones de diseño

Registro de las decisiones importantes del proyecto y **por qué** se tomaron. Sirve para entender el código actual y para explicarlo.

---

## 1. Herencia JOINED para las personas

**Contexto.** Necesitaba manejar trabajadores, alumnas y padres, que comparten datos (nombre, apellido, DNI) pero tienen campos propios (cargo; grado/nivel/sección).

**Opciones.**
- Tablas independientes por tipo → datos repetidos en cada una y sin forma de tratar a "una persona" como un solo concepto.
- Una sola tabla con todas las columnas → muchas columnas vacías (un trabajador no tiene grado).
- Una tabla base + una subtabla por tipo (**JOINED**).

**Decisión.** Herencia JOINED: `persona` guarda lo común; `trabajador`, `alumna` y `padre_apoderado` comparten el mismo `id_persona` y guardan lo propio. Las visitas externas son filas de `persona` sin subtabla.

**Tradeoff.** Un guardado requiere dos INSERT (en `persona` y en la subtabla), pero gano normalización, identidad única y búsquedas simples por DNI. Elijo el costo de escritura a cambio de integridad.

---

## 2. El campo `tipo` viaja en el DTO, no en la base

**Contexto.** Con un solo endpoint para guardar cualquier persona, el backend necesita saber qué subtipo crear.

**Opciones.**
- Deducir el tipo por los campos presentes (`si tiene cargo → Trabajador`) → ambiguo y frágil.
- Un endpoint por tipo → duplica controladores y servicios.
- Un campo `tipo` explícito que envía el frontend.

**Decisión.** El frontend envía `tipo` ("TRABAJADOR", "ALUMNA", "PADRE", "EXTERNO") dentro del JSON; el controlador hace el dispatch con `mapper.map(dto, ClaseSegunTipo.class)` y el servicio guarda en el repositorio correcto con `instanceof`.

**Tradeoff.** El campo `tipo` no existe como columna (sería redundante: la subtabla ya indica el tipo). Prefiero la explicitud del front antes que adivinar por campos.

---

## 3. Ingresos en una sola tabla

**Contexto.** Originalmente había una tabla de ingresos por cada tipo de persona (trabajadores, padres, externos).

**Opciones.** Mantener tres tablas casi idénticas, o unificar.

**Decisión.** Una sola tabla `ingreso_personal` con FK a `persona`. El filtrado por tipo se hace con consultas `EXISTS` sobre las subtablas.

**Tradeoff.** Las consultas de listado son un poco más elaboradas, pero elimino código duplicado (3 servicios → 1), los reportes se unifican y agregar un nuevo tipo de persona no obliga a tocar la tabla de ingresos.

---

## 4. Relación N:M entre padres y alumnas

**Contexto.** Un padre puede tener varias hijas y una alumna puede tener varios apoderados.

**Opción descartada.** Poner `id_padre` en `alumna`: no alcanza (una alumna tendría un solo padre) y duplicar la alumna por cada padre es inaceptable.

**Decisión.** Tabla intermedia `padre_alumna` con PK compuesta (`id_padre`, `id_alumna`) y datos propios de la relación (`parentesco`, `es_tutor_principal`). En JPA se modela con `@IdClass` y dos `@ManyToOne` (de solo lectura).

**Tradeoff.** Una tabla más y llaves compuestas que hay que manejar, pero represento la realidad sin duplicar personas y puedo guardar atributos de la relación.

---

## 5. Tabla `rol` separada

**Contexto.** El rol del usuario estaba como texto ("admin"/"user").

**Decisión.** Tabla `rol` y FK `id_rol` en `usuario`.

**Tradeoff.** Un join más al consultar, a cambio de integridad (no se puede asignar un rol inexistente) y de poder agregar roles sin tocar código.

---

## 6. Seguridad con JWT (access + refresh)

**Contexto.** El frontend es una SPA y el backend no debía guardar sesión.

**Decisión.** Autenticación sin estado: access token de 1 hora + refresh token de 7 días. Spring Security con filtro propio que valida el token en cada request; `/login` y `/refresh` son públicos.

**Tradeoff.** Manejar dos tokens agrega complejidad (renovación automática en el front), pero el backend escala sin estado de sesión y el access token de vida corta reduce el riesgo si se filtra.

---

## 7. Paginación en el servidor

**Decisión.** Los listados usan `Pageable` de Spring Data; el frontend pide páginas y filtros en vez de traer todo.

**Tradeoff.** Más parámetros y lógica en los componentes, pero las consultas devuelven solo lo necesario (con `LIMIT/OFFSET` real) en lugar de cargar toda la tabla en memoria.

---

## Notas

- Las personas externas (visitas) se representan como registros de `persona` sin fila en ninguna subtabla; un query con `NOT EXISTS` las identifica.
- Las contraseñas se guardan con BCrypt (nunca en texto plano).
