package com.Library_Management_System.repository;




import com.Library_Management_System.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBookByCategory(String category);


    @Query("SELECT b FROM Book b WHERE b.title = :title AND b.author = :author")
    List<Book> findBookByTitleAndAuthor(@Param("title") String title, @Param("author") String author);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
