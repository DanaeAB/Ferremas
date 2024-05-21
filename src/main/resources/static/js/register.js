import AuthService from "/js/services/AuthService.js";

// Seleccion de elementos HTML
const registerForm = document.querySelector("#formcuenta");
const nombreInput = document.getElementById("nombre_i");
const apellidoInput = document.getElementById("apellido_i");
const emailInput = document.getElementById("email_i");
const passwordInput = document.getElementById("password_i");
const passwordConfirmInput = document.getElementById("passwordConfirm_i");
const direccionInput = document.getElementById("direccion_i");
const crearCuentaBtn = document.getElementById("crearcta_a");

const nombreError = document.getElementById("nombre_error");
const apellidoError = document.getElementById("apellido_error");
const emailError = document.getElementById("email_error");
const passwordError = document.getElementById("password_error");
const passwordConfirmError = document.getElementById("passwordConfirm_error");
const direccionError = document.getElementById("direccion_error");

const validaciones = {
    'nombre': false,
    'apellido': false,
    'email': false,
    'direccion': false,
    'password': false
};

nombreInput.addEventListener("input", validarNombre);
apellidoInput.addEventListener("input", validarApellido);
emailInput.addEventListener("input", validarEmail);
direccionInput.addEventListener("input", validarDireccion);
passwordInput.addEventListener("input", validarPassword);
passwordConfirmInput.addEventListener("input", validarPasswordConfirm);

// Funciones de validacion
function validarNombre(evt) {
    const nombre = evt.target.value.trim();
    validaciones['nombre'] = false;

    nombreError.textContent = "";
    nombreError.style.display = "none";

    if (nombre === "") {
        nombreError.textContent = "Por favor, ingresa tu nombre.";
        nombreError.style.display = "block";
    } else if (/^\d+$/.test(nombre)) {
        nombreError.textContent = "El nombre no puede contener números.";
        nombreError.style.display = "block";
    }
    else {
        validaciones['nombre'] = true;
    }
    habilitarBoton();
}

function validarApellido(evt) {
    const apellido = evt.target.value.trim();
    validaciones['apellido'] = false;

    apellidoError.textContent = "";
    apellidoError.style.display = "none";

    if (apellido === "") {
        apellidoError.textContent = "Por favor, ingresa tu apellido.";
        apellidoError.style.display = "block";
    } else if (/^\d+$/.test(apellido)) {
        apellidoError.textContent = "El apellido no puede contener números.";
        apellidoError.style.display = "block";
    } else {
        validaciones['apellido'] = true;
    }
    habilitarBoton();
}

function validarEmail(evt) {
    const email = evt.target.value.trim();
    validaciones['email'] = false;

    emailError.textContent = "";
    emailError.style.display = "none";

    if (email === "") {
        emailError.textContent = "Por favor, ingresa tu correo electrónico.";
        emailError.style.display = "block";
    } else if (!email.endsWith("@gmail.com") && !email.endsWith("@hotmail.com")) {
        emailError.textContent = "El email debe ser de Gmail o Hotmail.";
        emailError.style.display = "block";
    } else {
        validaciones['email'] = true;
    }
    habilitarBoton();
}

function validarDireccion(evt) {
    const direccion = evt.target.value.trim();
    validaciones['direccion'] = false;

    direccionError.textContent = "";
    direccionError.style.display = "none";

    if (direccion === "") {
        direccionError.textContent = "Por favor, ingresa tu dirección.";
        direccionError.style.display = "block";
    } else if (direccion.length < 6) {
        direccionError.textContent = "La dirección no puede ser menor a 6 caracteres.";
        direccionError.style.display = "block";
    } else {
        validaciones['direccion'] = true;
    }
    habilitarBoton();
}

function validarPassword(evt) {
    const password = evt.target.value.trim();
    validaciones['password'] = false;

    passwordError.textContent = "";
    passwordError.style.display = "none";

    if (password === "") {
        passwordError.textContent = "Por favor, ingresa tu contraseña.";
        passwordError.style.display = "block";
    } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/.test(password)) {
        passwordError.textContent = "La contraseña debe tener 6 caracteres y contener números y letras.";
        passwordError.style.display = "block";
    } else {
        validaciones['password'] = true;
    }
    habilitarBoton();
}

function validarPasswordConfirm(evt) {
    const passwordInput = document.getElementById("password_i").value.trim();
    const password = evt.target.value.trim();
    validaciones['passwordConfirm'] = false;

    passwordConfirmError.textContent = "";
    passwordConfirmError.style.display = "none";

    if (password === "") {
        passwordConfirmError.textContent = "Por favor, ingresa la confirmación de contraseña.";
        passwordConfirmError.style.display = "block";
    } else if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/.test(password)) {
        passwordConfirmError.textContent = "La contraseña debe tener 6 caracteres y contener números y letras.";
        passwordConfirmError.style.display = "block";
    } else if (password !== passwordInput) {
        passwordConfirmError.textContent = "La contraseña no coincide.";
        passwordConfirmError.style.display = "block";
    } else {
        validaciones['passwordConfirm'] = true;
    }
    habilitarBoton();
}

function habilitarBoton() {
    const validacion = Object.keys(validaciones).every((value) => validaciones[value] === true);
    console.log(validaciones);
    crearCuentaBtn.disabled = !validacion;
}

// Registro de usuario
registerForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const email = registerForm.email_i.value.trim();
    const nombre = registerForm.nombre_i.value.trim();
    const apellido = registerForm.apellido_i.value.trim();
    const direccion = registerForm.direccion_i.value.trim();
    const password = registerForm.password_i.value.trim();
    const passwordConfirmation = registerForm.passwordConfirm_i.value.trim();

    AuthService.register({ email, nombre, apellido, direccion, password, passwordConfirmation }).then(usuario => {
        localStorage.setItem("usuario", JSON.stringify(usuario));
        window.location.href = "/catalogo.html";
    }).catch(error => {
        const errors = error?.data?.errors;
        if (!errors) return;

        Object.keys(errors).forEach(error => {
            if(error === 'nombre') {
                nombreError.textContent = errors[error];
                nombreError.style.display = "block";
            } else if(error === 'apellido') {
                apellidoError.textContent = errors[error];
                apellidoError.style.display = "block";
            } else if (error === 'email') {
                emailError.textContent = errors[error];
                emailError.style.display = "block";
            } else if (error === 'direccion') {
                direccionError.textContent = errors[error];
                direccionError.style.display = "block";
            } else if (error === 'password') {
                passwordError.textContent = errors[error];
                passwordError.style.display = "block";
            } else if (error === 'passwordConfirmation') {
                passwordError.textContent = errors[error];
                passwordError.style.display = "block";
            }
        })
    });
})