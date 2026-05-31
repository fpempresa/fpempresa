/* EmpleaFP · Carga de iconos
   Sustituye cada <i data-icon="nombre"></i> por el contenido del fichero
   assets/icons/nombre.svg, insertado en línea. Como los SVG usan
   stroke/fill = "currentColor", heredan el color del elemento contenedor.

   Nota: requiere servirse por HTTP (no file://), porque usa fetch.        */
(function () {
  var cache = {};

  function load(name) {
    if (cache[name]) return cache[name];
    cache[name] = fetch("assets/icons/" + name + ".svg")
      .then(function (r) { return r.text(); })
      .catch(function () { return ""; });
    return cache[name];
  }

  function render() {
    var nodes = document.querySelectorAll("[data-icon]");
    nodes.forEach(function (el) {
      var name = el.getAttribute("data-icon");
      load(name).then(function (markup) {
        if (!markup) return;
        var tmp = document.createElement("div");
        tmp.innerHTML = markup.trim();
        var svg = tmp.querySelector("svg");
        if (svg) el.replaceWith(svg);
      });
    });
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", render);
  } else {
    render();
  }
})();
