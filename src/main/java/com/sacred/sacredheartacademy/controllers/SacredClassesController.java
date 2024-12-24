package com.sacred.sacredheartacademy.controllers;

import com.sacred.sacredheartacademy.models.Institution.SacredClass;
import com.sacred.sacredheartacademy.services.SacredClassesService;
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
    }    @GetMapping("class_list")
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
