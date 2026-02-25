document.addEventListener("DOMContentLoaded", () => {
  const modal = document.getElementById("modalEliminar");
  if (!modal) return;

  modal.addEventListener("show.bs.modal", (event) => {
    const btn = event.relatedTarget;
    const action = btn.getAttribute("data-action");
    const nombre = btn.getAttribute("data-nombre");

    const form = document.getElementById("formEliminar");
    const nombreSpan = document.getElementById("nombreModal");
    if (form) form.setAttribute("action", action);
    if (nombreSpan) nombreSpan.textContent = nombre || "";
  });
});


