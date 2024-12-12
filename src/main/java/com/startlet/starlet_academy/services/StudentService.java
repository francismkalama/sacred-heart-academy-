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
import java.util.stream.Collectors;

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
            try{
                parentRepository.save(parent);
                logger.info("Parent Data saved successfully");
            }catch (Exception e){
                logger.error("Error Saving parent {}",e.getMessage());
            }
        }
        for (Enrollment enrollment : enrollmentData) {
            enrollment.setStudent(savedStudent);
            try {
                enrollmentRepository.save(enrollment);
                logger.info("Enrollment Data saved successfully");
            }catch (Exception e){
                logger.error("Error Saving enrollment  {}",e.getMessage());
            }
        }
        return savedStudent;
    }
    public StudentDTO getStudentById(int studentId) {
        StudentDTO studentDTO = new StudentDTO();
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
            logger.error("Student not found with ID: " + studentId);
//            throw new RuntimeException("Student not found with ID: " + studentId);
            return studentDTO;
        }
    }

    public Page<Student> getStudentList(Pageable pageable){
        return studentRepository.findAllStudents(pageable);
    }

    public Student updateStudent(long studentId, Student student) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setGender(student.getGender());
        existingStudent.setAddressCity(student.getAddressCity());
        existingStudent.setAddressState(student.getAddressState());
        existingStudent.setAddressPostalCode(student.getAddressPostalCode());
        existingStudent.setAddressStreet(student.getAddressStreet());

        List<Parent> parentsList = student.getParents();
        if(parentsList != null && !parentsList.isEmpty()){
            List<Parent> updatedParents = new ArrayList<>();
            for (Parent parent:parentsList){
                Parent parentObj = student.getParents()
                        .stream()
                        .filter(p -> p.getParentId() == parent.getParentId()) // Check for existing parent by ID
                        .findFirst()
                        .orElse(new Parent());
                parentObj.setName(parent.getName());

            }

        }

        if (student.getParents()!=null){
            List<Parent> parentsData = student.getParents().stream()
                    .map(prnt -> parentRepository.findById(prnt.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent not found: " + prnt.getParentId())))
                    .collect(Collectors.toList());

        }
        if (student.getEnrollments() != null){
            List<Enrollment> enrollmentsData = student.getEnrollments().stream()
                            .map(enrollment -> enrollmentRepository.findById(enrollment.getEnrollmentId())
                                    .orElseThrow(() -> new RuntimeException("Parent not found: " + enrollment.getEnrollmentId())))
                    .collect(Collectors.toList());
        }

        return studentRepository.save(existingStudent);
    }

    public long getStudentCount() {
        return studentRepository.count();
    }
}
