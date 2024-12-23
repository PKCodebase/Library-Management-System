package com.Library_Management_System.service;

import com.Library_Management_System.entity.Borrowing;
import com.Library_Management_System.exception.BookNotFoundException;
import com.Library_Management_System.exception.NoCopiesAvailableException;
import com.Library_Management_System.exception.UserNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public interface BorrowingService {

    Borrowing borrowBook( Long bookId,Long userId) throws UserNotFoundException, BookNotFoundException, NoCopiesAvailableException;

    Borrowing returnBook(Long borrowingId) throws UserNotFoundException, BookNotFoundException;

    List<Borrowing> getBorrowingsByUserId (Long userId);

    List<Borrowing> getBorrowingsByBook (Long bookId);



}
