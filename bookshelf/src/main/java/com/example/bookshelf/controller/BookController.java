package com.example.bookshelf.controller;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.bookshelf.model.Book;
import com.example.bookshelf.service.BookService;
import jakarta.validation.Valid;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooksSorted(
                @RequestParam(required = false) String author,
                @RequestParam(required = false) String sortBy,
                @RequestParam(required = false, defaultValue = "0") int page,
                @RequestParam(required = false, defaultValue = "10") int size) {


            return new ResponseEntity<>(bookService.getBooks(author, sortBy, page, size), HttpStatus.OK);
        
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<EntityModel<Book>> getBookById(@PathVariable int id){
        Book book = bookService.getBookById(id);

        EntityModel<Book> bookEntity = EntityModel.of(book,
            linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel(),
            linkTo(methodOn(BookController.class).getAllBooksSorted(null, null, 0, 10)).withRel("all-books")    
        );

        if(book==null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(bookEntity, HttpStatus.OK);
    }
   
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
        try{
            bookService.addBook(book);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable int id , @Valid @RequestBody Book updatedBook){
        try{
            bookService.updateBook(id, updatedBook);
            return new ResponseEntity<>("Book Details Updated", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id){
        boolean deleted = bookService.deleteBook(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();

    }



}
