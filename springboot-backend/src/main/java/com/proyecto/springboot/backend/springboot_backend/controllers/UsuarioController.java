package com.proyecto.springboot.backend.springboot_backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.springboot.backend.springboot_backend.entities.Usuario;
import com.proyecto.springboot.backend.springboot_backend.services.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;
    
    @GetMapping
    public List<Usuario> listar() {
        return service.findByAll();
    }
    
    @GetMapping("/{rut}")
    public ResponseEntity<?> verUsuario(@PathVariable Long rut) {
        Optional<Usuario> usuarioOptional = service.findByRut(rut);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody Usuario unUsuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unUsuario));
    }

    @PutMapping("/{rut}")
    public ResponseEntity<?> modificar(@PathVariable Long rut, @RequestBody Usuario unUsuario) {
        Optional<Usuario> usuarioOptional = service.findByRut(rut);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNombre(unUsuario.getNombre());
            usuarioExistente.setCorreo(unUsuario.getCorreo());
            usuarioExistente.setContrasenia(unUsuario.getContrasenia());
            Usuario usuarioModificado = service.save(usuarioExistente);
            return ResponseEntity.ok(usuarioModificado);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminar(@PathVariable Long rut) {
        Usuario unUsuario = new Usuario();
        unUsuario.setRut(rut);
        Optional<Usuario> usuarioOptional = service.delete(unUsuario);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
}
