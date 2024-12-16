package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.*;
import com.startlet.starlet_academy.models.Institution.Fees;
import com.startlet.starlet_academy.models.Institution.FeesDTO;
import com.startlet.starlet_academy.repositorys.EnrollmentRepository;
import com.startlet.starlet_academy.repositorys.FeesRepository;
import com.startlet.starlet_academy.repositorys.ParentRepository;
import com.startlet.starlet_academy.repositorys.StudentRepository;
import com.startlet.starlet_academy.utils.NumberConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final EnrollmentRepository enrollmentRepository;

    private final FeesRepository feesRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public StudentService(StudentRepository studentRepository, ParentRepository parentRepository, EnrollmentRepository enrollmentRepository, FeesRepository feesRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.feesRepository = feesRepository;
    }
    public Student  addStudent(Student student, List<Parent> parentsData, List<Enrollment> enrollmentData, List<Fees> feeData) {
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
        for (Fees fees : feeData) {
            fees.setStudent(savedStudent);
            try {
                feesRepository.save(fees);
                logger.info("Fees Data saved successfully");
            }catch (Exception e){
                logger.error("Error Saving fees information  {}",e.getMessage());
            }
        }
        return savedStudent;
    }
    public StudentDTO getStudentById(int studentId) {
        StudentDTO studentDTO = new StudentDTO();
   Student results = studentRepository.findStudentsWithParents(studentId);
   studentDTO.setFirstName(results.getFirstName());
   studentDTO.setLastName(results.getLastName());
   studentDTO.setAddressState(results.getAddressState());
   studentDTO.setAddressCity(results.getAddressCity());
   studentDTO.setAddressPostalCode(results.getAddressPostalCode());
   studentDTO.setAddressStreet(results.getAddressStreet());
   studentDTO.setAdmNo(results.getAdmNo());
   studentDTO.setGender(results.getGender());
   studentDTO.setDateOfBirth(results.getDateOfBirth());
   studentDTO.setDateOfAdmission(results.getDateOfAdmission());
   studentDTO.setStudentsClass(results.getStudentClass());
   studentDTO.setTerm(results.getTerm());
   List<ParentDTO> rsParentDataSet = new ArrayList<>();
   List<FeesDTO> rsFeeDataSet = new ArrayList<>();
   List<Parent> rsParent = results.getParents();
    List<Fees> rsFees = results.getFees();
    for (Parent parent: rsParent){
        ParentDTO parentDTO = new ParentDTO();
        parentDTO.setEmail(parent.getEmail());
        parentDTO.setRelationship(parent.getRelationship());
        parentDTO.setPhone(parent.getPhone());
        parentDTO.setName(parent.getName());
        rsParentDataSet.add(parentDTO);
    }
    for (Fees fer: rsFees){
        FeesDTO feesDTO = new FeesDTO();
        feesDTO.setFeesAmount(fer.getFeesAmount());
        feesDTO.setComputer(fer.getComputer());
        feesDTO.setAssessment(fer.getAssessment());
        feesDTO.setExams(fer.getExams());
        feesDTO.setLunch(fer.getLunch());
        feesDTO.setOutstandingFees(fer.getOutstandingFees());
        feesDTO.setTransport(fer.getTransport());
        feesDTO.setTution(fer.getTution());
        feesDTO.setAdmission(fer.getAdmission());
        feesDTO.setExtraCurriculim(fer.getExtraCurriculum());
        feesDTO.setAmountDesc(NumberConversion.convertBigDecimal(fer.getTotal()));
        rsFeeDataSet.add(feesDTO);
         }
    studentDTO.setParents(rsParentDataSet);
    studentDTO.setFees(rsFeeDataSet);

        // Check if the student exists

//        if (student.isPresent()) {
//            StudentDTOResponse stude = student.get();
//            studentDTO.setFirstName(stude.getFirst_name());
//            studentDTO.setLastName(stude.getLast_name());
////            List<Parent> studentParentData = stude.getParents();
//            List<ParentDTO> genParentData = new ArrayList<>();
//            for (Parent nameData : studentParentData){
//                ParentDTO parentDTO = new ParentDTO();
//                parentDTO.setName(nameData.getName());
//                parentDTO.setPhone(nameData.getPhone());
//                parentDTO.setRelationship(nameData.getRelationship());
//                parentDTO.setEmail(nameData.getEmail());
//                genParentData.add(parentDTO);
//            }
//            studentDTO.setParents(genParentData);
//            return studentDTO;  // Return the student if found
//        } else {
//            logger.error("Student not found with ID: " + studentId);
//            return studentDTO;
//        }
        return studentDTO;
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
