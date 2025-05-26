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

import com.proyecto.springboot.backend.springboot_backend.entities.Curso;
import com.proyecto.springboot.backend.springboot_backend.services.CursoService;

@RestController
@RequestMapping("api/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public List<Curso> List(){
        return service.findByAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> verCurso(@PathVariable Long id){
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()){
            return ResponseEntity.ok(cursoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso unCurso){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unCurso));
    }

    @PutMapping
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Curso unCurso){
        Optional<Curso> cursoOptional = service.findById(id);
        if (cursoOptional.isPresent()){
            Curso cursoexistente = cursoOptional.get();
            cursoexistente.setTitulo(unCurso.getTitulo());
            cursoexistente.setDescripcion(unCurso.getDescripcion());
            cursoexistente.setPublicado(unCurso.getPublicado());
            Curso cursomodificado = service.save(cursoexistente);
            return ResponseEntity.ok(cursomodificado);
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Curso unCurso = new Curso();
        unCurso.setId(id);
        Optional<Curso> cursoOptional = service.delete(unCurso);
        if(cursoOptional.isPresent()){
            return ResponseEntity.ok(cursoOptional.orElseThrow());
            
        }
        return ResponseEntity.notFound().build();
    }

}
