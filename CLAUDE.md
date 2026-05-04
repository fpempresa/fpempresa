# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

FPempresa (empleaFP) is a Java EE web application — a job marketplace connecting vocational training (FP) graduates with companies in Spain. Four user roles exist: `titulado` (graduate), `empresa` (company), `centro` (training center), and `administrador`.

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

- Email: AWS SES (`MailKernelServiceImplAWS`)
- Reports/PDF: JasperReports (`ReportServiceImplJasper`)
- Email templates: Chunk Templates (`TemplateServiceImplChunk`)
- Monitoring: JavaMelody at `/api/administrador/monitoring`
- Scheduled tasks: `NotificarUsuariosInactivosTask` and `SoftDeleteUsuariosInactivosYNotificadosTask` run weekdays via Spring cron scheduler

## Key conventions

- New business process permissions are added via `@PreAuthorization`/`@PostAuthorization` annotations on the business process method **plus** a Flyway migration SQL file that inserts the ACE rows into the security tables.
- Hibernate mappings use XML (`.hbm.xml`), not JPA annotations. Each new entity needs a `.hbm.xml` file registered in `hibernate.cfg.xml`.
- Controllers use try-with-resources on `DataSession` and delegate exceptions to `ExceptionHelper.exceptionToHttpResponse()`.
