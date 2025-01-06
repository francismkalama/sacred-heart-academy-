package com.sacred.sacredheartacademy.controllers;

import com.google.gson.Gson;
import com.sacred.sacredheartacademy.models.*;
import com.sacred.sacredheartacademy.models.Institution.Fees;
import com.sacred.sacredheartacademy.models.Institution.FeesDTO;
import com.sacred.sacredheartacademy.models.dataobjects.MonthlyTransactions;
import com.sacred.sacredheartacademy.services.StudentService;
import com.sacred.sacredheartacademy.utils.NumberConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
//        student.setDateOfBirth(new Date(studentDTO.getDateOfBirth())); // Example, convert from timestamp
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setDateOfAdmission(studentDTO.getDateOfAdmission());
        student.setStudentClass(studentDTO.getStudentsClass());
        student.setTerm(studentDTO.getTerm());
        student.setGender(studentDTO.getGender());
        student.setAddressStreet(studentDTO.getAddressStreet());
        student.setAddressCity(studentDTO.getAddressCity());
        student.setAddressState(studentDTO.getAddressState());
        student.setAddressPostalCode(studentDTO.getAddressPostalCode());
        student.setAdmNo(studentDTO.getAdmNo());
        student.setDateSaved(LocalDateTime.now());
        student.setStudent_status(true);
        List<Parent> parentsData = new ArrayList<>();
        for (ParentDTO parentdto : studentDTO.getParents()) {
            Parent parent = new Parent();
            parent.setName(parentdto.getName());
            parent.setRelationship(parentdto.getRelationship());
            parent.setPhone(parentdto.getPhone());
//            logger.info("email {}",parentdto.getEmail());
            parent.setEmail(parentdto.getEmail());
            parentsData.add(parent);
        }
        List<Enrollment> enrollmentData = new ArrayList<>();
        for (EnrollmentDTO enrollmentDTO : studentDTO.getEnrollments()) {
            Enrollment enrollment = new Enrollment();
            enrollment.setClassName(enrollmentDTO.getClassName());
            enrollment.setSection(enrollmentDTO.getSection());
//            enrollment.setEnrollmentDate(new Date(enrollmentDTO.getEnrollmentDate())); // Convert from timestamp
            enrollment.setEnrollmentDate(enrollmentDTO.getEnrollmentDate());
            enrollment.setStatus(enrollmentDTO.getStatus());
            enrollmentData.add(enrollment);
        }
        List<Fees>feeData = new ArrayList<>();
//        if(studentDTO.getFees()!=null){
            for (FeesDTO feesDTO : studentDTO.getFees()){
//                logger.info(" {}", feesDTO.getExams().add(feesDTO.getComputer()).add(feesDTO.getAssessment()).add(feesDTO.getExtraCurriculum()));
//                BigDecimal paidAmount = Objects.nonNull(feesDTO.getExams())?feesDTO.getExams():new BigDecimal("0.0").add(feesDTO.getComputer()).add(feesDTO.getAssessment())
//                        .add(feesDTO.getExtraCurriculum()).add(feesDTO.getTransport())
//                        .add(feesDTO.getTution()).add(feesDTO.getAdmission()).add(feesDTO.getLunch());
                BigDecimal paidAmount = feesDTO.getExams().add(feesDTO.getComputer()).add(feesDTO.getAssessment())
                        .add(feesDTO.getExtraCurriculum()).add(feesDTO.getTransport())
                        .add(feesDTO.getTution()).add(feesDTO.getAdmission()).add(feesDTO.getLunch());
                logger.info("Paid Amount {}",paidAmount);
                Fees fees = new Fees();
                fees.setExams(feesDTO.getExams());
                fees.setComputer(feesDTO.getComputer());
                fees.setAssessment(feesDTO.getAssessment());
                fees.setOutstandingFees(feesDTO.getFeesAmount().subtract(paidAmount));
                fees.setExtraCurriculum(feesDTO.getExtraCurriculum());
                fees.setTransport(feesDTO.getTransport());
                fees.setTution(feesDTO.getTution());
                fees.setLunch(feesDTO.getLunch());
                fees.setAdmission(feesDTO.getAdmission());
                fees.setFeesAmount(feesDTO.getFeesAmount());
                fees.setTotal(paidAmount);
                fees.setDateSaved(LocalDateTime.now());
//                logger.info("Amount {} in words {}", paidAmount,NumberConversion.convertBigDecimal(paidAmount));
                feeData.add(fees);
            }
//        }
        studentService.addStudent(student, parentsData, enrollmentData,feeData);
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
    @GetMapping("/student_number")
    public ResponseEntity<Page<List<Student>>> getStudentBySearch(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size, @RequestParam String admNo) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("student_id"));
        Page<List<Student>> studentsPage = studentService.getStudentWithAdmNo(pageable,admNo);
        return ResponseEntity.ok(studentsPage);
    }

    @PostMapping("deactivate/{studentId}")
    public ResponseEntity<?> deactivateStudent(@PathVariable long studentId){
        try {
           studentService.deactivateStudent(studentId);
            return new ResponseEntity<>("Successfully deactivated", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity("Unable to deactivate "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
    }
@PostMapping("/{studentId}")
public ResponseEntity<Student> updateStudent(@PathVariable long studentId,@RequestBody StudentDTO student){
    logger.info("Received Update students Requests {}",new Gson().toJson(student));
        Student studentData = studentService.updateStudent(studentId,student);
    return ResponseEntity.ok(studentData);
}
    @GetMapping("/student_count/{monthValue}")
    public ResponseEntity<Long> getStudentsCount(@PathVariable int monthValue) {
        long studentsCount;
        if (monthValue > 0){
            LocalDateTime currentDateTime = LocalDateTime.now();
            studentsCount= studentService.getStudentCountbyMonth(monthValue,currentDateTime.getYear());
        }else{
            studentsCount = studentService.getStudentCount();
        }
        return ResponseEntity.ok(studentsCount);
    }

    @GetMapping("/monthly/{year}")
    public List<MonthlyTransactions> getMonthly(@PathVariable String year) {
        return studentService.getMonthlyTransations(year);  // Call the service method
    }
    @GetMapping("admission/{admNo}")
    public boolean admNumberCheck(@PathVariable String admNo){
        return studentService.checkAdmissionNumber(admNo);

    }
}
