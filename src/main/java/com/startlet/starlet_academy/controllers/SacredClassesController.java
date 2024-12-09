package com.startlet.starlet_academy.controllers;

import com.startlet.starlet_academy.models.Institution.SacredClass;
import com.startlet.starlet_academy.services.SacredClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/classes")
public class SacredClassesController {
    @Autowired
    private SacredClassesService sacredClassesService;
    @PostMapping("create")
    public ResponseEntity<SacredClass> addClass(@RequestBody SacredClass classEntity) {
        return ResponseEntity.ok(sacredClassesService.addClass(classEntity));
    }    @GetMapping
    public ResponseEntity<List<SacredClass>> getAllSacredClasses() {
        return ResponseEntity.ok(sacredClassesService.getAllClasses());
    }
    @GetMapping("/{classId}")
    public ResponseEntity<SacredClass> getClassById(@PathVariable int classId) {
        return sacredClassesService.getClassById(classId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
