package com.sacred.sacredheartacademy.services;

import com.sacred.sacredheartacademy.models.*;
import com.sacred.sacredheartacademy.models.Institution.Fees;
import com.sacred.sacredheartacademy.models.Institution.FeesDTO;
import com.sacred.sacredheartacademy.models.Institution.FeesHistory;
import com.sacred.sacredheartacademy.models.dataobjects.MonthlyTransactions;
import com.sacred.sacredheartacademy.repositorys.*;
import com.sacred.sacredheartacademy.utils.NumberConversion;
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

    public Student addStudent(Student student, List<Parent> parentsData, List<Enrollment> enrollmentData, List<Fees> feeData) {
        Student savedStudent = studentRepository.save(student);
        for (Parent parent : parentsData) {
            parent.setStudent(savedStudent);
            try {
                parentRepository.save(parent);
                logger.info("Parent Data saved successfully");
            } catch (Exception e) {
                logger.error("Error Saving parent {}", e.getMessage());
            }
        }
        for (Enrollment enrollment : enrollmentData) {
            enrollment.setStudent(savedStudent);
            try {
                enrollmentRepository.save(enrollment);
                logger.info("Enrollment Data saved successfully");
            } catch (Exception e) {
                logger.error("Error Saving enrollment  {}", e.getMessage());
            }
        }
        for (Fees fees : feeData) {
            fees.setStudent(savedStudent);
            FeesHistory history = new FeesHistory();
            try {
                BeanUtils.copyProperties(fees, history);
                history.setDateSaved(LocalDateTime.now());
                history.setUpdatedDate(LocalDateTime.now());
                history.setStudentClass(student.getStudentClass());
                history.setTerm(student.getTerm());
                feesRepository.save(fees);
                feesHistoryRepository.save(history);
                logger.info("Fees Data saved successfully");
            } catch (Exception e) {
                logger.error("Error Saving fees information  {}", e.getMessage());
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
        studentDTO.setExtraCuricullum(results.getExtraCuricullum());
        studentDTO.setTransportCharge(results.getTransportCharge());
        studentDTO.setTransportRoute(results.getTransportRoute());
        List<ParentDTO> rsParentDataSet = new ArrayList<>();
        List<FeesDTO> rsFeeDataSet = new ArrayList<>();
        List<Parent> rsParent = results.getParents();
        List<Fees> rsFees = results.getFees();
        for (Parent parent : rsParent) {
            ParentDTO parentDTO = new ParentDTO();
            parentDTO.setEmail(parent.getEmail());
            parentDTO.setRelationship(parent.getRelationship());
            parentDTO.setPhone(parent.getPhone());
            parentDTO.setName(parent.getName());
            rsParentDataSet.add(parentDTO);
        }
        for (Fees fer : rsFees) {
            BigDecimal paidAmount = safeValue(fer.getExams())
                    .add(safeValue(fer.getComputer()))
                    .add(safeValue(fer.getAssessment()))
                    .add(safeValue(fer.getExtraCurriculum()))
                    .add(safeValue(fer.getTransport()))
                    .add(safeValue(fer.getTution()))
                    .add(safeValue(fer.getAdmission()))
                    .add(safeValue(fer.getLunch()));
            FeesDTO feesDTO = new FeesDTO();
            feesDTO.setFeesAmount(fer.getFeesAmount());
            feesDTO.setComputer(fer.getComputer());
            feesDTO.setAssessment(fer.getAssessment());
            feesDTO.setExams(fer.getExams());
            feesDTO.setLunch(fer.getLunch());
//        feesDTO.setOutstandingFees(fer.getFeesAmount().subtract(paidAmount));
            feesDTO.setOutstandingFees(fer.getOutstandingFees());
            feesDTO.setTransport(fer.getTransport());
            feesDTO.setTution(fer.getTution());
            feesDTO.setAdmission(fer.getAdmission());
            feesDTO.setExtraCurriculum(fer.getExtraCurriculum());
            feesDTO.setPaidAmount(paidAmount);
            feesDTO.setAmountDesc(NumberConversion.convertBigDecimal(paidAmount) + " only");
            rsFeeDataSet.add(feesDTO);
        }
        studentDTO.setParents(rsParentDataSet);
        studentDTO.setFees(rsFeeDataSet);
        return studentDTO;
    }

    public Page<Student> getStudentList(Pageable pageable) {
        return studentRepository.findAllStudents(pageable);
    }

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
        existingStudent.setUpdatedDate(LocalDateTime.now());
        existingStudent.setExtraCuricullum(student.getExtraCuricullum());
        existingStudent.setTransportCharge(student.getTransportCharge());
        existingStudent.setTransportRoute(student.getTransportRoute());
        List<ParentDTO> parentsList = student.getParents();
        List<FeesDTO> feesList = student.getFees();
        List<Parent> updatedParents = new ArrayList<>();
        List<Fees> updatedFees = new ArrayList<>();
        if (parentsList != null && !parentsList.isEmpty()) {
            for (ParentDTO parent : parentsList) {
                Parent parentObj = existingStudent.getParents()
                        .stream()
                        .filter(p -> p.getStudent().getId() == studentId) // Check for existing parent by ID
                        .findFirst()
                        .orElse(new Parent());
                if (parentObj.getEmail().equals(parent.getEmail())) {
                    parentObj.setName(parent.getName());
                    parentObj.setPhone(parent.getPhone());
                    parentObj.setRelationship(parent.getRelationship());
                } else {
                    parentObj.setEmail(parent.getEmail());
                    parentObj.setName(parent.getName());
                    parentObj.setPhone(parent.getPhone());
                    parentObj.setRelationship(parent.getRelationship());
                }
                updatedParents.add(parentObj);

            }

        }
        if (feesList != null && !feesList.isEmpty()) {
            for (FeesDTO feesDTO : feesList) {
                BigDecimal paidAmount = feesDTO.getExams().add(feesDTO.getComputer()).add(feesDTO.getAssessment())
                        .add(feesDTO.getExtraCurriculum()).add(feesDTO.getTransport())
                        .add(feesDTO.getTution()).add(feesDTO.getAdmission()).add(feesDTO.getLunch());
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

                if (feesDTO.getOutstandingFees().compareTo(BigDecimal.ZERO) == 0) {
                    logger.info("Balance before  zero {}", feesDTO.getOutstandingFees());
                    fees.setOutstandingFees(feesDTO.getFeesAmount().subtract(paidAmount));
                    logger.info("Balance After {}", fees.getOutstandingFees());
                } else if (feesDTO.getOutstandingFees().compareTo(BigDecimal.ZERO) == -1) {
                    logger.info("Balance less than  zero {}", feesDTO.getOutstandingFees());
                    fees.setOutstandingFees(feesDTO.getOutstandingFees().add(paidAmount));
                    logger.info("Balance After {}", fees.getOutstandingFees());
                } else {
                    logger.info("Balance greater that  zero {}", feesDTO.getOutstandingFees());
                    fees.setOutstandingFees(feesDTO.getFeesAmount().subtract(paidAmount));
                    logger.info("Balance After {}", fees.getOutstandingFees());


//            if(feesDTO.getOutstandingFees().compareTo(BigDecimal.ZERO) == 0){
//                fees.setOutstandingFees(feesDTO.getFeesAmount().subtract(paidAmount));
//            }else{
////                logger.info("Values supplied {} {}",student.getStudentsClass(),student.getTerm());
////                logger.info("Values from DB {} {}",existingStudent.getStudentClass(),existingStudent.getTerm());
//                if(((student.getStudentsClass().toLowerCase() ==existingStudent.getStudentClass().toLowerCase()) &&(student.getTerm()==existingStudent.getTerm())) &&((feesDTO.getTransport().compareTo(BigDecimal.ZERO)==0) ||(feesDTO.getExtraCurriculum().compareTo(BigDecimal.ZERO)==0))){
////                    fees.setOutstandingFees(feesDTO.getFeesAmount().subtract(paidAmount));
//                    logger.info("Kwa same with same term");
//                    fees.setOutstandingFees(feesDTO.getOutstandingFees().subtract(paidAmount));
//                } else if ((student.getStudentsClass().toLowerCase() ==existingStudent.getStudentClass().toLowerCase()) &&(student.getTerm()==existingStudent.getTerm())&&((feesDTO.getTransport().compareTo(BigDecimal.ZERO)>0) ||(feesDTO.getExtraCurriculum().compareTo(BigDecimal.ZERO)>0))) {
//                    logger.info("Kwa same with transaport");
//                    fees.setOutstandingFees(feesDTO.getFeesAmount().subtract(paidAmount));
//                } else{
//                    logger.info("Kwa not same term");
//                    fees.setOutstandingFees(feesDTO.getOutstandingFees().add(feesDTO.getFeesAmount().subtract(paidAmount)));
//                }


                    logger.info("Outstanding Balance {}", feesDTO.getOutstandingFees());
                }
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
        history.setTerm(fee.getStudent().getTerm());
        history.setStudentClass(fee.getStudent().getStudentClass());
        history.setUpdatedDate(LocalDateTime.now());
        history.setDateSaved(LocalDateTime.now());
        feesHistoryRepository.save(history);
    }

    public long getStudentCount() {
        return studentRepository.count();
    }

    public long getStudentCountbyMonth(int monthValue, int year) {
        return studentRepository.countStudentByAdmMonth(monthValue, year);
    }

    public List<MonthlyTransactions> getMonthlyTransations(String year) {
        return monthlyTransactionsRepository.findByYear(Long.valueOf(year));
    }

    public Page<List<Student>> getStudentWithAdmNo(Pageable pageable, String admNo) {
        return studentRepository.findStudentBySearchIgnoreCase(pageable, admNo);
    }

    public Page<FeesHistory> getStudentFeeHistory(Pageable pageable, Long id) {
        return feesHistoryRepository.findByStudent_Id(id, pageable);
    }

    public boolean checkAdmissionNumber(String admissionNumber) {
        return studentRepository.checkExistingStudent(admissionNumber);
    }

    public Student deactivateStudent(long studentId) {
        Student existingStudent = studentRepository.findStudentsWithParents(studentId);
        existingStudent.setStudent_status(false);
        studentRepository.save(existingStudent);
        return existingStudent;
    }

    public long generateAdmissionNumber() {
        long currentAdmValue = studentRepository.getMaxAdmissionValue();
        logger.info("Adm Number for new Student {}", currentAdmValue + 1);
        return currentAdmValue;
    }
    private static BigDecimal safeValue(BigDecimal value) {
        return Objects.requireNonNullElse(value, BigDecimal.ZERO);
    }
}
