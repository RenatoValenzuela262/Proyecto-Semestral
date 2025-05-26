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

import com.proyecto.springboot.backend.springboot_backend.entities.Incidencia;
import com.proyecto.springboot.backend.springboot_backend.services.IncidenciaService;

@RestController
@RequestMapping("api/incidencia")

public class IncidenciaController {

    @Autowired
    private IncidenciaService service;

    @GetMapping
    public List<Incidencia> List(){
        return service.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verIncidencia(@PathVariable Long id){
        Optional<Incidencia> incidenciaOptional = service.findById(id);
        if (incidenciaOptional.isPresent()){
            return ResponseEntity.ok(incidenciaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Incidencia> registrar(@RequestBody Incidencia unaIncidencia){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(unaIncidencia));
    }

    @PutMapping
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Incidencia unaIncidencia){
        Optional<Incidencia> incidenciaOptional = service.findById(id);
        if (incidenciaOptional.isPresent()){
            Incidencia incidenciaexistente = incidenciaOptional.get();
            incidenciaexistente.setDescripcion(unaIncidencia.getDescripcion());
            incidenciaexistente.setEstado(unaIncidencia.getEstado());
            incidenciaexistente.setPrioridad(unaIncidencia.getPrioridad());
            Incidencia incidenciamodificada = service.save(incidenciaexistente);
            return ResponseEntity.ok(incidenciamodificada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Incidencia unaIncidencia = new Incidencia();
        unaIncidencia.setId(id);
        Optional<Incidencia> incidenciaOptional = service.delete(unaIncidencia);
        if (incidenciaOptional.isPresent()){
            return ResponseEntity.ok(incidenciaOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }



}
