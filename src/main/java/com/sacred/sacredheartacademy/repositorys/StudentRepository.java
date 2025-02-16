package com.sacred.sacredheartacademy.repositorys;

import com.sacred.sacredheartacademy.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
//    @EntityGraph(attributePaths = {
//            "parents"
//    })
//    Optional<Student> findById(Long studentId);
//    Optional<Student> findByIdWithDetails(int studentId);

    //    @Query(value = "SELECT s.student_id,s.gender, s.first_name, s.last_name, s.address_city,s.address_postal_code," +
//            "s.address_state,s.address_street,s.date_of_birth, p.name FROM Students s " +
//            "LEFT JOIN Parents p ON p.student_id = s.student_id  " +
////            "LEFT JOIN enrollments e ON e.student_id = s.student_id "+
//            "LEFT JOIN fees_data fd ON fd.student_id = s.student_id "+
//            "WHERE s.student_id = :studentId",nativeQuery = true)
////    Optional<Student> findStudentsWithParents(@Param("studentId") int studentId);
//    Optional<StudentDTOResponse> findStudentsWithParents(@Param("studentId") int studentId);
//@Query(value = "SELECT s.*, p.*, fd.* FROM Students s " +
//        "LEFT JOIN Parents p ON p.student_id = s.student_id  " +
////            "LEFT JOIN enrollments e ON e.student_id = s.student_id "+
//        "LEFT JOIN fees_data fd ON fd.student_id = s.student_id "+
//        "WHERE s.student_id = :studentId",nativeQuery = true)
//    Optional<Student> findStudentsWithParents(@Param("studentId") int studentId);
//Optional<StudentDTOResponse> findStudentsWithParents(@Param("studentId") int studentId);
//StudentDTOResponse findStudentsWithParents(@Param("studentId") int studentId);
    @Query(value = "SELECT s.student_id AS student_id, s.adm_no, s.first_name, s.last_name, s.date_of_birth, s.gender, " +
            "s.address_street, s.address_city, s.address_state, s.extra_curicullum, s.transport_route, s.transport_charge,s.address_postal_code,s.date_of_admission,s.student_class,s.term,s.date_saved,s.updated_date,s.student_status," +
            "p.parent_id AS parent_id, p.name AS parent_name, p.relationship AS parent_relationship, p.phone AS parent_phone, p.email AS parent_email, " +
            "fd.fees_id AS fees_id, fd.outstanding_fees, fd.admission, fd.fees_amount, fd.tution, fd.lunch, fd.transport, " +
            "fd.computer, fd.exams, fd.assessment, fd.extra_curriculum, fd.total " +
            "FROM students s " +
            "LEFT JOIN parents p ON p.student_id = s.student_id " +
            "LEFT JOIN fees_data fd ON fd.student_id = s.student_id " +
            "WHERE s.student_id = :studentId AND s.student_status=true", nativeQuery = true)
    Student findStudentsWithParents(@Param("studentId") long studentId);

    @Query(value = "SELECT * FROM Students s WHERE s.student_status=true", nativeQuery = true)
    Page<Student> findAllStudents(Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM students s WHERE EXTRACT(YEAR FROM s.date_of_admission) = :year AND EXTRACT(MONTH FROM s.date_of_admission) = :monthValue", nativeQuery = true)
    long countStudentByAdmMonth(int monthValue, int year);

    @Query(value = "SELECT * FROM Students s WHERE s.adm_no ILIKE %:admNo% OR s.first_name ILIKE %:admNo% OR s.last_name ILIKE %:admNo% AND s.student_status=true", nativeQuery = true)
    Page<List<Student>> findStudentBySearchIgnoreCase(Pageable pageable, String admNo);

    //    @Query(value = "SELECT * FROM Students s WHERE s.adm_no ILIKE %:admNo% ",nativeQuery = true)
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM Students s WHERE s.adm_no = :admNo and s.student_status=true", nativeQuery = true)
    boolean checkExistingStudent(String admNo);

    @Query(value = "SELECT MAX(adm_no::NUMERIC) FROM Students", nativeQuery = true)
    Long getMaxAdmissionValue();

    @Query("""
            select (count(s) > 0) from Student s
            where s.firstName = ?1 and s.lastName = ?2 and s.gender = ?3""")
    boolean studentExists(String firstName, String lastName, String gender);

}