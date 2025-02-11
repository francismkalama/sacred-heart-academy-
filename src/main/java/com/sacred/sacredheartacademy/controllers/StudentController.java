package com.sacred.sacredheartacademy.controllers;

import com.google.gson.Gson;
import com.sacred.sacredheartacademy.models.*;
import com.sacred.sacredheartacademy.models.Institution.Fees;
import com.sacred.sacredheartacademy.models.Institution.FeesDTO;
import com.sacred.sacredheartacademy.models.Institution.FeesHistory;
import com.sacred.sacredheartacademy.models.dataobjects.MonthlyTransactions;
import com.sacred.sacredheartacademy.models.dataobjects.StudentReport;
import com.sacred.sacredheartacademy.repositorys.StudentRepository;
import com.sacred.sacredheartacademy.services.SaveExcelChanges;
import com.sacred.sacredheartacademy.services.StudentService;
import com.sacred.sacredheartacademy.utils.NumberConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    @Autowired
    private SaveExcelChanges saveExcelChanges;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/add")
    public Student addStudent(@RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        logger.info("Received Add students Requests {}", new Gson().toJson(studentDTO));
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
//        student.setAdmNo(studentDTO.getAdmNo());
        long admissionNumber = studentService.generateAdmissionNumber();
        student.setAdmNo(String.valueOf(admissionNumber + 1));
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
        List<Fees> feeData = new ArrayList<>();
//        if(studentDTO.getFees()!=null){
        for (FeesDTO feesDTO : studentDTO.getFees()) {
//                logger.info(" {}", feesDTO.getExams().add(feesDTO.getComputer()).add(feesDTO.getAssessment()).add(feesDTO.getExtraCurriculum()));
//                BigDecimal paidAmount = Objects.nonNull(feesDTO.getExams())?feesDTO.getExams():new BigDecimal("0.0").add(feesDTO.getComputer()).add(feesDTO.getAssessment())
//                        .add(feesDTO.getExtraCurriculum()).add(feesDTO.getTransport())
//                        .add(feesDTO.getTution()).add(feesDTO.getAdmission()).add(feesDTO.getLunch());
            BigDecimal paidAmount = feesDTO.getExams().add(feesDTO.getComputer()).add(feesDTO.getAssessment())
                    .add(feesDTO.getExtraCurriculum()).add(feesDTO.getTransport())
                    .add(feesDTO.getTution()).add(feesDTO.getAdmission()).add(feesDTO.getLunch());
            logger.info("Paid Amount {}", paidAmount);
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
        studentService.addStudent(student, parentsData, enrollmentData, feeData);
        return student;
    }

    @GetMapping("/{studentId}")
    public StudentDTO getStudent(@PathVariable long studentId) {
        return studentService.getStudentById(studentId);  // Call the service method
    }

    @GetMapping("/student_list")
    public ResponseEntity<Page<Student>> getAllStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("student_id"));
        Page<Student> studentsPage = studentService.getStudentList(pageable);
        return ResponseEntity.ok(studentsPage);
    }

    @GetMapping("/student_number")
    public ResponseEntity<Page<List<Student>>> getStudentBySearch(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String admNo) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("student_id"));
        Page<List<Student>> studentsPage = studentService.getStudentWithAdmNo(pageable, admNo);
        return ResponseEntity.ok(studentsPage);
    }

    @PostMapping("deactivate/{studentId}")
    public ResponseEntity<?> deactivateStudent(@PathVariable long studentId) {
        try {
            studentService.deactivateStudent(studentId);
            return new ResponseEntity<>("Successfully deactivated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unable to deactivate " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("upload/student/data")
    public ResponseEntity uploadStudentData(@RequestBody MultipartFile file) {
        try {
            saveExcelChanges.saveStudentData(file);
            return new ResponseEntity<>("Successfully upload students data", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unable to save student data " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/student/report")
    public ResponseEntity getStudentReport() {
        List<StudentReport> students = saveExcelChanges.studentReports();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(outputStream)) {

            // Write headers
            writer.println("DateOfAdmission,DateOfBirth,AddressCity,AddressPostalCode,AddressState,AddressStreet,AdmNo," +
                    "FirstName,Gender,LastName,StudentClass,Term,FeeToBePaid,Admission,Assessment,Computer,Exams," +
                    "ExtraCurriculum,FeesAmount,Lunch,OutstandingFees,Transport,Total,Tuition");

            // Write data rows
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (StudentReport student : students) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        student.getDateOfAdmission() != null ? sdf.format(student.getDateOfAdmission()) : "",
                        student.getDateOfBirth() != null ? sdf.format(student.getDateOfBirth()) : "",
                        student.getAddressCity(),
                        student.getAddressPostalCode(),
                        student.getAddressState(),
                        student.getAddressStreet(),
                        student.getAdmNo(),
                        student.getFirstName(),
                        student.getGender(),
                        student.getLastName(),
                        student.getStudentClass(),
                        student.getTerm(),
                        student.getFeeToBePaid() != null ? student.getFeeToBePaid().toString() : "0.00",
                        student.getAdmission() != null ? student.getAdmission().toString() : "0.00",
                        student.getAssessment() != null ? student.getAssessment().toString() : "0.00",
                        student.getComputer() != null ? student.getComputer().toString() : "0.00",
                        student.getExams() != null ? student.getExams().toString() : "0.00",
                        student.getExtra_curriculum() != null ? student.getExtra_curriculum().toString() : "0.00",
                        student.getFees_amount() != null ? student.getFees_amount().toString() : "0.00",
                        student.getLunch() != null ? student.getLunch().toString() : "0.00",
                        student.getOutstanding_fees() != null ? student.getOutstanding_fees().toString() : "0.00",
                        student.getTransport() != null ? student.getTransport().toString() : "0.00",
                        student.getTotal() != null ? student.getTotal().toString() : "0.00",
                        student.getTuition() != null ? student.getTuition().toString() : "0.00"
                );
            }
            writer.flush();

            // Return CSV as a response
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"students_full_details.csv\"")
                    .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                    .body(outputStream.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable long studentId, @RequestBody StudentDTO student) {
        logger.info("Received Update students Requests {}", new Gson().toJson(student));
        Student studentData = studentService.updateStudent(studentId, student);
        return ResponseEntity.ok(studentData);
    }

    @GetMapping("/student_count/{monthValue}")
    public ResponseEntity<Long> getStudentsCount(@PathVariable int monthValue) {
        long studentsCount;
        if (monthValue > 0) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            studentsCount = studentService.getStudentCountbyMonth(monthValue, currentDateTime.getYear());
        } else {
            studentsCount = studentService.getStudentCount();
        }
        return ResponseEntity.ok(studentsCount);
    }

    @GetMapping("/student_fee_history/{student}")
    public ResponseEntity<Page<FeesHistory>> getStudentsCount(@PathVariable String student, @PageableDefault Pageable pageable) {

        return ResponseEntity.ok(studentService.getStudentFeeHistory(pageable, Long.valueOf(student)));
    }

    @GetMapping("/monthly/{year}")
    public List<MonthlyTransactions> getMonthly(@PathVariable String year) {
        return studentService.getMonthlyTransations(year);  // Call the service method
    }

    @GetMapping("admission/{admNo}")
    public boolean admNumberCheck(@PathVariable String admNo) {
        return studentService.checkAdmissionNumber(admNo);

    }
}
