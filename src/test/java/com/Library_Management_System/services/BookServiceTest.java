package com.Library_Management_System.services;

import com.Library_Management_System.Util.BookUtil;
import com.Library_Management_System.entity.Book;
import com.Library_Management_System.exception.AuthorNotFoundException;
import com.Library_Management_System.exception.BookNotFoundException;
import com.Library_Management_System.exception.NullValueException;
import com.Library_Management_System.repository.BookRepository;
import com.Library_Management_System.service.Impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.ArrayList;

import static com.Library_Management_System.Util.BookUtil.bookEntity2;
import static com.Library_Management_System.Util.BookUtil.bookEntity3;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Book book;

    @Test
    public void addBookTest() {
        Book book = BookUtil.bookEntity();
        bookRepository.save(book);
        verify(bookRepository, times(1)).save(book);
    }
    @Test
    public void addBookTestFailure() {
        Book book = BookUtil.bookEntity();
        bookRepository.save(book);
        assertThrows(NullValueException.class, () -> bookServiceImpl.addBook(null));
    }


    @Test
    public void getAllBooksTest() {
        List<Book> books = List.of(bookEntity2(), bookEntity3());
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookServiceImpl.getAllBooks();
        assertEquals(books, result);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void getAllBooksTestFailure() {
        List<Book> books = new ArrayList<>();
        bookRepository.save(book);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.getAllBooks());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void updateBookTestSuccess() {
        Book book = BookUtil.bookEntity();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        bookServiceImpl.updateBook(1L, book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void updateBookTestFailure() {
        Book book = BookUtil.bookEntity();
        bookRepository.save(book);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.updateBook(2L, book));
        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    public void getBookByIdTestSuccess() {
        Book book = BookUtil.bookEntity();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<Book> result = bookServiceImpl.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void getBookByIdTestFailure() {
        Book book = BookUtil.bookEntity();
        bookRepository.save(book);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.getBookById(2L));
        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    public void deleteBookTestSuccess() {
        Book book = BookUtil.bookEntity();
        book.setCopiesAvailable(2);
        bookRepository.save(book);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        bookServiceImpl.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteBookTestFailure() {
        Book book = BookUtil.bookEntity();
        bookRepository.save(book);
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.deleteBook(2L));
        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    public void searchBooksByAuthorTest() {
        List<Book> books = List.of(bookEntity2(), bookEntity3());
        when(bookRepository.findByAuthorContainingIgnoreCase("Author")).thenReturn(books);
        when(bookRepository.findByAuthorContainingIgnoreCase("Author1")).thenReturn(Collections.emptyList());

        List<Book> result = bookServiceImpl.searchBooksByAuthor("Author");
        assertEquals(2, result.size());
    }

    @Test
    public void searchBooksByAuthorTestFailure() {
        List<Book> books = List.of(bookEntity2(),bookEntity3());
        when(bookRepository.findByAuthorContainingIgnoreCase("Author")).thenReturn(books);
        when(bookRepository.findByAuthorContainingIgnoreCase("Author1")).thenReturn(Collections.emptyList());

        List<Book> result = bookServiceImpl.searchBooksByAuthor("Author");
        assertEquals(2, result.size());
        assertThrows(AuthorNotFoundException.class, () -> bookServiceImpl.searchBooksByAuthor("Author1"));
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("Author1");
    }

    @Test
    public void searchBooksByTitleTest() {
       List<Book> books = List.of(bookEntity2(), bookEntity3());
        when(bookRepository.findByTitleContainingIgnoreCase("Title")).thenReturn(books);
        when(bookRepository.findByTitleContainingIgnoreCase("Title1")).thenReturn(Collections.emptyList());

        List<Book> result = bookServiceImpl.searchBooksByTitle("Title");
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Title");

    }

    @Test
    public void searchBooksByTitleTestFailure() {

        List<Book> books = List.of(bookEntity2(), bookEntity3());
        when(bookRepository.findByTitleContainingIgnoreCase("Title")).thenReturn(books);
        when(bookRepository.findByTitleContainingIgnoreCase("Title1")).thenReturn(Collections.emptyList());


        List<Book> result = bookServiceImpl.searchBooksByTitle("Title");
        assertEquals(2, result.size());
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.searchBooksByTitle("Title1"));
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Title1");
    }

    @Test
    public void searchBooksByTitleAndAuthor() {
        List<Book> books = List.of(bookEntity2(), bookEntity3());
        when(bookRepository.findBookByTitleAndAuthor("Title", "Author")).thenReturn(books);
        when(bookRepository.findBookByTitleAndAuthor("Title1", "Author1")).thenReturn(Collections.emptyList());

        List<Book> result = bookServiceImpl.searchBooksByTitleAndAuthor("Title", "Author");
        assertEquals(2, result.size());
    }

    @Test
    public void searchBooksByTitleAndAuthorTestFailure() {
        List<Book> books = List.of(bookEntity2(), bookEntity3());
        when(bookRepository.findBookByTitleAndAuthor("Title", "Author")).thenReturn(books);
        when(bookRepository.findBookByTitleAndAuthor("Title1", "Author1")).thenReturn(Collections.emptyList());

        List<Book> result = bookServiceImpl.searchBooksByTitleAndAuthor("Title", "Author");
        assertEquals(2, result.size());
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.searchBooksByTitleAndAuthor("Title1", "Author1"));
        verify(bookRepository, times(1)).findBookByTitleAndAuthor("Title1", "Author1");
    }

    @Test
    public void searchBookByCategory() {
        List<Book> books = List.of(bookEntity2(), bookEntity3());
        when(bookRepository.findBookByCategory("Category")).thenReturn(books);
        when(bookRepository.findBookByCategory("Category1")).thenReturn(Collections.emptyList());
        List<Book> result = bookServiceImpl.searchBookByCategory("Category");
        assertEquals(2, result.size());
    }

    @Test
    public void searchBookByCategoryTestFailure() {
        List<Book> books = List.of(bookEntity2(), bookEntity3());
        when(bookRepository.findBookByCategory("Category")).thenReturn(books);
        when(bookRepository.findBookByCategory("Category1")).thenReturn(Collections.emptyList());
        assertThrows(BookNotFoundException.class, () -> bookServiceImpl.searchBookByCategory("Category1"));
        verify(bookRepository, times(1)).findBookByCategory("Category1");
        verify(bookRepository, never()).findBookByCategory("Category2");
    }
}
