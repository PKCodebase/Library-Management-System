package com.Library_Management_System.Util;

import com.Library_Management_System.entity.Book;
import com.Library_Management_System.entity.Borrowing;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.enums.UserRole;

import java.time.LocalDate;

public class BorrowingUtil {
    public  static Borrowing borrowingEntity(){
        Borrowing borrowing = new Borrowing();
        borrowing.setId(1L);

        Book book = new Book();
        book.setId(1L);
        borrowing.setBook(book);

        User user = new User();
        user.setId(1L);
        borrowing.setUser(user);

        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setReturnDate(LocalDate.now().plusDays(14));
        borrowing.setReturned(false);

     return borrowing;
    }
}
