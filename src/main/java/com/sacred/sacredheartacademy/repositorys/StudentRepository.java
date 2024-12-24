package com.sacred.sacredheartacademy.repositorys;

import com.sacred.sacredheartacademy.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
        "s.address_street, s.address_city, s.address_state, s.address_postal_code,s.date_of_admission,s.student_class,s.term,s.date_saved,s.updated_date," +
        "p.parent_id AS parent_id, p.name AS parent_name, p.relationship AS parent_relationship, p.phone AS parent_phone, p.email AS parent_email, " +
        "fd.fees_id AS fees_id, fd.outstanding_fees, fd.admission, fd.fees_amount, fd.tution, fd.lunch, fd.transport, " +
        "fd.computer, fd.exams, fd.assessment, fd.extra_curriculum, fd.total " +
        "FROM students s " +
        "LEFT JOIN parents p ON p.student_id = s.student_id " +
        "LEFT JOIN fees_data fd ON fd.student_id = s.student_id " +
        "WHERE s.student_id = :studentId", nativeQuery = true)
Student findStudentsWithParents(@Param("studentId") long studentId);

    @Query(value = "SELECT * FROM Students",nativeQuery = true)
    Page<Student> findAllStudents(Pageable pageable);
    @Query(value ="SELECT COUNT(*) FROM students s WHERE EXTRACT(YEAR FROM s.date_of_admission) = :year AND EXTRACT(MONTH FROM s.date_of_admission) = :monthValue", nativeQuery = true)
    long countStudentByAdmMonth(int monthValue, int year);
@Query(value = "SELECT * FROM Students s WHERE s.adm_no LIKE %:admNo% ",nativeQuery = true)
    Page<List<Student>> findStudentBySearch(Pageable pageable, String admNo);
}
