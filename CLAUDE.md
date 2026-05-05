# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

FPempresa (empleaFP) is a Java EE web application — a job marketplace connecting vocational training (FP) graduates with companies in Spain. Four user roles exist: `titulado` (graduate), `empresa` (company), `centro` (training center), and `administrador`.

## Java version

The project compiles with **Java 7**. Do not use any Java 8+ features: no lambdas, no method references, no `Stream`, no `Comparator.comparing()`, no `List.sort()`, no `Optional`, no default interface methods, no `Files.readString()`. Use anonymous inner classes instead of lambdas, and `Collections.sort()` with an explicit `Comparator`.

## Build

```bash
# Build WAR (requires APP_ENVIRONMENT set to find config.properties)
APP_ENVIRONMENT=local ant

# Output: dist/fpempresa.war
# Deploy to Tomcat (configured in nbproject)
```

Environment-specific configuration is loaded from `../private/config.properties.${APP_ENVIRONMENT}` during the build. This file lives outside the repo.

The project depends on the sibling `ix3` framework (8 JARs: `ix3core`, `ix3rule`, `ix3dao`, `ix3security`, `ix3securityimpl`, `ix3service`, `ix3businessprocess`, `ix3web`). These must be built first from `../ix3/`.

## Tests

```bash
# Run all tests via Ant
ant test

# Tests are REST integration tests (REST Assured) that require a running server.
# Test source: test/es/logongas/fpempresa/presentacion/api/
```

## CSS

LESS (`web/less/`) and SCSS (`web/scss/`) compile to CSS (`web/css/`). NetBeans handles this automatically on save. Compiled CSS files are committed to the repo.

## Architecture

### Backend layers (Java, under `src/java/es/logongas/fpempresa/`)

| Package | Role |
|---|---|
| `modelo/` | Domain entities (POJOs) |
| `dao/` | Hibernate DAOs + HBM XML mappings |
| `service/` | CRUD service layer (thin, mostly delegates) |
| `businessprocess/` | Business logic (authorization annotations live here) |
| `presentacion/controller/` | Spring MVC REST controllers mapped to `/api/*` |
| `security/` | Auth + custom annotation-based authorization |
| `database/` | Flyway SQL migrations (V100, V101, ...) |

The ix3 framework provides generic CRUD factories (`CRUDServiceFactory`, `CRUDBusinessProcessFactory`, `DAOFactory`) that auto-discover implementations by naming convention. Adding a new entity typically requires: model class → HBM XML mapping → register in `hibernate.cfg.xml` → optional service/businessprocess overrides.

Spring XML configuration: `src/java/applicationContext.xml` (beans, DAOs, services, business processes, security, scheduling).

### EndPointsFactoryImpl y BeanMapper

**`EndPointsFactoryImpl`** (`src/java/es/logongas/fpempresa/presentacion/controller/EndPointsFactoryImpl.java`) es el **registro central de todos los endpoints HTTP** de la aplicación. Cada URL que devuelve JSON debe estar registrada aquí. Si falta, `ControllerHelper.objectToHttpResponse` lanza una `NullPointerException` al intentar obtener el `BeanMapper` del endpoint.

Hay dos métodos para registrar un endpoint:

```java
// Para entidades Hibernate: registra automáticamente path/ClassName/**
EndPoint.createEndPointCrud(path, MiEntidad.class)
// Con BeanMapper personalizado:
EndPoint.createEndPointCrud(path, new BeanMapper(MiEntidad.class, deleteProperties, expandProperties))

// Para recursos no-Hibernate o rutas custom (GET/POST/etc. específico):
EndPoint.createEndPoint(path + "/MiEntidad/**", "GET", new BeanMapper(MiEntidad.class, null, null))
```

Hay cinco secciones de paths (`/site`, `/administrador`, `/titulado`, `/centro`, `/empresa`). Los endpoints comunes a todos los roles se añaden en `addCommonEndPoints`. **Siempre que se añada un nuevo recurso REST hay que registrarlo aquí.**

**`BeanMapper`** controla qué campos se incluyen/excluyen en la serialización JSON:

```java
new BeanMapper(Class entityClass, String deleteProperties, String expandProperties)
```

- **`deleteProperties`** — lista de propiedades a excluir, separadas por comas. Prefijos de dirección:
  - Sin prefijo → excluir en ambas direcciones (lectura y escritura)
  - `<campo` → excluir solo en salida (toJson, lo que el cliente recibe)
  - `campo>` → excluir solo en entrada (fromJson, lo que el cliente envía)
- **`expandProperties`** — relaciones a expandir (incluir el objeto completo en lugar de solo el ID), separadas por comas. `"*"` expande todas. Los mismos prefijos `<`/`>` aplican para controlar la dirección.

Ejemplo: `new BeanMapper(Oferta.class, "secretToken,empresa.numOfertasPublicadas>", "ciclos")` excluye `secretToken` en ambas direcciones, excluye `empresa.numOfertasPublicadas` solo en entrada, y expande la relación `ciclos`.

### Security

Authorization defaults to **DENY**. Two providers run in order:

1. **`AuthorizationProviderImplAnnotation`** — reads `@PreAuthorization`/`@PostAuthorization` annotations on business process methods. Each annotation contains `@ACE` entries with `aceType` (Allow/Deny), `groupLogin`, and optionally a `conditionalExpression` (SpEL) or `conditionalScriptEvaluatorFQCN` (Java class in `security/ace/`).

2. **`AuthorizationProviderImplIdentity`** — database-driven ACEs (Flyway migrations set these up in files named `*_ace_*.sql`).

Session storage is JWT via cookie (`XSRF-TOKEN`), implemented in `WebSessionSidStorageImplJwt`.

### Frontend (AngularJS 1.x SPAs under `web/`)

Hay cinco tipos de usuario, cada uno con su propia aplicación Angular independiente (su propio `index.html`, `app.js`, `app-route.js` y `app-config.js`). Aunque los modelos de datos son los mismos, cada app expone pantallas distintas adaptadas a su rol:

| App | Path | Usuario |
|---|---|---|
| Site | `web/site/` | Usuarios sin registrar (página pública, login, registro) |
| Titulado | `web/titulado/` | Graduados de FP (curriculum, búsqueda de ofertas) |
| Empresa | `web/empresa/` | Empresas (gestión de ofertas, candidatos) |
| Centro | `web/centro/` | Profesores de centros educativos (gestión de titulados, ofertas) |
| Administrador | `web/administrador/` | Administradores de la plataforma (gestión global) |

Shared AngularJS code lives in `web/common/`. The custom `ix3-angular` library (`web/lib/ix3-angular/`) is the core of the client architecture and defines a strict layered stack.

> **Curriculum**: los datos del currículum del titulado se muestran en tres apps distintas. Cualquier cambio en cómo se presenta un campo del currículum debe aplicarse en los tres sitios:
> - `web/titulado/views/curriculum/` y `web/titulado/views/main/main.html` — el titulado edita y ve su propio currículum
> - `web/empresa/views/candidato/detail.html` — la empresa ve el currículum al revisar un candidato
> - `web/administrador/views/usuario/curriculum/` — el administrador ve y edita el currículum de cualquier titulado

REST calls go to `/api/*`. The Angular apps use `app-constant.js` (base URL config) and `server.js.jsp` (server-side JS constants).

#### Estructura de pantalla

Cada app tiene la misma estructura de tres zonas, definida en `index.html` con `IndexController`:

```
index.html
├── fragments/header/header.html        ← <ng-include> fijo en la parte superior
├── <ui-view>                           ← outlet raíz de ui-router
│   └── fragments/lateralmenu/lateralmenu.html   (estado "lateralmenu", LateralMenuController)
│       ├── columna izquierda: enlaces del menú lateral
│       └── columna derecha: <ui-view> anidado  ← aquí se renderizan las vistas
│           ├── views/main/main.html    ← estado "lateralmenu.main", url "/", pantalla por defecto
│           └── views/**/*.html         ← resto de pantallas, todas hijas de "lateralmenu"
└── fragments/footer/footer.html        ← <ng-include> fijo en la parte inferior
```

El estado `lateralmenu` es el **padre de todos los estados** de la app. Se registra en `app-route.js` y actúa como shell de layout: proporciona el menú lateral (columna izquierda) y el área de contenido con su `<ui-view>` anidado (columna derecha). Todos los demás estados se declaran como hijos suyos: `$stateProvider.state('lateralmenu.nombreVista', ...)`.

La ruta por defecto (`$urlRouterProvider.otherwise('/')`) carga el estado `lateralmenu.main`, que renderiza `views/main/main.html` en el `<ui-view>` interior. El resto de vistas viven en `views/` y se registran como hijos de `lateralmenu` en sus propios ficheros JS.

#### ix3-angular client layers (bottom → top)

Each layer has a factory (`remoteDAOFactory`, `repositoryFactory`, `serviceFactory`) that auto-creates a generic instance for any entity name, but can be overridden or extended per entity via the provider at config time.

| Layer | ix3-angular service | Location in `web/lib/ix3-angular/js/` | Role |
|---|---|---|---|
| **RemoteDAO** | `remoteDAOFactory` | `dao/remotedao.js` | Raw `$http` calls to `/api/{Entity}`. Standard operations: `create` (`GET /$create`), `get`, `insert` (POST), `update` (PUT), `delete`, `search` (with `$orderby`, `$expand`, `$pagenumber`, `$pagesize`, `$distinct`, `$namedsearch`, and `field$operator=value` filter params), `getChild`, `schema` (`GET /$schema`) |
| **Repository** | `repositoryFactory` | `repository/repository.js` | Wraps RemoteDAO. Calls `richDomain.extend()` on every returned object. Runs `domainValidator.validate()` before insert/update |
| **Service** | `serviceFactory` | `service/service.js` | Thin delegation over Repository. Entry point used by controllers |
| **Domain** | `richDomainProvider` | `domain/richdomain.js` | Entity enrichment: adds methods and `$validators` to plain objects returned from the API, by entity class name (`$className` field). Global transformers + per-entity transformers |
| **Controller** | `genericControllerCrudList` / `genericControllerCrudDetail` | `controller/crud/` | Generic CRUD controllers. List controller handles `filters`, `orderby`, `page`, and `namedSearch`. Detail controller handles `controllerAction` (NEW/EDIT/VIEW/DELETE) with `preXxx`/`postXxx` hooks |
| **Routes** | `crudRoutes` provider | `controller/crud/crudroutes.js` | Auto-registers all CRUD routes (`/entity/search`, `/entity/new`, `/entity/edit/:id`, `/entity/view/:id`, `/entity/delete/:id`) and their `resolve` (loads entity schema before the controller runs) |
| **Presentation** | directives + filters + services | `presentation/` | Form directives (`ix3form`, `ix3validation`, `ix3label`, `ix3options`, `ix3date`, `ix3businessmessages`, `ix3pagination`), filters (`ix3date`, `ix3orderby`, `ix3when`), and UI services (`dialog`, `notify`) |
| **Infrastructure** | `ix3Configuration`, `authorizationManager`, `schemaEntities`, `session` | `infrastructure/` | `ix3Configuration`: API URL, security ACL, CRUD config. `authorizationManager`: client-side route guard (`$stateChangeStart` → 200/401/403). `schemaEntities`: fetches and caches entity metadata from `/$schema`. `session`: current user |

#### Extending a layer for a specific entity

When an entity needs custom HTTP calls or business logic, each layer is extended at Angular config time in `web/common/`:

```
web/common/dao/{domain}/EntityRemoteDAO.js      → remoteDAOFactoryProvider.addExtendRemoteDAO("Entity", fn)
web/common/repository/{domain}/EntityRepo.js    → repositoryFactoryProvider.addExtendRepository("Entity", fn)
web/common/service/{domain}/EntityService.js    → serviceFactoryProvider.addExtendService("Entity", fn)
web/common/domain/{domain}/Entity.js            → richDomainProvider.addEntityTransformer("Entity", fn)
```

A simple entity with no custom logic needs none of these — just CRUD route registration and `search.html` + `detail.html` templates. Client-side `$validators` defined in the domain transformer are evaluated by `domainValidator` before every insert/update, before the HTTP call is made.

#### Controladores genéricos de pantalla

Toda pantalla de la aplicación arranca llamando a uno de estos dos métodos sobre su `$scope`. El controlador concreto solo añade lo que es específico de esa pantalla.

**Pantalla de búsqueda/listado — `genericControllerCrudList.extendScope($scope, controllerParams)`**

Añade al scope:

| Propiedad / función | Descripción |
|---|---|
| `filters` | Objeto con operadores: `$eq`, `$ne`, `$gt`, `$ge`, `$lt`, `$le`, `$like`, `$llike`, `$liker`, `$lliker`, `$isnull`. Se pasan como params a la API |
| `orderby` | Array de `{fieldName, orderDirection}` (ASC/DESC) |
| `page` | `{pageNumber, pageSize, totalPages}` — la paginación se gestiona sola con `$watch` |
| `distinct`, `namedSearch` | Flags adicionales de búsqueda |
| `models` | Array con los resultados tras el `search()` |
| `businessMessages` | Errores de la última operación |
| `search()` | Ejecuta la búsqueda con los filtros actuales |
| `buttonSearch()` | Resetea a página 0 y llama a `search()` |
| `buttonNew/Edit/Delete/View(id)` | Navegan a la ruta correspondiente |
| `preSearch(filters)`, `postSearch(models)` | Hooks para override en el controlador concreto |

**Pantalla de formulario — `genericControllerCrudDetail.extendScope($scope, controllerParams)`**

`controllerParams` viene del `resolve` de la ruta e incluye `entity`, `controllerAction`, `id`, `parentProperty`, `parentId`, `expand`.

Añade al scope:

| Propiedad / función | Descripción |
|---|---|
| `controllerAction` | Modo activo: `"NEW"`, `"EDIT"`, `"VIEW"`, `"DELETE"` |
| `model` | Objeto cargado del servidor (o creado vacío en NEW) |
| `businessMessages` | Errores de validación o del servidor |
| `labelButtonOK / Cancel` | Se actualizan solos según `controllerAction` |
| `runningOKAction` | `true` mientras se procesa, evita doble submit |
| `doCreate/Get/Insert/Update/Delete()` | Operaciones CRUD con validación integrada |
| `preXxx / postXxx` | Hooks vacíos para override: `preCreate`, `postCreate`, `preGet`, `postGet`, `preInsert`, `postInsert`, `preUpdate`, `postUpdate`, `preDelete`, `postDelete` |
| `buttonOK()` | Ejecuta la operación correcta según `controllerAction` y llama a `finishOK()` |
| `buttonCancel()` | Llama a `finishCancel()` → `history.back()` |
| `finishOK / finishCancel()` | Navegación al terminar — se puede sobreescribir |
| `buttonNewChild/EditChild/DeleteChild/ViewChild/DefaultChild(entity, pk, parentProperty, parentId)` | Navegan a una vista hija. En NEW/EDIT guardan primero antes de navegar |

`doInsert()` y `doUpdate()` llaman primero a `formValidator.validate($scope.mainForm, $scope.$validators)` — si hay errores de HTML5 o de `$validators` del scope, se asignan a `businessMessages` y no se hace la llamada al servidor.

**`formValidator`** (capa de validación de formulario HTML):

Valida el `ngForm` de Angular inspeccionando `$error` en cada campo. Primero comprueba errores HTML5 (`required`, `email`, `maxlength`, `minlength`, `pattern`, `min`, `max`, `url`, `integer`) y luego, **solo si no hay errores HTML5**, ejecuta el array `validators` del scope (reglas JS personalizadas de la vista, distintas de los `$validators` del dominio). El label de cada error se resuelve automáticamente buscando el `<label for="...">` del input en el DOM.

#### Layer responsibilities (illustrated by `Usuario`, the most complete example)

- **RemoteDAO** (`UsuarioRemoteDAO.js`): defines the raw HTTP calls only — URL, method, params, error mapping. No domain knowledge.
- **Repository** (`UsuarioRepository.js`): re-exposes every RemoteDAO method, calling `richDomain.extend()` on the response so returned objects get their domain methods. This is the only place `richDomain.extend()` is called for custom operations.
- **Service** (`UsuarioService.js`): the only layer that knows about `session`. After any `get` or `update` (standard or custom), if the returned entity is the logged-in user it calls `session.setUser()` to keep session data fresh. Cross-cutting side-effects belong here, not in Repository.
- **Domain** (`Usuario.js`): adds methods (`getNombreCompleto`, `getEstadoUsuarioDescription`, `getTipoUsuarioDescription`) and `$validators` to each object via `richDomainProvider.addEntityTransformer`. Validators use `executeInActions: ['INSERT']` to run only on creation — not on every update.
- **Controller** (in each app's `views/usuario/usuario.js`): only the `administrador` app uses `addAllRoutes` (full search + CRUD). Other apps (`titulado`, `empresa`, `centro`) use `addEditRoute` only — the route set is configured per-app, not shared. Controllers call `genericControllerCrudDetail.extendScope` and then add only their app-specific logic (e.g., `softDelete` with double-confirmation sweetAlert in `titulado`, `updateEstadoUsuario` in `administrador`).

### Database migrations

Flyway migrations live in `src/java/es/logongas/fpempresa/database/` as `V{number}__{description}.sql`. Migrations run automatically at startup via `DatabaseMigrateContextListener`. Always add new migrations with the next sequential version number.

### Email & integrations

- Email: AWS SES (`MailKernelServiceImplAWS`), fallback SMTP (`MailKernelServiceImplSMTP`)
- Reports/PDF: JasperReports (`ReportServiceImplJasper`). El PDF del currículum se genera a partir de cuatro plantillas en `src/java/es/logongas/fpempresa/service/report/files/`:
  - `curriculum.jrxml` — plantilla principal
  - `formacion_academica.jrxml` — subinforme de formación académica
  - `experiencia_laboral.jrxml` — subinforme de experiencia laboral
  - `titulo_idioma.jrxml` — subinforme de idiomas

  Cada `.jrxml` debe compilarse a su `.jasper` correspondiente antes de desplegar. **Si se modifica cualquier `.jrxml` hay que volver a compilarlo** (con Jaspersoft Studio o la herramienta Ant/CLI de JasperReports) para que el cambio tenga efecto en el PDF generado. Ambos ficheros (`.jrxml` y `.jasper`) están versionados en el repositorio.
- Email templates: Chunk Templates (`TemplateServiceImplChunk`) — though email bodies are currently built programmatically in `NotificationImpl` via an inner `BodyContent` class (título, parrafos, pie, labelButton, linkButton)
- Monitoring: JavaMelody at `/api/administrador/monitoring`
- Scheduled tasks: `NotificarUsuariosInactivosTask` and `SoftDeleteUsuariosInactivosYNotificadosTask` run weekdays via Spring cron scheduler

#### Sistema de notificaciones por correo

La clase central es `NotificationImpl` (`service/notification/impl/NotificationImpl.java`). Toda la lógica de composición y envío de correos pasa por aquí. `MailKernelService.send(Mail)` es la única salida real hacia AWS SES.

**Configuración relevante** (`config.properties`):

| Clave | Uso |
|---|---|
| `app.url` | URL base para los links dentro de los correos |
| `app.correoSoporte` | Destinatario de mensajes de soporte y errores al admin |
| `app.enabledEMailNotifications` | `true`/`false` — deshabilitar no lanza ningún correo |
| `mail.sender` | Dirección `From` de todos los correos |

**Correos transaccionales** (disparados por acción de usuario):

| Método en `Notification` | Disparado por | Destinatario | Asunto |
|---|---|---|---|
| `validarCuenta(Usuario)` | Registro de nueva cuenta | Usuario | "Confirma tu dirección de correo para acceder a EmpleaFP" |
| `resetearContrasenya(Usuario)` | "Olvidé mi contraseña" | Usuario | "Cambiar contraseña en EmpleaFP" |
| `nuevaOferta(Usuario, Oferta)` | `OfertaCRUDService` al publicar oferta | Titulados suscritos por provincia/ciclo | "Nueva oferta de empleo en EmpleaFP: {puesto}" |
| `inscritoCandidato(DataSession, Candidato)` | `CandidatoCRUDService` al insertar candidato | Email de contacto de la empresa | "Nuevo candidato en EmpleaFP de su oferta: {puesto}" (adjunta `curriculum.pdf`) |
| `desinscritoCandidato(DataSession, Candidato)` | `CandidatoCRUDService` al borrar candidato | Email de contacto de la empresa | "Desinscrito candidato en EmpleaFP de su oferta '{puesto}'" |
| `mensajeSoporte(nombre, correo, mensaje)` | Formulario de soporte | `app.correoSoporte` | "Petición de soporte de {nombre}" |

Notas:
- `nuevaOferta` se envía en un hilo aparte vía `sendMailExecutor` (ThreadPoolTaskExecutor, 5-30 hilos) para no bloquear la petición HTTP.
- `inscritoCandidato` y `desinscritoCandidato` solo se envían si la empresa **no** tiene centro asociado (`empresa.centro == null`).
- `validarCuenta` tiene límite de 5 envíos/día y mínimo 30 minutos entre reenvíos (controlado por `EventCountInDay`).
- `resetearContrasenya` tiene límite de 50 intentos/día.

**Correos de tareas programadas** (Spring scheduler, `applicationContext.xml` líneas 99–115):

| Tarea | Cron | Método | Qué hace |
|---|---|---|---|
| `NotificarUsuariosInactivosTask` | `0 0 9 ? * MON-FRI` | `notification.usuarioInactivo(Usuario)` | Avisa a titulados inactivos de que su cuenta será borrada. Registra `fechaEnvioCorreoAvisoBorrarUsuario`. |
| `SoftDeleteUsuariosInactivosYNotificadosTask` | `0 30 9 ? * MON-FRI` | — | Borra (soft-delete) a los avisados hace ≥15 días. Envía resumen al admin con conteo de OK/errores. |

**Correos internos al administrador** (automáticos ante errores):

- `exceptionToAdministrador(url, user, Throwable)` — cualquier excepción en tareas programadas.
- `mensajeToAdministrador(asunto, cuerpo)` — resumen del soft-delete masivo.
- Cuando `EventCountInDay` supera el umbral (p. ej. 300 registros/día con error), se notifica al admin.

**Cumplimiento RGPD**:
- Todos los correos incluyen pie legal con entidad, dirección y opción de baja.
- Correos promocionales (`nuevaOferta`) añaden cabeceras `List-Unsubscribe` / `List-Unsubscribe-Post` (RFC 8058) apuntando a `/api/site/Usuario/cancelarSuscripcion/{idIdentity}/{publicToken}`.

## Key conventions

- New business process permissions are added via `@PreAuthorization`/`@PostAuthorization` annotations on the business process method **plus** a Flyway migration SQL file that inserts the ACE rows into the security tables.
- Hibernate mappings use XML (`.hbm.xml`), not JPA annotations. Each new entity needs a `.hbm.xml` file registered in `hibernate.cfg.xml`.
- Controllers use try-with-resources on `DataSession` and delegate exceptions to `ExceptionHelper.exceptionToHttpResponse()`.
