package com.Library_Management_System.controller;

import com.Library_Management_System.Util.BookUtil;
import com.Library_Management_System.Util.BorrowingUtil;
import com.Library_Management_System.Util.UserUtil;
import com.Library_Management_System.entity.Book;
import com.Library_Management_System.entity.Borrowing;
import com.Library_Management_System.service.BorrowingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BorrowingControllerTest {

    @InjectMocks
    private BorrowingController borrowingController;

    @Mock
    private BorrowingService borrowingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBookSuccess() {
        Long bookId = 1L;
        Long userId = 1L;
        Borrowing borrowing = BorrowingUtil.borrowingEntity();
        borrowing.setId(1L);
        borrowing.setBook(BookUtil.bookEntity());
        borrowing.setUser(UserUtil.userEntity());

        when(borrowingService.borrowBook(bookId, userId)).thenReturn(borrowing);

        Book result = borrowingController.borrowBook(bookId, userId).getBook();
        verify(borrowingService).borrowBook(bookId, userId);

    }
    @Test
    void returnBookSuccess() {
        Long borrowingId = 1L;
        Borrowing borrowing = BorrowingUtil.borrowingEntity();
        borrowing.setId(1L);
        borrowing.setBook(BookUtil.bookEntity());
        borrowing.setUser(UserUtil.userEntity());

        when(borrowingService.returnBook(borrowingId)).thenReturn(borrowing);

        Book result = borrowingController.returnBook(borrowingId).getBook();
        verify(borrowingService).returnBook(borrowingId);

    }
    @Test
    void getBorrowingByUserSuccess(){
        Long userId = 1L;
        List<Borrowing> borrowings = List.of(BorrowingUtil.borrowingEntity());
        when(borrowingService.getBorrowingsByUserId(userId)).thenReturn(borrowings);
        ResponseEntity<List<Borrowing>> result =  (ResponseEntity<List<Borrowing>>) borrowingController.getBorrowingsByUser(userId);
        verify(borrowingService).getBorrowingsByUserId(userId);
    }
    @Test
    void getBorrowingByBookSuccess(){
        Long bookId = 1L;
        List<Borrowing> borrowings = List.of(BorrowingUtil.borrowingEntity());
        when(borrowingService.getBorrowingsByBook(bookId)).thenReturn(borrowings);
        ResponseEntity<List<Borrowing>> result = (ResponseEntity<List<Borrowing>>) borrowingController.getBorrowingsByBook(bookId);
        verify(borrowingService).getBorrowingsByBook(bookId);
    }
}
