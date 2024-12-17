package com.Library_Management_System.controller;

import com.Library_Management_System.entity.Borrowing;
import com.Library_Management_System.service.BorrowingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowings")
@Tag(name = "Borrowing", description = "Borrowing APIs")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/borrow")
    public Borrowing borrowBook(@RequestParam Long bookId, @RequestParam Long userId) {
        return borrowingService.borrowBook(bookId, userId);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Borrowing returnBook(@RequestParam Long borrowingId) {
        return borrowingService.returnBook(borrowingId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBorrowingsByUser(@PathVariable Long userId) {
        List<Borrowing> borrowings = borrowingService.getBorrowingsByUser(userId);
        if (borrowings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No borrow record available for user with ID: " + userId);
        }
        return ResponseEntity.ok(borrowings);
    }

    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBorrowingsByBook(@PathVariable Long bookId) {
        List<Borrowing> borrowings = borrowingService.getBorrowingsByBook(bookId);
        if (borrowings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No borrow record available for book with ID: " + bookId);
        }
        return ResponseEntity.ok(borrowings);
    }
}
