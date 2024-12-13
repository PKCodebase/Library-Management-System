package com.Library_Management_System.repository;




import com.Library_Management_System.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBookByCategory(String category);

    List<Book> findBookByAuthor(String author);

    List<Book> findBookByTitle(String title);

    List<Book> findBookByTitleAndAuthor(String category,String author);
}
