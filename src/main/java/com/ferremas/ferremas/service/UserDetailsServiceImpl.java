package com.ferremas.ferremas.service;

import com.ferremas.ferremas.exception.RequestException;
import com.ferremas.ferremas.model.Usuario;
import com.ferremas.ferremas.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> userOpt = repository.findById(Integer.decode(username));
        if (userOpt.isEmpty()) {
            throw new RequestException("El usuario no existe.", HttpStatus.BAD_REQUEST);
        }
        Usuario user = userOpt.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}