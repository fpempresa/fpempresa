/* EmpleaFP · Buscador de Empresas — opción D
   Modelo: la búsqueda NO filtra al escribir; solo se ejecuta al pulsar
   "Buscar" o Enter (una única petición al servidor).
   Todo el formulario vive dentro de "Filtros" (incluido Nombre comercial).

   NOTA: los datos de ROWS son de ejemplo. Al integrar en EmpleaFP,
   sustituye filterRows() por la llamada real al servidor en run(). */

const { useState } = React;

/* ----------------------------- Datos de muestra ---------------------------- */
const ROWS = [
  { n: 1, nombre: "Persinas Garcíaaaaa", cif: "12345678Z", muni: "Valencia", fecha: "08/09/2023" },
  { n: 2, nombre: "S2Grupo", cif: "J37117538", muni: "Aldea del Fresno", fecha: "08/02/2024" },
  { n: 4, nombre: "Persianas perez", cif: "12345678Z", muni: "Dénia", fecha: "05/06/2024" },
];

/* Campos del formulario (todos dentro de Filtros) */
const FULL_FIELDS = [
  { key: "nombre", label: "Nombre comercial", ph: "Nombre comercial", span: true },
  { key: "id", label: "Id Empresa", ph: "Id Empresa" },
  { key: "cif", label: "CIF", ph: "CIF" },
  { key: "municipio", label: "Municipio", ph: "Municipio" },
  { key: "correo", label: "Correo de contacto", ph: "correo@empresa.com", type: "email" },
  { key: "desde", label: "Fecha desde", ph: "dd/mm/aaaa" },
  { key: "hasta", label: "Fecha hasta", ph: "dd/mm/aaaa" },
];

const has = (v) => v && String(v).trim();
const countActive = (v) => FULL_FIELDS.filter((f) => has(v[f.key])).length;

/* Filtrado local de demostración. En producción → petición al servidor. */
function filterRows(a) {
  a = a || {};
  const inc = (h, n) => h.toLowerCase().includes(String(n).trim().toLowerCase());
  return ROWS.filter((r) => {
    if (has(a.nombre) && !inc(r.nombre, a.nombre)) return false;
    if (has(a.id) && String(r.n) !== String(a.id).trim()) return false;
    if (has(a.cif) && !inc(r.cif, a.cif)) return false;
    if (has(a.municipio) && !inc(r.muni, a.municipio)) return false;
    return true;
  });
}

/* --------------------------------- Iconos --------------------------------- */
/* Cada icono se carga desde assets/icons/<name>.svg y se inserta en línea.
   El SVG usa stroke="currentColor", por lo que hereda el color del botón. */
const ICON_CACHE = {};
function Icon({ name, size = 16 }) {
  const [markup, setMarkup] = useState(ICON_CACHE[name] || "");
  React.useEffect(() => {
    if (ICON_CACHE[name]) { setMarkup(ICON_CACHE[name]); return; }
    let alive = true;
    fetch(`assets/icons/${name}.svg`)
      .then((r) => r.text())
      .then((txt) => { ICON_CACHE[name] = txt; if (alive) setMarkup(txt); })
      .catch(() => {});
    return () => { alive = false; };
  }, [name]);
  return (
    <span
      className="ic"
      aria-hidden="true"
      style={{ width: size, height: size }}
      dangerouslySetInnerHTML={{ __html: markup }}
    />
  );
}

/* -------------------------------- Tabla ----------------------------------- */
function EmpresasTable({ applied }) {
  const rows = filterRows(applied);
  const term = applied && (applied.nombre || "").trim();
  return (
    <>
      <table className="efp-table">
        <thead>
          <tr>
            <th style={{ width: 44 }}>#</th>
            <th>Nombre Comercial</th>
            <th style={{ width: 120 }}>CIF</th>
            <th style={{ width: 150 }}>Municipio</th>
            <th style={{ width: 96 }}>Fecha</th>
            <th style={{ width: 138 }}>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((r) => (
            <tr key={r.n}>
              <td><a className="efp-link">{r.n}</a></td>
              <td><a className="efp-link">{r.nombre}</a></td>
              <td>{r.cif}</td>
              <td>{r.muni}</td>
              <td>{r.fecha}</td>
              <td><div className="efp-rowact">
                <button className="efp-btn efp-btn-sm efp-btn-blue">Rep. Empresa</button>
                <button className="efp-icbtn efp-icbtn-red" title="Eliminar"><Icon name="trash" size={15} /></button>
              </div></td>
            </tr>
          ))}
          {rows.length === 0 && (
            <tr><td colSpan="6" className="efp-empty">Sin resultados{term ? ` para “${term}”` : ""}.</td></tr>
          )}
        </tbody>
      </table>
      <div className="efp-pager">
        <button className="efp-pg" disabled>«</button>
        <button className="efp-pg is-on">1</button>
        <button className="efp-pg" disabled>»</button>
      </div>
    </>
  );
}

/* ---------------------------------- App ----------------------------------- */
function App() {
  const [vals, setVals] = useState({});       // valores escritos en el formulario
  const [applied, setApplied] = useState({});  // criterios YA confirmados (Buscar)
  const [open, setOpen] = useState(false);     // panel de filtros desplegado
  const set = (k, v) => setVals((s) => ({ ...s, [k]: v }));
  const run = () => { setApplied({ ...vals }); setOpen(false); }; // ← aquí iría la petición real
  const active = countActive(vals);

  return (
    <div className="efp-card">
      {/* Fila 1 — Nueva Empresa SOLA y prominente */}
      <div className="efp-cardhead efp-cardhead-solo">
        <h2 className="efp-title">Empresas</h2>
        <button className="efp-btn efp-btn-orange efp-btn-lg"><Icon name="plus" size={15} /><span>Nueva Empresa</span></button>
      </div>

      {/* Fila 2 — Filtros (izq) · Descargar Excel (der) */}
      <div className="efp-subbar">
        <button
          className={"efp-btn efp-btn-outline efp-filterbtn" + (open ? " is-open" : "")}
          onClick={() => setOpen((o) => !o)}
        >
          <Icon name="funnel" size={15} /><span>Filtros</span>
          {active > 0 && <span className="efp-badge">{active}</span>}
          <span className="efp-chev" style={{ transform: open ? "rotate(180deg)" : "" }}><Icon name="chevron" size={14} /></span>
        </button>
        {active > 0 && !open && (
          <span className="efp-subhint">{active} {active === 1 ? "filtro aplicado" : "filtros aplicados"}</span>
        )}
        <button className="efp-btn efp-btn-outline efp-excel-r"><Icon name="excel" size={15} /><span>Descargar Excel</span></button>
      </div>

      {/* Panel de filtros plegable — un único Buscar */}
      <div className={"efp-collapse" + (open ? " open" : "")}>
        <div className="efp-collapse-inner">
          <div className="efp-panel">
            <div className="efp-grid">
              {FULL_FIELDS.map((f) => (
                <label key={f.key} className={"efp-field" + (f.span ? " span2" : "")}>
                  <span className="efp-flabel">{f.label}</span>
                  <input
                    className="efp-input"
                    type={f.type === "email" ? "email" : "text"}
                    placeholder={f.ph}
                    value={vals[f.key] || ""}
                    onChange={(e) => set(f.key, e.target.value)}
                    onKeyDown={(e) => e.key === "Enter" && run()}
                  />
                </label>
              ))}
            </div>
            <div className="efp-panel-foot">
              <button className="efp-btn efp-btn-ghost" onClick={() => setVals({})}>Limpiar</button>
              <button className="efp-btn efp-btn-blue" onClick={run}><Icon name="search" size={16} /><span>Buscar</span></button>
            </div>
          </div>
        </div>
      </div>

      <EmpresasTable applied={applied} />
    </div>
  );
}

ReactDOM.createRoot(document.getElementById("root")).render(<App />);
