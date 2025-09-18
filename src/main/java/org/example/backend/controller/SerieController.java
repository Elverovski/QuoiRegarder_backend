package org.example.backend.controller;

import org.example.backend.models.Serie;
import org.example.backend.service.SerieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serie")
@CrossOrigin
public class SerieController {

    public final SerieService service;


    public SerieController(SerieService service) {
        this.service = service;
    }

    @GetMapping("/getAllSeries")
    public List<Serie> getAllSeries(){
        return service.findAllSeries();
    }

    @PostMapping("/createSerie")
    public Serie createSerie(@RequestBody Serie newSerie){
        return service.createSerie(newSerie);
    }

    @PutMapping("/updateSerie/{id}")
    public Serie updateSerie(@RequestBody Serie newOne, @PathVariable Long id){
        return service.updateSerie(newOne, id);
    }
    @DeleteMapping("/deleteSerie/{id}")
    public void deleteSerie(@PathVariable Long id){
        service.deleteSerie(id);
    }
}

