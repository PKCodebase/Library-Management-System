package com.Library_Management_System.service;
import com.Library_Management_System.entity.Book;
import com.Library_Management_System.exception.AuthorNotFoundException;
import com.Library_Management_System.exception.BookNotFoundException;
import com.Library_Management_System.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {
    Book addBook(Book book);

    List<Book> getAllBooks();

    void deleteBook(Long id) throws BookNotFoundException;

    Book updateBook(Long id, Book book) throws BookNotFoundException;

    List<Book> searchBookByCategory(String category);

    Optional<Book> getBookById(Long id);

    List<Book> searchBooksByTitle(String title);

    List<Book> searchBooksByAuthor(String author) throws AuthorNotFoundException;

    List<Book> searchBooksByTitleAndAuthor(String title, String author);
}
