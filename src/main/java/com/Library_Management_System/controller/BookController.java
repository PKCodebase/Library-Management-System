package com.Library_Management_System.controller;


import com.Library_Management_System.entity.Book;
import com.Library_Management_System.exception.AuthorNotFoundException;
import com.Library_Management_System.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/books")
@Tag( name = "Book Management", description = "Endpoints for managing books")
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }


    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Book deleted successfully!";
    }


    @GetMapping("/search")
    public List<Book> searchBookByCategory(@RequestParam String category){
        return bookService.searchBookByCategory(category);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
     public Book getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @GetMapping("/search/title")
    public List<Book> searchBookByTitle(@RequestParam String title){
        return bookService.searchBooksByTitle(title);
    }


    @GetMapping("/search/author")
    public List<Book> searchBooksByAuthor(@RequestParam String author) throws AuthorNotFoundException {
        return bookService.searchBooksByAuthor(author);
    }


    @GetMapping("/search/titleAndAuthor")
    public List<Book> searchBooksByTitleAndAuthor(@RequestParam String title, @RequestParam String author){
        return bookService.searchBooksByTitleAndAuthor(title,author);
    }


}
