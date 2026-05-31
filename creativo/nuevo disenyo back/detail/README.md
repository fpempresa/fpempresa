# Ficha de Empresa · EmpleaFP

Pantalla de edición de empresa con menú lateral, con el sistema visual EmpleaFP.

## Estructura

```
ficha-empresa/
├── index.html              ← maquetación de la página (enlaza CSS y JS)
├── css/
│   └── styles.css          ← todos los estilos
├── js/
│   ├── icons.js            ← carga los SVG e inserta los iconos
│   └── app.js              ← interacciones (menú activo, selects)
└── assets/
    └── icons/              ← 25 iconos en ficheros SVG sueltos
        ├── brand.svg  dash.svg  building.svg  briefcase.svg  tag.svg
        ├── globe.svg  shield.svg  user.svg  users.svg  cap.svg
        ├── clock.svg  book.svg  id.svg  lock.svg  chart.svg
        ├── activity.svg  file.svg  mail.svg  cal.svg  pin.svg
        └── phone.svg  info.svg  save.svg  exit.svg  person.svg
```

## Cómo verlo

Como el HTML carga el CSS, el JS y los SVG como ficheros externos, ábrelo con
un **servidor local** (no con doble clic / file://, que bloquea esa carga):

```bash
# desde la carpeta ficha-empresa/
python3 -m http.server 8080
# y abre http://localhost:8080
```

(En VS Code también sirve la extensión "Live Server".)

## Notas para integrar en EmpleaFP

- **Iconos**: cada `<i data-icon="nombre"></i>` se sustituye por
  `assets/icons/nombre.svg` (ver `js/icons.js`). Los SVG usan
  `currentColor`, así que heredan el color del botón o enlace.
- **Menú lateral**: el elemento activo lleva la clase `.nav-link.active`
  (fondo azul claro + barra naranja). Cámbiala según la página.
- **Datos**: los valores de los campos son de ejemplo; conéctalos a tus datos
  reales al integrar.
- **Colores y tipografía**: definidos como variables CSS en `:root`
  (`--navy`, `--blue`, `--orange`, etc.) dentro de `css/styles.css`.
