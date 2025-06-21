package com.proyecto.springboot.backend.springboot_backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.springboot.backend.springboot_backend.entities.Contenido;
import com.proyecto.springboot.backend.springboot_backend.services.ContenidoService;



@RestController
@RequestMapping("api/contenido")
public class ContenidoController {

    @Autowired
    private ContenidoService service;

    @GetMapping
    public List<Contenido> List(){
        return service.findByAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> verContenido(@PathVariable Long id){
        Optional<Contenido> contenidoOptional = service.findById(id);
        if (contenidoOptional.isPresent()){
            return ResponseEntity.ok(contenidoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Contenido> crear(@RequestBody Contenido unContenido){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unContenido));
    }

    @PutMapping
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Contenido unContenido){
        Optional<Contenido> contenidoOptional = service.findById(id);
        if (contenidoOptional.isPresent()){
            Contenido contenidoexistente = contenidoOptional.get();
            contenidoexistente.setNombre(unContenido.getNombre());
            contenidoexistente.setTipo(unContenido.getTipo());
            Contenido contenidomodificado = service.save(contenidoexistente);
            return ResponseEntity.ok(contenidomodificado);
        }
        return ResponseEntity.notFound().build();

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Contenido unContenido = new Contenido();
        unContenido.setId(id);
        Optional<Contenido> contenidoOptional = service.delete(unContenido);
        if(contenidoOptional.isPresent()){
            return ResponseEntity.ok(contenidoOptional.orElseThrow());
            
        }
        return ResponseEntity.notFound().build();
    }


}
