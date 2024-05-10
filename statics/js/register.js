document.addEventListener("DOMContentLoaded", function () {
    var nombreInput = document.getElementById("nombre_i");
    var apellidoInput = document.getElementById("apellido_i");
    var emailInput = document.getElementById("email_i");
    var passwordInput = document.getElementById("password_i");

    nombreInput.addEventListener("input", validarNombre);
    apellidoInput.addEventListener("input", validarApellido);
    emailInput.addEventListener("input", validarEmail);
    passwordInput.addEventListener("input", validarPassword);

    function validarNombre() {
        var nombre = nombreInput.value.trim();
        var nombreError = document.getElementById("nombre_error");

        nombreError.textContent = "";
        nombreError.style.display = "none";

        if (nombre === "") {
            nombreError.textContent = "Por favor, ingresa tu nombre.";
            nombreError.style.display = "block";
        } else if (/^\d+$/.test(nombre)) {
            nombreError.textContent = "El nombre no puede contener números.";
            nombreError.style.display = "block";
        } else {
            redirigirSiEsValido();
        }
    }

    function validarApellido() {
        var apellido = apellidoInput.value.trim();
        var apellidoError = document.getElementById("apellido_error");

        apellidoError.textContent = "";
        apellidoError.style.display = "none";

        if (apellido === "") {
            apellidoError.textContent = "Por favor, ingresa tu apellido.";
            apellidoError.style.display = "block";
        } else if (/^\d+$/.test(apellido)) {
            apellidoError.textContent = "El apellido no puede contener números.";
            apellidoError.style.display = "block";
        } else {
            redirigirSiEsValido();
        }
    }

    function validarEmail() {
        var email = emailInput.value.trim();
        var emailError = document.getElementById("email_error");

        emailError.textContent = "";
        emailError.style.display = "none";

        if (email === "") {
            emailError.textContent = "Por favor, ingresa tu correo electrónico.";
            emailError.style.display = "block";
        } else if (!email.endsWith("@gmail.com") && !email.endsWith("@hotmail.com")) {
            emailError.textContent = "El email debe ser de Gmail o Hotmail.";
            emailError.style.display = "block";
        } else {
            redirigirSiEsValido();
        }
    }

    function validarPassword() {
        var password = passwordInput.value.trim();
        var passwordError = document.getElementById("password_error");

        passwordError.textContent = "";
        passwordError.style.display = "none";

        if (password === "") {
            passwordError.textContent = "Por favor, ingresa tu contraseña.";
            passwordError.style.display = "block";
        } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/.test(password)) {
            passwordError.textContent = "La contraseña debe tener 6 caracteres y contener números y letras.";
            passwordError.style.display = "block";
        } else {
            redirigirSiEsValido();
        }
    }

    function redirigirSiEsValido() {
        var nombre = nombreInput.value.trim();
        var apellido = apellidoInput.value.trim();
        var email = emailInput.value.trim();
        var password = passwordInput.value.trim();

        if (nombre !== "" && !(/^\d+$/.test(nombre)) &&
            apellido !== "" && !(/^\d+$/.test(apellido)) &&
            email !== "" && (email.endsWith("@gmail.com") || email.endsWith("@hotmail.com")) &&
            password !== "" && /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/.test(password)) {
            window.location.href = "catalogo.html";
        }
    }
});