package com.ferremas.ferremas.service;

import com.ferremas.ferremas.dto.AuthResponse;
import com.ferremas.ferremas.dto.LoginRequest;
import com.ferremas.ferremas.dto.RegisterRequest;
import com.ferremas.ferremas.dto.UsuarioResponse;
import com.ferremas.ferremas.exception.RequestException;
import com.ferremas.ferremas.model.Role;
import com.ferremas.ferremas.model.Usuario;
import com.ferremas.ferremas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UsuarioResponse getUserData(String token) {
        Integer userId = jwtService.getSubjectFromToken(token);
        Optional<Usuario> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RequestException("El usuario ingresado no existe.", HttpStatus.UNAUTHORIZED);
        }

        Usuario user = userOpt.get();
        return new UsuarioResponse(
                user.getId(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail(),
                user.getDireccion(),
                user.getRole()
        );
    }

    public AuthResponse register(RegisterRequest request, HashMap<String, String> errors) {
        Optional<Usuario> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) errors.put("email", "El usuario con el correo " + request.getEmail() + " ya existe.");
        if (!errors.isEmpty()) {
            throw new RequestException("Ocurrieron errores al intentar registrarse.", HttpStatus.BAD_REQUEST, errors);
        }

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USUARIO)
                .direccion(request.getDireccion())
                .build();

        userRepository.save(usuario);
        String token = jwtService.getToken(usuario);

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);

        return new AuthResponse(token);
    }
}
