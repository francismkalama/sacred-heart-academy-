package com.startlet.starlet_academy.services;

import com.google.gson.Gson;
import com.startlet.starlet_academy.models.*;
import com.startlet.starlet_academy.repositorys.EnrollmentRepository;
import com.startlet.starlet_academy.repositorys.ParentRepository;
import com.startlet.starlet_academy.repositorys.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository studentRepository;


    private final ParentRepository parentRepository;
    private final EnrollmentRepository enrollmentRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public StudentService(StudentRepository studentRepository, ParentRepository parentRepository, EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Student  addStudent(Student student, List<Parent> parentsData, List<Enrollment> enrollmentData) {
        Student savedStudent = studentRepository.save(student);
        for (Parent parent : parentsData) {
            parent.setStudent(savedStudent);
            parentRepository.save(parent);
        }
        for (Enrollment enrollment : enrollmentData) {
            enrollment.setStudent(savedStudent);
            enrollmentRepository.save(enrollment);
        }
        return savedStudent;
    }
    public StudentDTO getStudentById(int studentId) {
        StudentDTO studentDTO = new StudentDTO();
        // Use the findById method from the repository to fetch the student
//        Optional<Student> student = studentRepository.findById(studentId);
        Optional<Student> student = studentRepository.findStudentsWithParents(studentId);
        // Check if the student exists
        if (student.isPresent()) {
            Student stude = student.get();
            studentDTO.setFirstName(stude.getFirstName());
            studentDTO.setLastName(stude.getLastName());
            List<Parent> studentParentData = stude.getParents();
            List<ParentDTO> genParentData = new ArrayList<>();
//            studentDTO.setParents(student.get().getParents());
            for (Parent nameData : studentParentData){
                ParentDTO parentDTO = new ParentDTO();
                parentDTO.setName(nameData.getName());
                parentDTO.setPhone(nameData.getPhone());
                parentDTO.setRelationship(nameData.getRelationship());
                parentDTO.setEmail(nameData.getEmail());
                genParentData.add(parentDTO);
            }
            studentDTO.setParents(genParentData);
            return studentDTO;  // Return the student if found
        } else {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }
    }

    public Page<Student> getStudentList(Pageable pageable){
        return studentRepository.findAllStudents(pageable);
    }
}
