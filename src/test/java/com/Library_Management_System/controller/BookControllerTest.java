package com.Library_Management_System.controller;

import com.Library_Management_System.Util.BookUtil;
import com.Library_Management_System.entity.Book;
import com.Library_Management_System.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@SpringBootTest
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
    void addBookSuccess(){
        Book book = BookUtil.bookEntity();
        bookController.addBook(book);
        verify(bookService, times(1)).addBook(book);

    }
    @Test
    void getAllBookSuccess(){
        List<Book> book = List.of(BookUtil.bookEntity());
        when(bookService.getAllBooks()).thenReturn(book);
        bookController.getAllBooks();
        verify(bookService, times(1)).getAllBooks();
    }
    @Test
    public void updateBookByIdSuccess(){
        Book book = BookUtil.bookEntity();
        book.setId(1L);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        bookController.updateBook(1L, book);
        verify(bookService, times(1)).updateBook(1L, book);

    }
    @Test
    public void deleteBookByIdSuccess(){
        Book book = BookUtil.bookEntity();
        book.setId(1L);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        bookController.deleteBook(1L);
        verify(bookService, times(1)).deleteBook(1L);
    }
    @Test
    public void getBookByIdSuccess(){
        Book book = BookUtil.bookEntity();
        book.setId(1L);
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        bookController.getBookById(1L);
        verify(bookService, times(1)).getBookById(1L);
    }
    @Test
    public void searchBookByCategorySuccess(){
        List<Book> book = List.of(BookUtil.bookEntity());
        when(bookService.searchBookByCategory("category")).thenReturn(book);
        bookController.searchBookByCategory("category");
        verify(bookService, times(1)).searchBookByCategory("category");
    }
    @Test
    public void searchBookByAuthorSuccess(){
        List<Book> book = List.of(BookUtil.bookEntity());
        when(bookService.searchBooksByAuthor("author")).thenReturn(book);
        bookController.searchBooksByAuthor("author");
        verify(bookService, times(1)).searchBooksByAuthor("author");
    }
    @Test
    public void searchBookByTitleSuccess(){
        List<Book> book = List.of(BookUtil.bookEntity());
        when(bookService.searchBooksByTitle("title")).thenReturn(book);
        bookController.searchBookByTitle("title");
        verify(bookService, times(1)).searchBooksByTitle("title");
    }
    @Test
    public void searchBookByTitleAndAuthorSuccess(){
        List<Book> book = List.of(BookUtil.bookEntity());
        when(bookService.searchBooksByTitleAndAuthor("title", "author")).thenReturn(book);
        bookController.searchBooksByTitleAndAuthor("title", "author");
        verify(bookService, times(1)).searchBooksByTitleAndAuthor("title", "author");
    }

}
