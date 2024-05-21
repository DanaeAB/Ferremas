import AuthService from "/js/services/AuthService.js";

const usuarioNav = document.querySelector("#user");
const navLinks = document.querySelector("#navLinks");
AuthService.getUserData().then(user => {
  localStorage.setItem("usuario", JSON.stringify(user));
  usuarioNav.textContent = "Pedidos";
  usuarioNav.href = "/pedidos.html";

  function cerrarSesion() {
    localStorage.removeItem("usuario");
    localStorage.removeItem("token");
    window.location.href = "/";
  }

  const el = document.createElement("button");
  el.classList.value = "w3-hide-small w3-bar-item w3-button w3-mobile w3-medium w3-right";
  el.textContent = "Cerrar sesión";
  el.style.marginTop = "10px";

  el.addEventListener("click", cerrarSesion);

  navLinks.prepend(el);

});