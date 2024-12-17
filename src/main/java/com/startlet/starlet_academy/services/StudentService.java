package com.startlet.starlet_academy.services;

import com.startlet.starlet_academy.models.*;
import com.startlet.starlet_academy.models.Institution.Fees;
import com.startlet.starlet_academy.models.Institution.FeesDTO;
import com.startlet.starlet_academy.models.Institution.FeesHistory;
import com.startlet.starlet_academy.models.dataobjects.MonthlyTransactions;
import com.startlet.starlet_academy.repositorys.*;
import com.startlet.starlet_academy.utils.NumberConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final EnrollmentRepository enrollmentRepository;

    private final FeesRepository feesRepository;
    private final FeesHistoryRepository feesHistoryRepository;
    private final MonthlyTransactionsRepository monthlyTransactionsRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public StudentService(StudentRepository studentRepository, ParentRepository parentRepository, EnrollmentRepository enrollmentRepository, FeesRepository feesRepository, FeesHistoryRepository feesHistoryRepository, MonthlyTransactionsRepository monthlyTransactionsRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.feesRepository = feesRepository;
        this.feesHistoryRepository = feesHistoryRepository;
        this.monthlyTransactionsRepository = monthlyTransactionsRepository;
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
            FeesHistory history = new FeesHistory();
            try {
                BeanUtils.copyProperties(fees, history);
                history.setDateSaved(LocalDateTime.now());
                history.setUpdatedDate(LocalDateTime.now());
                feesRepository.save(fees);
                feesHistoryRepository.save(history);
                logger.info("Fees Data saved successfully");
            }catch (Exception e){
                logger.error("Error Saving fees information  {}",e.getMessage());
            }
        }
        return savedStudent;
    }
    public StudentDTO getStudentById(long studentId) {
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
        BigDecimal paidAmount = fer.getExams().add(fer.getComputer()).add(fer.getAssessment())
                .add(fer.getExtraCurriculum()).add(fer.getTransport())
                .add(fer.getTution()).add(fer.getAdmission()).add(fer.getLunch());
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
        feesDTO.setExtraCurriculum(fer.getExtraCurriculum());
        feesDTO.setPaidAmount(paidAmount);
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

//    public Student updateStudent(long studentId, Student student) {
////        findStudentsWithParents
////        Student existingStudent = studentRepository.findById(studentId)        .orElseThrow(() -> new RuntimeException("Student not found"));
//        Student existingStudent = studentRepository.findStudentsWithParents(studentId);
//
//        existingStudent.setFirstName(student.getFirstName());
//        existingStudent.setLastName(student.getLastName());
//        existingStudent.setDateOfBirth(student.getDateOfBirth());
//        existingStudent.setDateOfAdmission(student.getDateOfAdmission());
//        existingStudent.setGender(student.getGender());
//        existingStudent.setAddressCity(student.getAddressCity());
//        existingStudent.setAddressState(student.getAddressState());
//        existingStudent.setAddressPostalCode(student.getAddressPostalCode());
//        existingStudent.setAddressStreet(student.getAddressStreet());
//        existingStudent.setStudentClass(student.getStudentClass());
//        existingStudent.setTerm(student.getTerm());
//        List<Parent> parentsList = student.getParents();
//        logger.info("Parent List Size {}",parentsList.size());
//        List<Parent> updatedParents = new ArrayList<>();
//        if(parentsList != null && !parentsList.isEmpty()){
//            logger.info("inside parent list");
//            for (Parent parent:parentsList){
//                Parent parentObj = existingStudent.getParents()
//                        .stream()
//                        .filter(p -> p.getParentId() == parent.getParentId()) // Check for existing parent by ID
//                        .findFirst()
//                        .orElse(new Parent());
//                parentObj.setName(parent.getName());
//                updatedParents.add(parentObj);
//
//            }
//
//        }
//
//        if (student.getParents()!=null){
//            List<Parent> parentsData = student.getParents().stream()
//                    .map(prnt -> parentRepository.findById(prnt.getParentId())
//                    .orElseThrow(() -> new RuntimeException("Parent not found: " + prnt.getParentId())))
//                    .collect(Collectors.toList());
//
//        }
//        if (student.getEnrollments() != null){
//            List<Enrollment> enrollmentsData = student.getEnrollments().stream()
//                            .map(enrollment -> enrollmentRepository.findById(enrollment.getEnrollmentId())
//                                    .orElseThrow(() -> new RuntimeException("Parent not found: " + enrollment.getEnrollmentId())))
//                    .collect(Collectors.toList());
//        }
//        existingStudent.setParents(updatedParents);
//
//        return studentRepository.save(existingStudent);
//    }
public Student updateStudent(long studentId, StudentDTO student) {
//        findStudentsWithParents
//        Student existingStudent = studentRepository.findById(studentId)        .orElseThrow(() -> new RuntimeException("Student not found"));
    Student existingStudent = studentRepository.findStudentsWithParents(studentId);

    existingStudent.setFirstName(student.getFirstName());
    existingStudent.setLastName(student.getLastName());
    existingStudent.setDateOfBirth(student.getDateOfBirth());
    existingStudent.setDateOfAdmission(student.getDateOfAdmission());
    existingStudent.setGender(student.getGender());
    existingStudent.setAddressCity(student.getAddressCity());
    existingStudent.setAddressState(student.getAddressState());
    existingStudent.setAddressPostalCode(student.getAddressPostalCode());
    existingStudent.setAddressStreet(student.getAddressStreet());
    existingStudent.setStudentClass(student.getStudentsClass());
    existingStudent.setTerm(student.getTerm());
    List<ParentDTO> parentsList = student.getParents();
    List<FeesDTO>feesList = student.getFees();
    List<Parent> updatedParents = new ArrayList<>();
    List<Fees>updatedFees = new ArrayList<>();
    if(parentsList != null && !parentsList.isEmpty()){
        for (ParentDTO parent:parentsList){
            Parent parentObj = existingStudent.getParents()
                    .stream()
                    .filter(p -> p.getStudent().getId() == studentId) // Check for existing parent by ID
                    .findFirst()
                    .orElse(new Parent());
            if (parentObj.getEmail().equals(parent.getEmail())){
                parentObj.setName(parent.getName());
                parentObj.setPhone(parent.getPhone());
                parentObj.setRelationship(parent.getRelationship());
            }else {
                parentObj.setEmail(parent.getEmail());
                parentObj.setName(parent.getName());
                parentObj.setPhone(parent.getPhone());
                parentObj.setRelationship(parent.getRelationship());
            }
            updatedParents.add(parentObj);

        }

    }
    if(feesList != null && !feesList.isEmpty()){
        for (FeesDTO feesDTO:feesList){
            BigDecimal empyValue = BigDecimal.ZERO;
            BigDecimal paidAmount = Objects.nonNull(feesDTO.getExams())?feesDTO.getExams():empyValue.
                    add(Objects.nonNull(feesDTO.getComputer())?feesDTO.getComputer():empyValue).
                    add(Objects.nonNull(feesDTO.getAssessment())?feesDTO.getAssessment():empyValue)
                    .add(Objects.nonNull(feesDTO.getExtraCurriculum())?feesDTO.getExtraCurriculum():empyValue)
                    .add(Objects.nonNull(feesDTO.getTransport())?feesDTO.getTransport():empyValue)
                    .add(Objects.nonNull(feesDTO.getTution())?feesDTO.getTution():empyValue).
                    add(Objects.nonNull(feesDTO.getAdmission())?feesDTO.getAdmission():empyValue)
                    .add(Objects.nonNull(feesDTO.getLunch())?feesDTO.getLunch():empyValue);
            Fees fees = existingStudent.getFees()
                    .stream()
                    .filter(f -> f.getStudent().getId() == studentId) // Check for existing parent by ID
                    .findFirst()
                    .orElse(new Fees());
            fees.setExtraCurriculum(feesDTO.getExtraCurriculum());
            fees.setAdmission(feesDTO.getAdmission());
            fees.setAssessment(feesDTO.getAssessment());
            fees.setLunch(feesDTO.getLunch());
            fees.setTution(feesDTO.getTution());
            fees.setFeesAmount(feesDTO.getFeesAmount());
            fees.setExams(feesDTO.getExams());
            fees.setComputer(feesDTO.getComputer());
            fees.setOutstandingFees(feesDTO.getFeesAmount().subtract(paidAmount));
            fees.setTransport(feesDTO.getTransport());
            fees.setTotal(paidAmount);
            fees.setUpdatedDate(LocalDateTime.now());
            updatedFees.add(fees);
            saveHistory(fees);

        }

    }
//
//    if (student.getParents()!=null){
//        List<Parent> parentsData = student.getParents().stream()
//                .map(prnt -> parentRepository.findById(prnt.getParentId())
//                        .orElseThrow(() -> new RuntimeException("Parent not found: " + prnt.getParentId())))
//                .collect(Collectors.toList());
//
//    }
//    if (student.getEnrollments() != null){
//        List<Enrollment> enrollmentsData = student.getEnrollments().stream()
//                .map(enrollment -> enrollmentRepository.findById(enrollment.getEnrollmentId())
//                        .orElseThrow(() -> new RuntimeException("Parent not found: " + enrollment.getEnrollmentId())))
//                .collect(Collectors.toList());
//    }
    existingStudent.setParents(updatedParents);
    existingStudent.setFees(updatedFees);

    return studentRepository.save(existingStudent);
}

    private void saveHistory(Fees fee) {
        FeesHistory history = new FeesHistory();
        history.setExtraCurriculum(fee.getExtraCurriculum());
        history.setAdmission(fee.getAdmission());
        history.setAssessment(fee.getAssessment());
        history.setLunch(fee.getLunch());
        history.setTution(fee.getTution());
        history.setFeesAmount(fee.getFeesAmount());
        history.setExams(fee.getExams());
        history.setComputer(fee.getComputer());
        history.setOutstandingFees(fee.getOutstandingFees());
        history.setTransport(fee.getTransport());
        history.setTotal(fee.getTotal());
        history.setStudent(fee.getStudent());
        history.setUpdatedDate(LocalDateTime.now());
        history.setDateSaved(LocalDateTime.now());
        feesHistoryRepository.save(history);
    }

    public long getStudentCount() {
        return studentRepository.count();
    }

    public long getStudentCountbyMonth(int monthValue, int year) {
        return studentRepository.countStudentByAdmMonth(monthValue,year);
    }

    public List<MonthlyTransactions> getMonthlyTransations(){
        return monthlyTransactionsRepository.findAll();
    }
}
