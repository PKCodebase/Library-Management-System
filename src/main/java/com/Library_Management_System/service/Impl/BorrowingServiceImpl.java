package com.Library_Management_System.service.Impl;

import com.Library_Management_System.entity.Book;
import com.Library_Management_System.entity.Borrowing;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.*;
import com.Library_Management_System.repository.BookRepository;
import com.Library_Management_System.repository.BorrowingRepository;
import com.Library_Management_System.repository.UserRepository;
import com.Library_Management_System.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Borrowing borrowBook(Long bookId, Long userId) throws NoCopiesAvailableException {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book not found with this id.");
        }
        if (book.get().getCopiesAvailable() <= 0) {
            throw new NoCopiesAvailableException("No copies available for borrowing.");
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with this id.");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book.get());
        borrowing.setUser(user.get());
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setReturnDate(LocalDate.now().plusDays(14));
        borrowing.setReturned(false);

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        return borrowingRepository.save(borrowing);
    }

    @Override
    public Borrowing returnBook(Long borrowingId) {
        Optional<Borrowing> borrowing = borrowingRepository.findById(borrowingId);
        if(borrowing.isEmpty()){
            throw new BookNotFoundException("Book not found with this id.");
        }
        if (borrowing.get().isReturned()) {
            throw new BookAlreadyReturnException("Book already returned");
        }
        borrowing.get().setReturned(true);
        borrowing.get().getBook().setCopiesAvailable(borrowing.get().getBook().getCopiesAvailable() + 1);
        return borrowingRepository.save(borrowing.get());
    }
    @Override
    public List<Borrowing> getBorrowingsByUserId (Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found with this id.");
        }
        List<Borrowing> borrowings =  borrowingRepository.findByUserId(userId);
        if(borrowings.isEmpty()){
            throw new NoBookBorrowedException("No any book borrowed by this user");
        }

        return borrowings;
    }
    @Override
    public List<Borrowing> getBorrowingsByBook (Long bookId){
        List<Borrowing> borrowings =  borrowingRepository.findByBookId(bookId);
        if(borrowings.isEmpty()){
            throw new BookNotFoundException("No any people borrowed this book");
        }
        return borrowings;
    }

}
