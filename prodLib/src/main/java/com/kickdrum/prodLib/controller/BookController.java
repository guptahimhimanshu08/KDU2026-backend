package com.kickdrum.prodLib.controller;

import com.kickdrum.prodLib.model.Book;
import com.kickdrum.prodLib.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @Operation(summary = "Starts the async book processing")
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestParam String title) {
        Book book = service.addBook(title);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(service.getAllBooks());
    }
}
