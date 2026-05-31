# Buscador de Empresas · EmpleaFP (opción D)

Estructura del proyecto:

```
buscador-empresas-D/
├── index.html              ← página principal (enlaza CSS y JS)
├── css/
│   └── styles.css          ← todos los estilos
├── js/
│   └── app.js              ← lógica (React + JSX)
└── assets/
    └── icons/              ← iconos en ficheros SVG sueltos
        ├── search.svg
        ├── funnel.svg
        ├── chevron.svg
        ├── excel.svg
        ├── plus.svg
        └── trash.svg
```

## Cómo verlo

Como el HTML carga el JS y los SVG como ficheros externos, ábrelo con un
**servidor local** (no con doble clic / file://, que bloquea esa carga):

```bash
# desde la carpeta buscador-empresas-D/
python3 -m http.server 8080
# y abre http://localhost:8080
```

(En VS Code también sirve la extensión "Live Server".)

## Notas para integrar en EmpleaFP

- **Iconos**: están en `assets/icons/*.svg` y se pintan con máscara CSS
  (`.ic` en `styles.css`), por lo que heredan el color del botón
  (`currentColor`). Puedes reutilizarlos como prefieras.
- **JSX**: `app.js` usa JSX vía Babel en el navegador (cómodo para revisar).
  Para producción, precompila el JSX o adáptalo a tu stack.
- **La búsqueda NO filtra al escribir**: solo se ejecuta al pulsar *Buscar*
  o Enter. La función `run()` en `app.js` es el punto donde debes hacer la
  llamada real al servidor (ahora usa `filterRows()` con datos de ejemplo).
- **Comportamiento**: "Nueva Empresa" va sola y destacada arriba; todos los
  campos (incl. Nombre comercial) viven dentro del panel **Filtros**,
  plegado por defecto, con un único botón **Buscar**.
