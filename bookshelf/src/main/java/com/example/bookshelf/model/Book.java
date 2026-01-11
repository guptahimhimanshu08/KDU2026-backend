package com.example.bookshelf.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {


    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 100)
    private String title;

    @NotBlank(message = "Author cannot be empty")
    @Size(min = 2, max = 100)
    private String author;

}
