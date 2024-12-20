package com.Library_Management_System.services;

import com.Library_Management_System.Util.BookUtil;
import com.Library_Management_System.Util.BorrowingUtil;
import com.Library_Management_System.Util.UserUtil;
import com.Library_Management_System.entity.Book;
import com.Library_Management_System.entity.Borrowing;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.*;
import com.Library_Management_System.repository.BookRepository;
import com.Library_Management_System.repository.BorrowingRepository;
import com.Library_Management_System.repository.UserRepository;
import com.Library_Management_System.service.Impl.BorrowingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowingServiceTest {

    @InjectMocks
    private BorrowingServiceImpl borrowingServiceImpl;

    @Mock
    private BorrowingRepository borrowingRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Borrow Book Test cases
    @Test
    public void borrowBookSuccess() {
        User user = UserUtil.userEntity();
        Book book = BookUtil.bookEntity();
        Borrowing borrowing = BorrowingUtil.borrowingEntity();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(borrowingRepository.save(any(Borrowing.class))).thenReturn(borrowing);

        Borrowing result = borrowingServiceImpl.borrowBook(user.getId(), book.getId());

        verify(bookRepository).findById(book.getId());
        verify(userRepository).findById(user.getId());
        verify(borrowingRepository).save(any(Borrowing.class));

    }
    @Test
    public void borrowBookNotFound() {
        User user = UserUtil.userEntity();
        Book book = BookUtil.bookEntity();


        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> borrowingServiceImpl.borrowBook(user.getId(), book.getId()));
    }
    @Test
    public void borrowUserNotFound() {
        User user = UserUtil.userEntity();
        Book book = BookUtil.bookEntity();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> borrowingServiceImpl.borrowBook(user.getId(), book.getId()));
    }
    @Test
    public void borrowNoCopiesAvailable() {
        User user = UserUtil.userEntity();
        Book book = BookUtil.bookEntity();
        book.setCopiesAvailable(0);

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(NoCopiesAvailableException.class, () -> borrowingServiceImpl.borrowBook(user.getId(), book.getId()));
    }

    //Return Book Test Cases
    @Test
    public void returnBookSuccess() {
        Borrowing borrowing = BorrowingUtil.borrowingEntity();

        when(borrowingRepository.findById(1L)).thenReturn(Optional.of(borrowing));
        when(borrowingRepository.save(borrowing)).thenReturn(borrowing);

        Borrowing updatedBorrowing = borrowingServiceImpl.returnBook(1L);

        assertTrue(updatedBorrowing.isReturned());
        assertEquals(1, updatedBorrowing.getBook().getCopiesAvailable());
        verify(borrowingRepository, times(1)).findById(1L);
        verify(borrowingRepository, times(1)).save(borrowing);

    }
    @Test
    public void returnBookNotFound() {
        when(borrowingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> borrowingServiceImpl.returnBook(1L));
    }
    @Test
    public void returnBookAlreadyReturned() {
        Borrowing borrowing = BorrowingUtil.borrowingEntity();
        borrowing.setReturned(true);

        when(borrowingRepository.findById(1L)).thenReturn(Optional.of(borrowing));

        assertThrows(BookAlreadyReturnException.class, () -> borrowingServiceImpl.returnBook(1L));
    }

    //Get Borrowing Test Cases

    @Test
    public void getBorrowingByUserIdSuccess() {
        User user = UserUtil.userEntity();

        user.setId(1L);

        Borrowing borrowing = BorrowingUtil.borrowingEntity();
        List<Borrowing> borrowings = List.of(borrowing);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(borrowingRepository.findByUserId(user.getId())).thenReturn(borrowings);
        List<Borrowing> result = borrowingServiceImpl.getBorrowingsByUserId(user.getId());
        assertEquals(borrowings, result);
        verify(userRepository, times(1)).findById(user.getId());
        verify(borrowingRepository, times(1)).findByUserId(user.getId());
        verifyNoMoreInteractions(userRepository, borrowingRepository);
    }
    @Test
    public void getBorrowingByUserIdNotFound() {
        User user = UserUtil.userEntity();
        user.setId(1L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> borrowingServiceImpl.getBorrowingsByUserId(user.getId()));
        verify(userRepository, times(1)).findById(user.getId());
        verifyNoMoreInteractions(userRepository, borrowingRepository);
    }

    @Test
    public void getNoBorrowBook() {
        Long userId = 1L;
        User user = UserUtil.userEntity();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(borrowingRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(NoBookBorrowedException.class, () -> borrowingServiceImpl.getBorrowingsByUserId(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(borrowingRepository, times(1)).findByUserId(userId);
        verifyNoMoreInteractions(userRepository, borrowingRepository);
    }

    //Get Borrowing Book  cases
    @Test
    public void getBorrowingBookBySuccess(){
    List<Borrowing> borrowings = List.of(BorrowingUtil.borrowingEntity());
    when(borrowingRepository.findByBookId(1L)).thenReturn(borrowings);
    List<Borrowing> result = borrowingServiceImpl.getBorrowingsByBook(1L);
    assertEquals(borrowings, result);
    verify(borrowingRepository, times(1)).findByBookId(1L);
    verifyNoMoreInteractions(borrowingRepository);
    }

    @Test
    public void getBorrowingBookByNotFound(){
    when(borrowingRepository.findByBookId(1L)).thenReturn(Collections.emptyList());
    assertThrows(BookNotFoundException.class, () -> borrowingServiceImpl.getBorrowingsByBook(1L));
    verify(borrowingRepository, times(1)).findByBookId(1L);
    verifyNoMoreInteractions(borrowingRepository);
    }
}
