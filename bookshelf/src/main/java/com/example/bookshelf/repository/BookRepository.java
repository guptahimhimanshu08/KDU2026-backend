package com.example.bookshelf.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bookshelf.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
     @Query("""
        SELECT b FROM Book b
        WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Book> searchBooks(String keyword, Pageable pageable);

    Page<Book> findByAuthor(String author, Pageable pageable);

}
