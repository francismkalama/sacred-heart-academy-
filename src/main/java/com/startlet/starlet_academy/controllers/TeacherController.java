package com.startlet.starlet_academy.controllers;

import com.startlet.starlet_academy.models.Institution.Teacher;
import com.startlet.starlet_academy.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.addTeacher(teacher));
    }

    @GetMapping("teacher_list")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int teacherId) {
        return teacherService.getTeacherById(teacherId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/{teacherId}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable int teacherId,@RequestBody Teacher updatedTeacher) {
        Teacher teacher = teacherService.updateTeacher(teacherId, updatedTeacher);
        return ResponseEntity.ok(teacher);
    }
}
