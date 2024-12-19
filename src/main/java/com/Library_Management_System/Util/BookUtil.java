package com.Library_Management_System.Util;

import com.Library_Management_System.entity.Book;

public class BookUtil {
    public static Book bookEntity(){
        Book book = new Book();
        book.setTitle("Book");
        book.setAuthor("Author");
        book.setCategory("Category");
        book.setCopiesAvailable(2);
        return book;
    }
    public static Book bookEntity2(){
        Book book = new Book();
        book.setTitle("Title Book");
        book.setAuthor("Author 1");
        book.setCategory("Category");
        book.setCopiesAvailable(5);
        return book;
    }
    public static Book bookEntity3(){
        Book book = new Book();
        book.setTitle("Title Book");
        book.setAuthor("Author 2");
        book.setCategory("Category");
        book.setCopiesAvailable(5);
        return book;
    }
}
