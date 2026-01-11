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
    private static final Logger log =
            LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository bookRepository, BookRegistryClient registryClient) {
        this.bookRepository = bookRepository;
        this.registryClient = registryClient;

    }
    
    public Book getBookWithCover(int id) {

        Book book = getBookById(id);

        String cover = registryClient.fetchBookCover(book.getAuthor());

        book.setCoverImage(cover);
        return book;
    }
    
    public Page<Book> getBooks(String author, String sortBy,String sortDir, int page, int size){
        
        if (page < 0) {
            throw new InvalidPaginationException("Page index must not be negative");
        }
        
        
        if (size <= 0 || size > 50) {
            throw new InvalidPaginationException("Page size must be between 1 and 50");
        }

        Sort.Direction direction =
            sortDir.equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

        if (!List.of("title").contains(sortBy)) {
            throw new InvalidSortException("Sorting by " + sortBy + " is not allowed");
        }

        int pageIndex = page - 1;

        if (pageIndex < 0) {
            throw new InvalidPaginationException("Page number must be >= 1");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        
        if (author != null && !author.isBlank()) {
            return bookRepository.findByAuthor(author, pageable);
        }

        return bookRepository.findAll(pageable);
    }

    public Book getBookById(int id){
        return bookRepository.findById(id)
         .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Transactional
    public Book addBook(Book book){

        if(book == null){
            throw new InvalidBookException("Book must not be null");
        }

        try{
            Book saved =  bookRepository.save(book);

            log.info("New book added successfully: id={}, title='{}', author='{}'",
                saved.getId(),
                saved.getTitle(),
                saved.getAuthor());

            return saved;

        }catch(DataIntegrityViolationException ex){

            throw new BookAlreadyExistsException("Book already exists", ex);
        }
    }
    
    @Transactional
    public Book updateBook(long id, Book updatedBook) {

        if (updatedBook == null) {
            throw new InvalidBookException("Book must not be null");
        }

        Book existing = bookRepository.findById(id)
            .orElseThrow(() ->
                new BookNotFoundException("Book not found with id: " + id)
            );
        
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());

        return bookRepository.save(existing);
        
    }

    @Transactional
    public void deleteBook(int id){

        Book book = bookRepository.findById(id)
            .orElseThrow(() ->
                new BookNotFoundException("Book not found with id: " + id)
            );
        bookRepository.deleteById(book);
    }

}
