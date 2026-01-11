package com.kickdrum.prodLib.service;

import com.kickdrum.prodLib.exception.BookNotFoundException;
import com.kickdrum.prodLib.model.Book;
import com.kickdrum.prodLib.repository.BookRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository repository;
    private final ExecutorService executor;

    public BookService(BookRepository repository, ExecutorService executor) {
        this.repository = repository;
        this.executor = executor;
    }

    @Transactional
    public Book addBook(String title) {

        Book book = new Book(title, "PROCESSING");
        repository.save(book);

        executor.submit(() -> processBarcode(book.getId()));

        return book;
    }

    private void processBarcode(String bookId) {
        try {
            Thread.sleep(3000);
            Book book = repository.findById(bookId)
                    .orElseThrow(() -> new BookNotFoundException("Book not found"));
            book.setStatus("AVAILABLE");
            repository.save(book);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Map<String, Long> auditBooks() {
        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Book::getStatus,
                        Collectors.counting()
                ));
    }
}
