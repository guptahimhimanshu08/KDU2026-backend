package com.example.bookshelf.service;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;

@Service
public class BookRegistryClient {

    private final WebClient webClient;

    public BookRegistryClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Retryable(
        retryFor = RuntimeException.class,
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000)
    )

    public String fetchBookCover(String author) {

        System.out.println("Calling external registry for author: " + author);

        return webClient
                .get()
                .uri("https://httpstat.us/503") 
                .retrieve()
                .bodyToMono(String.class)
                .block(); 
    }

    @Recover
    public String recover(RuntimeException ex, String author) {
        System.out.println("Registry unavailable. Falling back.");
        return "DEFAULT_COVER_IMAGE";
    }
}
