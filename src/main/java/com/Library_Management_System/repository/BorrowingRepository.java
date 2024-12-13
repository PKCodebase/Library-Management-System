package com.Library_Management_System.repository;


import com.Library_Management_System.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByUserId(Long userId);

    List<Borrowing> findByBookId(Long bookId);
}
