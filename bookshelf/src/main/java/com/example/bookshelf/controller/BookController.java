package com.example.bookshelf.controller;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @Operation(
        summary = "Get books",
        description = "Retrieve books with optional filtering, sorting, and pagination"
    )
    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooksSorted(
                @RequestParam(required = false) String author,
                @RequestParam(required = false) String sortBy,
                @RequestParam(defaultValue = "asc") String sortDir,
                @RequestParam(required = false, defaultValue = "1") int page,
                @RequestParam(required = false, defaultValue = "10") int size) {


            Page<Book> books = bookService.getBooks(
            author, sortBy, sortDir, page, size);

            return new ResponseEntity.ok(books);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Book>> getBookById(@PathVariable int id){
        Book book = bookService.getBookById(id);

        EntityModel<Book> bookEntity = EntityModel.of(book,
            linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel(),
            linkTo(methodOn(BookController.class).getAllBooksSorted(null, null, 0, 10)).withRel("all-books")    
        );

        return new ResponseEntity<>(bookEntity, HttpStatus.OK);
    }
    
    @Operation(
        summary = "Add a new book",
        description = "Creates a new book entry in the library"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Book created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid book data"),
        @ApiResponse(responseCode = "409", description = "Book already exists")
    })
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
      
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id , @Valid @RequestBody Book updatedBook){
       
        Book saved = bookService.updateBook(id, updatedBook);

        return new ResponseEntity<>(saved, HttpStatus.OK);
       
    }

    @Operation(
        summary = "Delete a book",
        description = "Deletes a book. Only ADMIN users are allowed."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id){

        bookService.deleteBook(id);

        return new ResponseEntity.noContent().build();

    }



}
