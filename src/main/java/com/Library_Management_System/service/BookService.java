package com.Library_Management_System.service;






import com.Library_Management_System.entity.Book;
import com.Library_Management_System.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setCategory(book.getCategory());
        existingBook.setCopiesAvailable(book.getCopiesAvailable());
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    public List<Book> searchBookByCategory(String category){
        return bookRepository.findBookByCategory(category);
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book Not Found"));
    }

    public List<Book> searchBooksByAuthor(String author){
        return bookRepository.findBookByAuthor(author);
    }

    public List<Book> searchBooksByTitle(String title){
        return bookRepository.findBookByTitle(title);
    }

    public List<Book> searchBooksByTitleAndAuthor( String title ,  String author){
        return bookRepository.findBookByTitleAndAuthor(title,author);
    }


}
