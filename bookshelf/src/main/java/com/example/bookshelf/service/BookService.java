package com.example.bookshelf.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.bookshelf.model.Book;
import com.example.bookshelf.repository.BookRepository;


@Service
public class BookService {
    
    private final BookRepository bookRepository;
    private final BookRegistryClient registryClient;

    @Autowired
    public BookService(BookRepository bookRepository, BookRegistryClient registryClient) {
        this.bookRepository = bookRepository;
        this.registryClient = registryClient;

    }
    
    public Book getBookWithCover(int id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        String coverImage = registryClient.fetchBookCover("dummy-author");

        System.out.println("Cover Image: " + coverImage);

        return book;
    }
    
    public Page<Book> getBooks(String author, String sortBy, int page, int size){
        
        Sort sort = Sort.by("title");
        if(sortBy != null ){
            sort = Sort.by(sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Filter by author
        if (author != null && !author.isBlank()) {
            return bookRepository.findByAuthor(author, pageable);
        }

        return bookRepository.findAll(pageable);
    }

    public Book getBookById(int id){
        return bookRepository.findById(id)
         .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book addBook(Book book){
        return bookRepository.save(book);
    }
    
    public Book updateBook(int id, Book updatedBook) {

        updatedBook.setId(id);
        return bookRepository.save(updatedBook);
        
    }

    public boolean deleteBook(int id){
        if (!bookRepository.existsById(id)) {
            return false;
        }
        bookRepository.deleteById(id);
        return true;
    }

}
