package com.startlet.starlet_academy.controllers;

import com.startlet.starlet_academy.models.Institution.Course;
import com.startlet.starlet_academy.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("create")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.addCourse(course));
    }

    @GetMapping("course_list")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getCoursesByTeacherId(@PathVariable int teacherId) {
        return ResponseEntity.ok(courseService.getCoursesByTeacherId(teacherId));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Course>> getCoursesByClassId(@PathVariable int classId) {
        return ResponseEntity.ok(courseService.getCoursesByClassId(classId));
    }
}
