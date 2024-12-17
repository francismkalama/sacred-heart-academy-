package com.startlet.starlet_academy.controllers;

import com.google.gson.Gson;
import com.startlet.starlet_academy.models.*;
import com.startlet.starlet_academy.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @PostMapping("/add")
    public Student addStudent(@RequestBody StudentDTO studentDTO){
        Student student = new Student();
        logger.info("Received Add students Requests {}",new Gson().toJson(studentDTO));
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setDateOfBirth(new Date(studentDTO.getDateOfBirth())); // Example, convert from timestamp
        student.setGender(studentDTO.getGender());
        student.setAddressStreet(studentDTO.getAddressStreet());
        student.setAddressCity(studentDTO.getAddressCity());
        student.setAddressState(studentDTO.getAddressState());
        student.setAddressPostalCode(studentDTO.getAddressPostalCode());
        List<Parent> parentsData = new ArrayList<>();
        for (ParentDTO parentdto : studentDTO.getParents()) {
            Parent parent = new Parent();
            parent.setName(parentdto.getName());
            parent.setRelationship(parentdto.getRelationship());
            parent.setPhone(parentdto.getPhone());
            logger.info("email {}",parentdto.getEmail());
            parent.setEmail(parentdto.getEmail());
            parentsData.add(parent);
        }
        List<Enrollment> enrollmentData = new ArrayList<>();
        for (EnrollmentDTO enrollmentDTO : studentDTO.getEnrollments()) {
            Enrollment enrollment = new Enrollment();
            enrollment.setClassName(enrollmentDTO.getClassName());
            enrollment.setSection(enrollmentDTO.getSection());
            enrollment.setEnrollmentDate(new Date(enrollmentDTO.getEnrollmentDate())); // Convert from timestamp
            enrollment.setStatus(enrollmentDTO.getStatus());
            enrollmentData.add(enrollment);
        }
        studentService.addStudent(student, parentsData, enrollmentData);
        return student;
    }
    @GetMapping("/{studentId}")
    public StudentDTO getStudent(@PathVariable long studentId) {
        return studentService.getStudentById(studentId);  // Call the service method
    }
    @GetMapping("/student_list")
    public ResponseEntity<Page<Student>> getAllStudents(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("student_id"));
        Page<Student> studentsPage = studentService.getStudentList(pageable);
        return ResponseEntity.ok(studentsPage);
    }
//    @PostMapping("/{studentId}")
//    public ResponseEntity<Student> updateStudent(@PathVariable long studentId,@RequestBody Student student){
//        Student studentData = studentService.updateStudent(studentId,student);
//        return ResponseEntity.ok(studentData);
//    }
@PostMapping("/{studentId}")
public ResponseEntity<Student> updateStudent(@PathVariable long studentId,@RequestBody StudentDTO student){
    Student studentData = studentService.updateStudent(studentId,student);
    return ResponseEntity.ok(studentData);
}
    @GetMapping("/student_count")
    public ResponseEntity<Long> getStudentsCount() {
        long studentsCount = studentService.getStudentCount();
        return ResponseEntity.ok(studentsCount);
    }
}
