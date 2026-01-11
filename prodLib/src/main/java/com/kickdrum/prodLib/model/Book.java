package com.kickdrum.prodLib.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "books")
public class Book {

    @Id
    private String id;

    private String title;

    private String status; 

    public Book() {
        this.id = UUID.randomUUID().toString();
    }

    public Book(String title, String status) {
        this();
        this.title = title;
        this.status = status;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }

    public void setTitle(String title) { this.title = title; }
    public void setStatus(String status) { this.status = status; }
}
