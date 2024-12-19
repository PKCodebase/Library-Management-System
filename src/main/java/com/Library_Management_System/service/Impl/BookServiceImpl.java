package com.Library_Management_System.service.Impl;

import com.Library_Management_System.entity.Book;
import com.Library_Management_System.exception.AuthorNotFoundException;
import com.Library_Management_System.exception.BookNotFoundException;
import com.Library_Management_System.repository.BookRepository;
import com.Library_Management_System.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    @PreAuthorize("hasAuthorize=Admin")
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()){
            throw new BookNotFoundException("No Books Available");
        }
        return books;
    }

    @Override
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

    @Override
    public void deleteBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }

        bookRepository.deleteById(id);
        System.out.println("Book deleted successfully with ID " + id);
    }


    @Override
    public List<Book> searchBookByCategory(String category){
        List<Book> books = bookRepository.findBookByCategory(category);
        if(books.isEmpty()){
            throw new BookNotFoundException (category + " : No Books Available ");
        }
        return books;
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        return  book;

    }

    @Override
    public List<Book> searchBooksByAuthor(String author) throws AuthorNotFoundException {
        List<Book> books =  bookRepository.findByAuthorContainingIgnoreCase(author);
        if(books.isEmpty()){
            throw new AuthorNotFoundException(author + " : This author Book is not available ");
        }
        return books;
    }

    @Override
    public List<Book> searchBooksByTitle(String title){
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        if(books.isEmpty()){
            throw new BookNotFoundException(title + " : Book Not Found ");
        }
        return books;
    }

    @Override
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
