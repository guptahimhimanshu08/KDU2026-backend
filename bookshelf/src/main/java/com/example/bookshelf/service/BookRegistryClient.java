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

        LoggerFactory.getLogger(BookRegistryClient.class).info("Calling external registry for author: " + author);

        return webClient
                .get()
                .uri("/cover?author={author}", author) 
                .retrieve()
                .onStatus(
                    status -> status.is5xxServerError(),
                    response -> Mono.error(
                        new RegistryUnavailableException("Registry unavailable")
                    )
                )
                .bodyToMono(String.class)
                .block(); 
    }

    @Recover
    public String recover(RuntimeException ex, String author) {

        return "DEFAULT_COVER_IMAGE";
    }
}
