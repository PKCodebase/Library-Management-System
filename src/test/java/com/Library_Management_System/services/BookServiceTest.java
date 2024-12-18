package com.Library_Management_System.services;

import com.Library_Management_System.entity.Book;
import com.Library_Management_System.exception.AuthorNotFoundException;
import com.Library_Management_System.exception.BookNotFoundException;
import com.Library_Management_System.service.BookService;
import com.Library_Management_System.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Book book;

    @Test
    public void addBookTest() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setCategory("Category");
        book.setCopiesAvailable(2);

        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);

        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
        assertEquals("Test Author", savedBook.getAuthor());
        assertEquals("Category", savedBook.getCategory());
        assertEquals(2, savedBook.getCopiesAvailable());

        verify(bookRepository, times(1)).save(book);
    }


    @Test
    public void getAllBooksTest() {
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setCategory("Category 1");
        book1.setCopiesAvailable(2);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setCategory("Category 2");
        book2.setCopiesAvailable(3);

        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Book 2", result.get(1).getTitle());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void updateBookTestSuccess() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setCategory("Category");
        book.setCopiesAvailable(2);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book updatedBook = bookService.updateBook(1L, book);

        assertNotNull(updatedBook);
        assertEquals(1L, updatedBook.getId());
        assertEquals("Test Book", updatedBook.getTitle());
        assertEquals("Test Author", updatedBook.getAuthor());
        assertEquals("Category", updatedBook.getCategory());
        assertEquals(2, updatedBook.getCopiesAvailable());
        assertEquals(book, updatedBook);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }


    @Test
    public void updateBookTestFailure(){
        Book book = new Book();
        book.setTitle("Book");
        book.setAuthor("Author");
        book.setCategory("Category");
        book.setCopiesAvailable(2);
        bookRepository.save(book);
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(2L, book));
        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    public void getBookByIdTestSuccess() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setCategory("Category");
        book.setCopiesAvailable(2);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Test Book", result.get().getTitle());
        assertEquals("Test Author", result.get().getAuthor());
        assertEquals("Category", result.get().getCategory());
        assertEquals(2, result.get().getCopiesAvailable());

        verify(bookRepository, times(1)).findById(1L);
    }


    @Test
    public void getBookByIdTestFailure(){
        Book book = new Book();
        book.setTitle("Book");
        book.setAuthor("Author");
        book.setCategory("Category");
        book.setCopiesAvailable(2);
        bookRepository.save(book);
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(2L));
        verify(bookRepository, times(1)).findById(2L);
    }
    @Test
    public void deleteBookTestSuccess(){
        Book book = new Book();
        book.setTitle("Book");
        book.setAuthor("Author");
        book.setCategory("Category");
        book.setCopiesAvailable(2);
        bookRepository.save(book);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
    @Test
    public void deleteBookTestFailure(){
        Book book = new Book();
        book.setTitle("Book");
        book.setAuthor("Author");
        book.setCategory("Category");
        book.setCopiesAvailable(2);
        bookRepository.save(book);
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(2L));
        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    public void searchBooksByAuthorTest(){
        Book book1 = new Book();
        book1.setTitle("Test Book");
        book1.setAuthor("Author 1");
        book1.setCategory("Fiction");
        book1.setCopiesAvailable(5);

        Book book2 = new Book();
        book2.setTitle("Test Book");
        book2.setAuthor("Author 2");
        book2.setCategory("Non-fiction");
        book2.setCopiesAvailable(3);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookRepository.findByAuthorContainingIgnoreCase("Author")).thenReturn(books);

        List<Book> result = bookService.searchBooksByAuthor("Author");
        assertEquals(2, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthor());
        assertEquals("Fiction", result.get(0).getCategory());
        assertEquals(5, result.get(0).getCopiesAvailable());

        assertEquals("Test Book", result.get(1).getTitle());
        assertEquals("Author 2", result.get(1).getAuthor());
        assertEquals("Non-fiction", result.get(1).getCategory());
        assertEquals(3, result.get(1).getCopiesAvailable());
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("Author");

    }
    @Test
    public void searchBooksByAuthorTestFailure(){

        Book book1 = new Book();
        book1.setTitle("Test Book");
        book1.setAuthor("Author 1");
        book1.setCategory("Fiction");
        book1.setCopiesAvailable(5);

        Book book2 = new Book();
        book2.setTitle("Test Book");
        book2.setAuthor("Author 2");
        book2.setCategory("Non-fiction");
        book2.setCopiesAvailable(3);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookRepository.findByAuthorContainingIgnoreCase("Author")).thenReturn(books);

        assertThrows(AuthorNotFoundException.class, () -> bookService.searchBooksByAuthor("Author1"));
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("Author1");

    }

    @Test
    public void searchBooksByTitleTest(){
        Book book1 = new Book();
        book1.setTitle("Title Book");
        book1.setAuthor("Author 1");
        book1.setCategory("Category");
        book1.setCopiesAvailable(5);

        Book book2 = new Book();
        book2.setTitle("Title Book");
        book2.setAuthor("Author 2");
        book2.setCategory("Category");
        book2.setCopiesAvailable(3);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookRepository.findByTitleContainingIgnoreCase("Title")).thenReturn(books);

        List<Book> result = bookService.searchBooksByTitle("Title");
        assertEquals(2, result.size());
        assertEquals("Title Book", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthor());
        assertEquals("Category", result.get(0).getCategory());
        assertEquals(5, result.get(0).getCopiesAvailable());

        assertEquals("Title Book", result.get(1).getTitle());
        assertEquals("Author 2", result.get(1).getAuthor());
        assertEquals("Category", result.get(1).getCategory());
        assertEquals(3, result.get(1).getCopiesAvailable());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Title");

    }
    @Test
    public void searchBooksByTitleTestFailure(){
        Book book1 = new Book();
        book1.setTitle("Title Book");
        book1.setAuthor("Author 1");
        book1.setCategory("Category");
        book1.setCopiesAvailable(5);

        Book book2 = new Book();
        book2.setTitle("Title Book");
        book2.setAuthor("Author 2");
        book2.setCategory("Category");
        book2.setCopiesAvailable(3);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookRepository.findByTitleContainingIgnoreCase("Title")).thenReturn(books);
        assertThrows(BookNotFoundException.class, () -> bookService.searchBooksByTitle("Title1"));
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Title1");

    }

    @Test
    public void searchBooksByTitleAndAuthor(){
        Book book1 = new Book();
        book1.setTitle("Title Book");
        book1.setAuthor("Author 1");
        book1.setCategory("Category");
        book1.setCopiesAvailable(5);

        Book book2 = new Book();
        book2.setTitle("Title Book");
        book2.setAuthor("Author 2");
        book2.setCategory("Category");
        book2.setCopiesAvailable(3);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookRepository.findBookByTitleAndAuthor("Title","Author")).thenReturn(books);

        List<Book> result = bookService.searchBooksByTitleAndAuthor("Title","Author");
        assertEquals(2, result.size());
        assertEquals("Title Book", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthor());
        assertEquals("Category", result.get(0).getCategory());
        assertEquals(5, result.get(0).getCopiesAvailable());

        assertEquals("Title Book", result.get(1).getTitle());
        assertEquals("Author 2", result.get(1).getAuthor());
        assertEquals("Category", result.get(1).getCategory());
        assertEquals(3, result.get(1).getCopiesAvailable());
        verify(bookRepository, times(1)).findBookByTitleAndAuthor("Title","Author");

    }
    @Test
    public void searchBooksByTitleAndAuthorTestFailure(){
        Book book1 = new Book();
        book1.setTitle("Title Book");
        book1.setAuthor("Author 1");
        book1.setCategory("Category");
        book1.setCopiesAvailable(5);

        Book book2 = new Book();
        book2.setTitle("Title Book");
        book2.setAuthor("Author 2");
        book2.setCategory("Category");
        book2.setCopiesAvailable(3);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookRepository.findBookByTitleAndAuthor("Title", "Author")).thenReturn(books);
        assertThrows(BookNotFoundException.class, () -> bookService.searchBooksByTitleAndAuthor("Title1", "Author1"));
        verify(bookRepository, times(1)).findBookByTitleAndAuthor("Title1", "Author1");

    }
    @Test
    public void searchBookByCategory(){

        Book book1 = new Book();
        book1.setTitle("Title Book");
        book1.setAuthor("Author 1");
        book1.setCategory("Category");
        book1.setCopiesAvailable(5);

        Book book2 = new Book();
        book2.setTitle("Title Book");
        book2.setAuthor("Author 2");
        book2.setCategory("Category");
        book2.setCopiesAvailable(3);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookRepository.findBookByCategory("Category")).thenReturn(books);

        List<Book> result = bookService.searchBookByCategory("Category");
        assertEquals(2, result.size());
        assertEquals("Title Book", result.get(0).getTitle());
        assertEquals("Author 1", result.get(0).getAuthor());
        assertEquals("Category", result.get(0).getCategory());
        assertEquals(5, result.get(0).getCopiesAvailable());

        assertEquals("Title Book", result.get(1).getTitle());
        assertEquals("Author 2", result.get(1).getAuthor());
        assertEquals("Category", result.get(1).getCategory());
        assertEquals(3, result.get(1).getCopiesAvailable());
        verify(bookRepository, times(1)).findBookByCategory("Category");

    }
    @Test
    public void searchBookByCategoryTestFailure(){
        Book book1 = new Book();
        book1.setTitle("Title Book");
        book1.setAuthor("Author 1");
        book1.setCategory("Category");
        book1.setCopiesAvailable(5);

        Book book2 = new Book();
        book2.setTitle("Title Book");
        book2.setAuthor("Author 2");
        book2.setCategory("Category");
        book2.setCopiesAvailable(3);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookRepository.findBookByCategory("Category")).thenReturn(books);
        assertThrows(BookNotFoundException.class, () -> bookService.searchBookByCategory("Category1"));
        verify(bookRepository, times(1)).findBookByCategory("Category1");

    }
}

