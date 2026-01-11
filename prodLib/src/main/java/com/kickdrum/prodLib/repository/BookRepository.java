package com.kickdrum.prodLib.repository;

import com.kickdrum.prodLib.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
