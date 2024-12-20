package com.Library_Management_System.service;

import com.Library_Management_System.entity.Book;
import com.Library_Management_System.entity.Borrowing;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.BookNotFoundException;
import com.Library_Management_System.exception.NoCopiesAvailableException;
import com.Library_Management_System.exception.UserNotFoundException;
import com.Library_Management_System.repository.BookRepository;
import com.Library_Management_System.repository.BorrowingRepository;
import com.Library_Management_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public interface BorrowingService {

    Borrowing borrowBook( Long bookId,Long userId) throws UserNotFoundException, BookNotFoundException, NoCopiesAvailableException;

    Borrowing returnBook(Long borrowingId) throws UserNotFoundException, BookNotFoundException;

    List<Borrowing> getBorrowingsByUserId (Long userId);

    List<Borrowing> getBorrowingsByBook (Long bookId);



}
