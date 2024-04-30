$(document).ready(function () {
    $("#form-registrar").submit(function (e) {

        var nombre = $("#firstname").val();
        var correo = $("#email").val();
        var clave = $("#clave").val();
        var apellido = $("#lastname").val();

        let msjNombre = "";
        let msjApellido = "";
        let msjCorreo = "";
        let msjClave = "";

        let enviar = false;

        /* NOMBRE*/
        if (nombre.trim().length < 2 || nombre.trim().length > 10) {
            msjNombre += "<li>El nombre debe ser entre 2 y 10 caracteres</li>";
            enviar = true;
        }

        var letra = nombre.charAt(0);
        if (!esMayuscula(letra)) {
            msjNombre += "<li>La primera letra debe ser mayúscula</li>";
            enviar = true;
        }

        if (!soloLetras(nombre)) {
            msjNombre += "<li>Solo deben ser letras sin espacios en blanco</li>";
            enviar = true;
        }

        /* FIN NOMBRE*/

        /* APELLIDO */

        if (apellido.trim().length < 2 || apellido.trim().length > 30) {
            msjApellido += "<li>El apellido debe ser entre 2 y 30 caracteres</li>";
            enviar = true;
        }

        var letra = apellido.charAt(0);
        if (!esMayuscula(letra)) {
            msjApellido += "<li>La primera letra debe ser mayúscula</li>";
            enviar = true;
        }

        if (!soloLetrasyespacio(apellido)) {
            msjApellido += "<li>Solo deben ser letras</li>";
            enviar = true;
        }


        /* FIN APELLIDO*/

        /* EMAIL */

        if (!validarEmail(correo)) {
            msjCorreo += "<li>El correo es incorrecto</li>";
            enviar = true;
        }

        if (correo.trim().length > 50) {
            msjCorreo += "<li>El correo debe ser menor a 50 caracteres</li>";
            enviar = true;
        }

        /* FIN EMAIL*/


        /* CONTRASEÑA */

        if (clave.trim().length < 8) {
            msjClave += "<li>La clave debe ser mayor a 8 caracteres</li>";
            enviar = true;
        }

        if (clave.trim().length > 50) {
            msjClave += "<li>La clave debe ser menor a 50 caracteres</li>";
            enviar = true;
        }

        if (!validarAlMenosUnNumero(clave)) {
            msjClave += "<li>Minimo 1 número</li>";
            enviar = true;
        }

        if (!validarAlMenosUnaMayuscula(clave)) {
            msjClave += "<li>Minimo 1 Mayúscula</li>";
            enviar = true;
        }

        if (!validarAlMenosUnCaracterEspecial(clave)) {
            msjClave += "<li>Minimo 1 Caracter Especial</li>";
            enviar = true;
        }

        if (!validarAlMenosUnaMinuscula(clave)) {
            msjClave += "<li>Minimo 1 Minúscula</li>";
            enviar = true;
        }
        /* FIN CONTRASEÑA */

        /* CONTRASEÑA */

        if (clave.trim().length < 8) {
            msjClave += "<li>La clave debe ser mayor a 8 caracteres</li>";
            enviar = true;
        }

        if (clave.trim().length > 50) {
            msjClave += "<li>La clave debe ser menor a 50 caracteres</li>";
            enviar = true;
        }

        if (!validarAlMenosUnNumero(clave)) {
            msjClave += "<li>Minimo 1 número</li>";
            enviar = true;
        }

        if (!validarAlMenosUnaMayuscula(clave)) {
            msjClave += "<li>Minimo 1 Mayúscula</li>";
            enviar = true;
        }

        if (!validarAlMenosUnCaracterEspecial(clave)) {
            msjClave += "<li>Minimo 1 Caracter Especial</li>";
            enviar = true;
        }

        if (!validarAlMenosUnaMinuscula(clave)) {
            msjClave += "<li>Minimo 1 Minúscula</li>";
            enviar = true;
        }
        /* FIN CONTRASEÑA */
        /* ADVERTENCIAS (WARNINGS)*/
        if (enviar) {
            $("#warningsnombre").html(msjNombre);
            $("#warningsapellido").html(msjApellido);
            $("#warningsemail").html(msjCorreo);
            $("#warningsclave").html(msjClave);

            $("#warnings").html("");

            e.preventDefault();
        }
        else {
            $("#warningsnombre").html("");
            $("#warningsapellido").html("");
            $("#warningsemail").html("");
            $("#warningsclave").html("");
        }

        /* FIN ADVERTENCIAS (WARNINGS)*/


    });

});