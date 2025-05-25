package com.proyecto.springboot.backend.springboot_backend.services;

import java.util.List;
import java.util.Optional;

import com.proyecto.springboot.backend.springboot_backend.entities.Usuario;

public interface UsuarioService {

    List<Usuario> findByAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuarioexistente);

    Optional<Usuario> delete(Usuario unUsuario);
}
