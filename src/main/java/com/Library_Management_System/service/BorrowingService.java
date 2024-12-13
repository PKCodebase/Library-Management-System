package com.Library_Management_System.service;

import com.Library_Management_System.entity.Book;
import com.Library_Management_System.entity.Borrowing;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.repository.BookRepository;
import com.Library_Management_System.repository.BorrowingRepository;
import com.Library_Management_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public Borrowing borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (book.getCopiesAvailable() <= 0) {
            throw new RuntimeException("No copies available for this book");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setUser(user);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setReturnDate(LocalDate.now().plusDays(14));
        borrowing.setReturned(false);

        book.setCopiesAvailable(book.getCopiesAvailable() - 1);
        bookRepository.save(book);

        return borrowingRepository.save(borrowing);
    }

    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId).orElseThrow(() -> new RuntimeException("Borrowing not found"));

        if (borrowing.isReturned()) {
            throw new RuntimeException("Book already returned");
        }

        borrowing.setReturned(true);
        Book book = borrowing.getBook();
        book.setCopiesAvailable(book.getCopiesAvailable() + 1);
        bookRepository.save(book);

        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getBorrowingsByUser (Long userId){
        return borrowingRepository.findByUserId(userId);
    }

    public List<Borrowing> getBorrowingsByBook (Long bookId){
        return borrowingRepository.findByBookId(bookId);
    }

}
