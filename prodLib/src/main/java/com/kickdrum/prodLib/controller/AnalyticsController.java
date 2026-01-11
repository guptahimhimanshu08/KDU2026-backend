package com.kickdrum.prodLib.controller;

import com.kickdrum.prodLib.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AnalyticsController {

    private final BookService service;

    public AnalyticsController(BookService service) {
        this.service = service;
    }

    @GetMapping("/analytics/audit")
    public Map<String, Long> audit() {
        return service.auditBooks();
    }
}
