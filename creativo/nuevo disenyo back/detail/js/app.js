/* EmpleaFP · Ficha de Empresa — interacciones de la maqueta */
(function () {
  // Estado activo del menú lateral
  document.querySelectorAll(".nav-link").forEach(function (a) {
    a.addEventListener("click", function (e) {
      e.preventDefault();
      document.querySelectorAll(".nav-link").forEach(function (x) {
        x.classList.remove("active");
      });
      a.classList.add("active");
    });
  });

  // Mantener el color de "placeholder" en los selects sin elegir
  document.querySelectorAll("select.control").forEach(function (s) {
    s.addEventListener("change", function () {
      s.classList.toggle("placeholder", s.value === "");
    });
  });
})();
