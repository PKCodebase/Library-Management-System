package com.Library_Management_System.service;
import com.Library_Management_System.entity.Book;
import com.Library_Management_System.exception.AuthorNotFoundException;
import com.Library_Management_System.exception.BookNotFoundException;
import com.Library_Management_System.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;


    @PreAuthorize("hasAuthorize=Admin")
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Long id, Book book) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isEmpty()) {
            throw new BookNotFoundException("Book not found with ID: " + id);
        }
        Book updatedBook = existingBook.get();
        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setCategory(book.getCategory());
        updatedBook.setCopiesAvailable(book.getCopiesAvailable());
        return bookRepository.save(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    public List<Book> searchBookByCategory(String category){
       List<Book> books = bookRepository.findBookByCategory(category);
        if(books.isEmpty()){
            throw new BookNotFoundException (category + " : No Books Available ");
        }
        return books;
    }

    public Book getBookById(Long id){
        Optional<Book> books =  bookRepository.findById(id);
        if(books.isEmpty()){
            throw new BookNotFoundException ("Book Not Found ");
        }
        return books.get();
    }

    public List<Book> searchBooksByAuthor(String author) throws AuthorNotFoundException {
        List<Book> books =  bookRepository.findBookByAuthor(author);
        if(books.isEmpty()){
            throw new AuthorNotFoundException(author + " : This author Book is not available ");
        }
        return books;
    }

    public List<Book> searchBooksByTitle(String title){
        List<Book> books = bookRepository.findBookByTitle(title);
        if(books.isEmpty()){
            throw new BookNotFoundException(title + " : Book Not Found ");
        }
        return books;
    }

    public List<Book> searchBooksByTitleAndAuthor(String title, String author) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }

        List<Book> books = bookRepository.findBookByTitleAndAuthor(title, author);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with title: " + title + " and author: " + author);
        }
        return books;
    }



}
