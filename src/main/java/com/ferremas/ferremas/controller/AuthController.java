package com.ferremas.ferremas.controller;

import com.ferremas.ferremas.dto.AuthResponse;
import com.ferremas.ferremas.dto.LoginRequest;
import com.ferremas.ferremas.dto.RegisterRequest;
import com.ferremas.ferremas.dto.UsuarioResponse;
import com.ferremas.ferremas.exception.RequestException;
import com.ferremas.ferremas.model.Usuario;
import com.ferremas.ferremas.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080"})
public class AuthController {

    private final AuthService authService;

    @GetMapping(value = "profile")
    public ResponseEntity<UsuarioResponse> profile(@RequestHeader(value = "Authorization") String token) {
        token = token.substring(7);
        return ResponseEntity.ok(authService.getUserData(token));
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        checkLoginErrors(request);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        HashMap<String, String> errors = checkRegisterErrors(request);
        return ResponseEntity.ok(authService.register(request, errors));
    }

    private void checkLoginErrors(LoginRequest request) {
        if ((request.getEmail() == null || request.getEmail().isBlank()) && (request.getPassword() == null || request.getPassword().isBlank())) {
            throw new RequestException("Debes ingresar tus credenciales.", HttpStatus.BAD_REQUEST);
        }
        if (request.getEmail() == null || request.getEmail().isBlank())
            throw new RequestException("Debes ingresar tu correo electrónico.", HttpStatus.BAD_REQUEST);
        if (request.getPassword() == null || request.getPassword().isBlank())
            throw new RequestException("Debes ingresar tu contraseña.", HttpStatus.BAD_REQUEST);
    }

    private HashMap<String, String> checkRegisterErrors(RegisterRequest request) {
        HashMap<String, String> errors = new HashMap<>();

        // Verificacion de errores
        if (request.getNombre() == null || request.getNombre().isBlank()) errors.put("nombre", "El nombre no puede estar vacío.");
        if (request.getApellido() == null || request.getApellido().isBlank()) errors.put("apellido", "El apellido no puede estar vacío.");
        if (request.getEmail() == null || request.getEmail().isBlank()) errors.put("email", "El correo electrónico no puede estar vacío.");
        if (request.getDireccion() == null || request.getDireccion().isBlank()) errors.put("direccion", "La dirección no puede estar vacía.");
        if (request.getPassword() == null || request.getPassword().isBlank()) errors.put("password", "La contraseña no puede estar vacía.");
        if (request.getPassword() != null && request.getPasswordConfirmation() != null && !request.getPassword().equals(request.getPasswordConfirmation())) {
            errors.put("passwordConfirmation", "La contraseña no coincide.");
        }
        if (request.getPasswordConfirmation() == null || request.getPasswordConfirmation().isBlank()) errors.put("passwordConfirmation", "La confirmación de contraseña no puede estar vacía.");
        if (!Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matcher(request.getEmail()).matches()) {
            errors.put("email", "El correo electrónico ingresado no es válido.");
        }
        if (!Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$").matcher(request.getPassword()).matches()) {
            errors.put("password", "La contraseña debe tener 6 caracteres y contener números y letras.");
        }

        return errors;
    }
}
