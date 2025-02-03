package com.sacred.sacredheartacademy.services;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.sacred.sacredheartacademy.models.Institution.Fees;
import com.sacred.sacredheartacademy.models.Institution.FeesHistory;
import com.sacred.sacredheartacademy.models.Student;
import com.sacred.sacredheartacademy.models.dataobjects.StudentReport;
import com.sacred.sacredheartacademy.poji.StudentPoji;
import com.sacred.sacredheartacademy.repositorys.FeesHistoryRepository;
import com.sacred.sacredheartacademy.repositorys.FeesRepository;
import com.sacred.sacredheartacademy.repositorys.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SaveExcelChanges {

    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private FeesRepository feesRepo;
    @Autowired
    private FeesHistoryRepository feesHistoryRepo;
    @Autowired
    private StudentService studentService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void saveStudentData(MultipartFile file) {
        try {
            List<StudentPoji> poiji = Poiji.fromExcel(file.getInputStream(), PoijiExcelType.XLSX, StudentPoji.class);
            poiji.forEach(e -> {
                boolean exists = studentRepo.studentExists(e.getFirstName().trim(),e.getLastName().trim(),e.getGender());
                if(Objects.isNull(e.getFirstName()) || Objects.isNull(e.getLastName())){
                    throw new RuntimeException("Student Info Missing");
                }
                else if(!exists){
                    log.info("Uploading student data");
                    log.info("Saving Data for student " + e.getFirstName() +" " + e.getLastName());
                    List<Fees> fee = new ArrayList<>();
                    fee.add(writeFees(e));
                    studentService.addStudent(writeStudent(e),new ArrayList<>(),new ArrayList<>(), fee);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Student writeStudent(StudentPoji poji){
        Student student = new Student();
        student.setFirstName(poji.getFirstName().trim());
        student.setLastName(poji.getLastName().trim());
//        student.setDateOfBirth(new Date(studentDTO.getDateOfBirth())); // Example, convert from timestamp
        student.setDateOfBirth(poji.getDateOfBirth());
        student.setDateOfAdmission(poji.getDateOfAdmission());
        student.setStudentClass(poji.getStudentClass().trim());
        student.setTerm(poji.getTerm());
        student.setGender(Objects.isNull(poji.getGender()) ? null : poji.getGender().trim());
        student.setAddressStreet(poji.getAddressStreet());
        student.setAddressCity(poji.getAddressCity());
        student.setAddressState(poji.getAddressState());
        student.setAddressPostalCode(poji.getAddressPostalCode());
//        student.setAdmNo(studentDTO.getAdmNo());
        student.setDateSaved(LocalDateTime.now());
        student.setStudent_status(true);
        return student;
    }

    private Fees writeFees(StudentPoji poiji){
       Fees fees = new Fees();
        fees.setFeesAmount(poiji.getFees_amount());
        fees.setComputer(poiji.getComputer());
        fees.setAssessment(poiji.getAssessment());
        fees.setExams(poiji.getExams());
        fees.setLunch(poiji.getLunch());
//        feesDTO.setOutstandingFees(fer.getFeesAmount().subtract(paidAmount));
        fees.setOutstandingFees(poiji.getOutstanding_fees());
        fees.setTransport(poiji.getTransport());
        fees.setTution(poiji.getTuition());
        fees.setAdmission(poiji.getAdmission());
        fees.setExtraCurriculum(poiji.getExtra_curriculum());
       return fees;
    }

    public List<StudentReport> studentReports(){
        List<StudentReport> reports = new ArrayList<>();
        List<Student> students = studentRepo.findAll();
        students.forEach(e -> {
            List<FeesHistory> history = feesHistoryRepo.findByStudent(e);
            history.forEach(f -> {
                StudentReport report = new StudentReport();
                report.setAssessment(f.getAssessment());
                report.setComputer(f.getComputer());
                report.setAdmission(f.getAdmission());
                report.setExams(f.getExams());
                report.setLunch(f.getLunch());
                report.setTransport(f.getTransport());
                report.setTuition(f.getTution());
                report.setTotal(f.getTotal());
                report.setFees_amount(f.getFeesAmount());
                report.setExtra_curriculum(f.getExtraCurriculum());
                report.setAddressPostalCode(e.getAddressPostalCode());
                report.setAdmNo(e.getAdmNo());
                report.setAddressStreet(e.getAddressStreet());
                report.setAddressState(e.getAddressState());
                report.setDateOfAdmission(e.getDateOfAdmission());
                report.setDateOfBirth(e.getDateOfBirth());
                report.setFirstName(e.getFirstName());
                report.setAddressCity(e.getAddressCity());
                report.setGender(e.getGender());
                report.setLastName(e.getLastName());
                report.setStudentClass(e.getStudentClass());
                report.setTerm(e.getTerm());
                reports.add(report);
            });
        });

        return reports;
    }
}
